package com.yafiak.simulation.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Semaphore;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.yafiak.simulation.model.Sensor;

public class YAFIAKDatabaseManager {
	
	private final String CONFIGURATION_FILE = "yafiak.db.properties";

	private Semaphore dbMutex;
	
	private String host;
	private String username;
	private String password;

	private Connection connection;

	public YAFIAKDatabaseManager(Semaphore dbMutex) {

		System.out.println("[YAFIAKDatabaseManager] --- Initialisation du gestionnaire de la base de données ---");
		
		this.dbMutex = dbMutex;
		
		System.out.println("\t[DEBUT] --- Chargement du fichier des propriétés de la base de données");

		Properties properties = new Properties();
		
		URL yafiak_db_properties = this.getClass().getClassLoader().getResource(CONFIGURATION_FILE);
		if (yafiak_db_properties == null) {
			System.out.println("[YAFIAKDatabaseManager][ERROR] --- Le fichier de yafiak.db.properties n'existe pas ---");
			System.exit(0);
		}
		
		File pathOfPropertyFile = null;
		FileInputStream propertyFile = null;

		try {
			pathOfPropertyFile = Paths.get(yafiak_db_properties.toURI()).toFile();
			System.out.println("\t--- [STEP 1/6] Fichier properties trouvé");
		} catch (URISyntaxException e) {
			System.out.println("\t--- [STEP 1/6] Fichier properties non trouvé");
			e.printStackTrace();
		}

		try {
			if (pathOfPropertyFile != null) {
				propertyFile = new FileInputStream(pathOfPropertyFile.getAbsolutePath());
				System.out.println("\t--- [STEP 2/6] Création du flux du fichier properties réussie");
			}
		} catch (FileNotFoundException e) {
			System.out.println("\t--- [STEP 2/6] Echec de création du flux du fichier properties");
			e.printStackTrace();
		}

		try {
			if (propertyFile != null) {
				properties.load(propertyFile);
				System.out.println("\t--- [STEP 3/6] Fichier properties injecté avec succès dans l'application");
			}
		} catch (IOException e) {
			System.out.println("\t--- [STEP 3/6] Echec de l'injection du fichier properties dans l'application");
			e.printStackTrace();
		}

		host = properties.getProperty("yafiak.database.host");
		username = properties.getProperty("yafiak.database.username");
		password = properties.getProperty("yafiak.database.password");
		
		String importFile = null;
		if (properties.getProperty("yafiak.database.import") != null)
			importFile = properties.getProperty("yafiak.database.import");

		try {
			connection = DriverManager.getConnection(host, username, password);
			System.out.println("\t--- [STEP 4/6] Connexion à la base de données effectuée avec succès");
		} catch (SQLException e) {
			System.out.println("\t--- [STEP 4/6] Echec de la connexion à la base de données");
			e.printStackTrace();
		}
		
		if (importFile != null && connection != null) {
			ScriptRunner sr = new ScriptRunner(connection);
			URL import_file = this.getClass().getClassLoader().getResource(importFile);
	        Reader reader = null;
			try {
				reader = new BufferedReader(new FileReader(Paths.get(import_file.toURI()).toFile().getAbsolutePath()));
				System.out.println("\t--- [STEP 5/6] Chargement du script SQL de peuplement initial de la base de données");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			if (reader != null) {
				System.out.println("\t--- [STEP 6/6] Exécution du script SQL de peuplement initial de la base de données");
				sr.runScript(reader);
			}
		}
		
		System.out.println("\t[FIN] --- Fin du chargement du fichier des propriétés de la base de données");
		System.out.println("[YAFIAKDatabaseManager] --- Initialisation du gestionnaire de la base de données effectuée correctement ---");
	}

	/**
	 * The <code>getAll()</code> method allows to recover all the sensors in the database
	 * @return
	 * 	<code>List<Sensor></code>
	 */
	public List<Sensor> getAll(boolean log) {
		long startTime = System.currentTimeMillis();
		List<Sensor> liste = new ArrayList<>();
		
		try {
			if (log)
				System.out.println("[YAFIAKDatabaseManager] --- Récupération de tous les capteurs en base... ---");
			while(!dbMutex.tryAcquire()); // critical section
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM T_SENSOR_SEN");
			
			while (rs.next())
				liste.add(new Sensor(rs.getInt(3),rs.getInt(2),rs.getInt(4), rs.getFloat(5), rs.getFloat(6)));
			
			rs.close();
			st.close();
			
			dbMutex.release(); // critical section
			
			if (log)
				System.out.println("[YAFIAKDatabaseManager] --- Résultat retourné en "+ Long.toString(System.currentTimeMillis()-startTime) +" millisecondes ---");
		} catch (SQLException e) {
			System.out.println("[YAFIAKDatabaseManager][ERROR] --- La récupération des capteurs en base a échoué ---");
			e.printStackTrace();
		}
		
		return liste;
	}
	
	/**
	 * The <code>getSensor(int x, int y)</code> method allows to recover the sensor with the logical coordinates x and y in the database
	 * @param int x
	 * @param int y
	 * @return
	 * 	<code>Sensor</code>
	 */
	public Sensor getSensor(int x, int y) {
		long startTime = System.currentTimeMillis();
		Sensor sensor = null;
		
		try {
			System.out.println("[YAFIAKDatabaseManager] --- Récupération du capteur de coordonnées logiques ("+Integer.toString(x)+", "+Integer.toString(y)+")... ---");
			while(!dbMutex.tryAcquire()); // critical section
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM T_SENSOR_SEN WHERE sen_x="+Integer.toString(x)+" AND sen_y="+Integer.toString(y));
			
			while (rs.next())
				sensor = new Sensor(rs.getInt(3),rs.getInt(2),rs.getInt(4), rs.getFloat(5), rs.getFloat(6));
			
			rs.close();
			st.close();
			
			dbMutex.release(); // critical section
			
			System.out.println("[YAFIAKDatabaseManager] --- Résultat retourné en "+ Long.toString(System.currentTimeMillis()-startTime) +" millisecondes ---");
		} catch (SQLException e) {
			System.out.println("[YAFIAKDatabaseManager][ERROR] --- La récupération du capteur ("+Integer.toString(x)+", "+Integer.toString(y)+") a échoué ---");
			e.printStackTrace();
		}
		
		return sensor;
		
	}
	
	/**
	 * The <code>getSensor(Sensor sensor)</code> method allows to update the sensor with the logical coordinates x and y in the database
	 * @param Sensor sensor
	 * @return
	 * 	<code>Sensor</code>
	 */
	public void updateSensor(Sensor sensor) {
		long startTime = System.currentTimeMillis();
		
		try {
			System.out.println("[YAFIAKDatabaseManager] --- Mise à jour du capteur de coordonnées logiques ("+sensor.getX()+", "+sensor.getY()+")... ---");
			while(!dbMutex.tryAcquire()); // critical section
			
			String update = "UPDATE public.t_sensor_sen SET sen_intensity = ? WHERE sen_x = ? AND sen_y = ?;";
			
			PreparedStatement pst = connection.prepareStatement(update);
			pst.setInt(1, sensor.getIntensity());
			pst.setInt(2, sensor.getX());
			pst.setInt(3, sensor.getY());
			int affectedRows = pst.executeUpdate();
			connection.commit();
			
			pst.close();
			
			dbMutex.release(); // critical section
			
			System.out.println("[YAFIAKDatabaseManager] --- Mise à jour effectuée en "+ Long.toString(System.currentTimeMillis()-startTime) +" millisecondes ---");
		} catch (SQLException e) {
			System.out.println("[YAFIAKDatabaseManager][ERROR] --- Mise à jour du capteur ("+sensor.getX()+", "+sensor.getY()+") a échoué ---");
			e.printStackTrace();
		}
		
	}
	
}
