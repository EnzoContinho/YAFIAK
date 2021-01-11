package com.yafiak.simulation.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;

import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yafiak.simulation.model.FireTruck;

/**
 * YAFIAK HTTP Client class
 * @author Hugo Ferrer
 *
 */
public class YAFIAKHttpClient {

	private final String CONFIGURATION_FILE = "yafiak.http.properties";
	private final String HTTP_GET = "GET";
	private final String HTTP_USER_AGENT = "User-Agent";
	
	private URL url;
	private String agent;
	private HttpURLConnection connection;
	
	/**
	 * Initialize the YAFIAK HTTP Client which is able to send HTTP requests to the YAFIAK Emergency Manager REST API.
	 * To work properly, this class needs to initialize itself that way:
	 * 	1st: Loading the file containing the properties
	 * 	2nd: Check if the remote API of the YAFIAK Emergency Manger is ready
	 */
	public YAFIAKHttpClient() {
		System.out.println("[YAFIAKHttpClient] --- Initialisation du client HTTP ---");
		System.out.println("\t[DEBUT] --- Vérification de l'état de l'API distante");
		
		Properties properties = new Properties();
		URL yafiak_http_properties = this.getClass().getClassLoader().getResource(CONFIGURATION_FILE);
		File pathOfPropertyFile = null;
		FileInputStream propertyFile = null;
		
		try {
			pathOfPropertyFile = Paths.get(yafiak_http_properties.toURI()).toFile();
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
		
		try {
			url = new URL(properties.getProperty("yafiak.http.url"));
			System.out.println("\t--- [STEP 4/6] Construction de l'URL réussie");
		} catch (MalformedURLException e) {
			System.out.println("\t--- [STEP 4/6] Echec de construction de l'URL");
			e.printStackTrace();
		}
		
		agent = properties.getProperty("yafiak.http.agent");
		
		if (url != null && agent != null) {
			
			try {
				connection = (HttpURLConnection) url.openConnection();
				System.out.println("\t--- [STEP 5/6] Ouverture de la connexion à l'API distante...");
			} catch (IOException e) {
				System.out.println("\t--- [STEP 5/6] Echec de l'ouverture de la connexion à l'API distante");
				e.printStackTrace();
			}
			
			try {
				if (connection != null)
					connection.setRequestMethod(HTTP_GET);
			} catch (ProtocolException e) {
				e.printStackTrace();
			}
			
			connection.setRequestProperty(HTTP_USER_AGENT, agent);
			int responseCode = 0;
			
			try {
				responseCode = connection.getResponseCode();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (responseCode != HttpURLConnection.HTTP_OK) {
				System.out.println("\t--- [STEP 6/6] Echec de connexion à l'API distante");
				System.exit(0);
			} else {
				System.out.println("\t--- [STEP 6/6] Connexion à l'API distante réussie");
			}
			
		}
		
		System.out.println("\t[FIN] --- Vérification de l'état de l'API distante effectuée correctement");
		System.out.println("[YAFIAKHttpClient] --- Initialisation du client HTTP effectuée correctement ---");
	}
	
	/**
	 * The function <code>getFireTrucks()</code> recover all the trucks from the YAFIAK Emergency Manager database.
	 * If the recovered trucks have a fire to treat, then these trucks are added to a list.
	 * @return
	 * 	<code>List<FireTruck></code>
	 */
	public List<FireTruck> getFireTrucks() {
		List<FireTruck> fireTrucks = new ArrayList<>();
		
		try {
			connection.setRequestMethod(HTTP_GET);
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
		
		connection.setRequestProperty(HTTP_USER_AGENT, agent);
		int responseCode = 0;
		
		try {
			responseCode = connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (responseCode != HttpURLConnection.HTTP_OK) {
			BufferedReader in = null;
			
			try {
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			if (in != null) {
				try {
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			deserializeJSON(fireTrucks, response);
			
		}
		
		return fireTrucks;
	}
	
	private void deserializeJSON(List<FireTruck> listToFill, StringBuffer apiResponse) {
		JSONArray jFireTrucks = new JSONArray(apiResponse.toString());
		
		for (int i = 0; i < jFireTrucks.length(); i++) {
			JSONObject jFireTruck = (JSONObject) jFireTrucks.get(i);
			
			if (jFireTruck.get("journey") != null) {
				JSONArray journeys = new JSONArray(
					((JSONObject) jFireTruck.get("journey")).getString("waypoints")
				);
				
				double longitude = jFireTruck.getDouble("longitude");
				double latitude = jFireTruck.getDouble("latitude");
				double capacity = jFireTruck.getDouble("capacity");
				double waterRate = jFireTruck.getDouble("waterRate");
				
				Map<String, double[]> journey = new HashMap<>();
				
				for (int k = 0; k < journeys.length(); k++) {
					JSONArray waypoint = (JSONArray) journeys.get(k);
					double coordinates[] = {waypoint.getDouble(0), waypoint.getDouble(1)};
					journey.put(Integer.toString(k), coordinates);
				}
				
				listToFill.add(new FireTruck(longitude, latitude, capacity, waterRate, journey));
			}
		}
	}
	
}
