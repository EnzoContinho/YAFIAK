package com.yafiak.simulation.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class YAFIAKDatabaseManager {

	private final String CONFIGURATION_FILE = "yafiak.db.properties";
	
	private String host;
	private String username;
	private String password;
	
	private Connection connection;
	
	public YAFIAKDatabaseManager() {
		
		System.out.println("[YAFIAKDatabaseManager] --- Initialisation du gestionnaire de la base de données ---");
		System.out.println("\t[DEBUT] --- Chargement du fichier des propriétés de la base de données et connexion");
		
		Properties properties = new Properties();
		URL yafiak_db_properties = this.getClass().getClassLoader().getResource(CONFIGURATION_FILE);
		File pathOfPropertyFile = null;
		FileInputStream propertyFile = null;
		
		try {
			pathOfPropertyFile = Paths.get(yafiak_db_properties.toURI()).toFile();
			System.out.println("\t--- [STEP 1/4] Fichier properties trouvé");
		} catch (URISyntaxException e) {
			System.out.println("\t--- [STEP 1/4] Fichier properties non trouvé");
			e.printStackTrace();
		}
		
		try {
			if (pathOfPropertyFile != null) {
				propertyFile = new FileInputStream(pathOfPropertyFile.getAbsolutePath());
				System.out.println("\t--- [STEP 2/4] Création du flux du fichier properties réussie");
			}
		} catch (FileNotFoundException e) {
			System.out.println("\t--- [STEP 2/4] Echec de création du flux du fichier properties");
			e.printStackTrace();
		}
		
		try {
			if (propertyFile != null) {
				properties.load(propertyFile);
				System.out.println("\t--- [STEP 3/4] Fichier properties injecté avec succès dans l'application");
			}
		} catch (IOException e) {
			System.out.println("\t--- [STEP 3/4] Echec de l'injection du fichier properties dans l'application");
			e.printStackTrace();
		}
		
		host = properties.getProperty("yafiak.database.host");
		username = properties.getProperty("yafiak.database.username");
		password = properties.getProperty("yafiak.database.password");
		
		try {
			connection = DriverManager.getConnection(host, username, password);
			System.out.println("\t--- [STEP 4/4] Connexion à la base de données effectuée avec succès");
		} catch (SQLException e) {
			System.out.println("\t--- [STEP 4/4] Echec de la connexion à la base de données");
			e.printStackTrace();
		}
		
		System.out.println("\t[FIN] --- Fin du chargement du fichier des propriétés de la base de données et connexion");
		System.out.println("[YAFIAKDatabaseManager] --- Initialisation du gestionnaire de la base de données effectuée correctement ---");
	}
	
	// TODO Function to get sensors information
	
}
