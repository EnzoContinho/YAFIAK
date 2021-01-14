package com.yafiak.restyafiak.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class YAFIAKHttpClient {

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String OSRM_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";
	private static final String SIMU_BASE_URL = "http://localhost:8081/api/sensors";
	private static final String SEMI_COLON = ";";
	private static final String GEOJSON_OPTION = "?geometries=geojson";
	
	private URL url;
	private String jArray = "";
	
	public String getPath(String departurePoint, String arrivalPoint) throws IOException {
		// Set the URL
		url = new URL(
			OSRM_BASE_URL
			+departurePoint
			+SEMI_COLON
			+arrivalPoint
			+GEOJSON_OPTION
		);
		// Connect to the OSRM API
		HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
		connexion.setRequestMethod("GET");
		connexion.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = connexion.getResponseCode();
		//System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) {
			// Success
			BufferedReader in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			JSONObject jObject = null;
			try {
				jObject = new JSONObject(response.toString());
			}catch (JSONException err){
				err.printStackTrace();
			}
			if (jObject != null) {
				JSONObject routesObject = (JSONObject) jObject.getJSONArray("routes").get(0);
				jArray = routesObject.getJSONObject("geometry").getJSONArray("coordinates").toString();
			}
		} else {
			System.out.println("GET request not worked");
		}
		return jArray;
	}
	
	public void postEndOfTransmission() throws IOException {
		// Set the URL
		url = new URL(SIMU_BASE_URL);
		HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
		connexion.setRequestMethod("POST");
		connexion.setRequestProperty("Content-Type", "application/json; utf-8");
		connexion.setRequestProperty("Accept", "application/json");
		connexion.setDoOutput(true);
		
		int responseCode = 0;
		responseCode = connexion.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			System.out.println("End of transmission POST succeed");
		} else {
			System.out.println("End of transmission POST failed ("+Integer.toString(responseCode)+")");
		}
		
	}
}
