package com.yafiak.simulation.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

public class YAFIAKHttpClient {

	private final String CONFIGURATION_FILE = "yafiak.http.properties";
	
	private URL url;
	private String agent;
	private HttpURLConnection connection;
	
	public YAFIAKHttpClient() {
		System.out.println("[YAFIAKHttpClient] --- Initialisation du client HTTP ---");
		System.out.println("\t[DEBUT] --- V�rification de l'�tat de l'API distante");
		
		Properties properties = new Properties();
		URL yafiak_http_properties = this.getClass().getClassLoader().getResource(CONFIGURATION_FILE);
		File pathOfPropertyFile = null;
		FileInputStream propertyFile = null;
		
		try {
			pathOfPropertyFile = Paths.get(yafiak_http_properties.toURI()).toFile();
			System.out.println("\t--- [STEP 1/6] Fichier properties trouv�");
		} catch (URISyntaxException e) {
			System.out.println("\t--- [STEP 1/6] Fichier properties non trouv�");
			e.printStackTrace();
		}
		
		try {
			if (pathOfPropertyFile != null) {
				propertyFile = new FileInputStream(pathOfPropertyFile.getAbsolutePath());
				System.out.println("\t--- [STEP 2/6] Cr�ation du flux du fichier properties r�ussie");
			}
		} catch (FileNotFoundException e) {
			System.out.println("\t--- [STEP 2/6] Echec de cr�ation du flux du fichier properties");
			e.printStackTrace();
		}
		
		try {
			if (propertyFile != null) {
				properties.load(propertyFile);
				System.out.println("\t--- [STEP 3/6] Fichier properties inject� avec succ�s dans l'application");
			}
		} catch (IOException e) {
			System.out.println("\t--- [STEP 3/6] Echec de l'injection du fichier properties dans l'application");
			e.printStackTrace();
		}
		
		try {
			url = new URL(properties.getProperty("yafiak.http.url"));
			System.out.println("\t--- [STEP 4/6] Construction de l'URL r�ussie");
		} catch (MalformedURLException e) {
			System.out.println("\t--- [STEP 4/6] Echec de construction de l'URL");
			e.printStackTrace();
		}
		
		agent = properties.getProperty("yafiak.http.agent");
		
		if (url != null && agent != null) {
			
			try {
				connection = (HttpURLConnection) url.openConnection();
				System.out.println("\t--- [STEP 5/6] Ouverture de la connexion � l'API distante...");
			} catch (IOException e) {
				System.out.println("\t--- [STEP 5/6] Echec de l'ouverture de la connexion � l'API distante");
				e.printStackTrace();
			}
			
			try {
				if (connection != null)
					connection.setRequestMethod("GET");
			} catch (ProtocolException e) {
				e.printStackTrace();
			}
			
			connection.setRequestProperty("User-Agent", agent);
			int responseCode = 0;
			
			try {
				responseCode = connection.getResponseCode();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (responseCode != HttpURLConnection.HTTP_OK) {
				System.out.println("\t--- [STEP 6/6] Echec de connecion � l'API distante");
				System.exit(0);
			} else {
				System.out.println("\t--- [STEP 6/6] Connexion � l'API distante r�ussie");
			}
			
		}
		
		System.out.println("\t[FIN] --- V�rification de l'�tat de l'API distante effectu�e correctement");
		System.out.println("[YAFIAKHttpClient] --- Initialisation du client HTTP effectu�e correctement ---");
	}
	
}
