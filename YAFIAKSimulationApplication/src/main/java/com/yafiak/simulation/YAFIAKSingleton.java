package com.yafiak.simulation;

import java.util.concurrent.Semaphore;

import com.yafiak.simulation.database.YAFIAKDatabaseManager;
import com.yafiak.simulation.http.YAFIAKHttpClient;

public class YAFIAKSingleton {

	private static volatile YAFIAKSingleton instance;
	
	private YAFIAKDatabaseManager dbManager;
	private YAFIAKHttpClient httpClient;
	
	private YAFIAKSingleton() {
		System.out.println("[INFO] --- Initialisation du singleton de l'application ---");
		this.dbManager = new YAFIAKDatabaseManager();
		this.httpClient = new YAFIAKHttpClient();
		System.out.println("[INFO] --- Initialisation du singleton de l'application effectuée correctement ---");
	}
	
	private YAFIAKSingleton(Semaphore mutex) {
		System.out.println("[INFO] --- Initialisation du singleton de l'application ---");
		this.dbManager = new YAFIAKDatabaseManager();
		this.httpClient = new YAFIAKHttpClient();
		System.out.println("[INFO] --- Initialisation du singleton de l'application effectuée correctement ---");
		mutex.release();
	}
	
	public static YAFIAKSingleton getInstance() {
		YAFIAKSingleton result = instance;
		if (result != null)
			return result;
		synchronized(YAFIAKSingleton.class) {
			if (instance == null)
				instance = new YAFIAKSingleton();
			return instance;
		}
	}
	
	public static YAFIAKSingleton getInstance(Semaphore mutex) {
		YAFIAKSingleton result = instance;
		if (result != null)
			return result;
		synchronized(YAFIAKSingleton.class) {
			if (instance == null)
				instance = new YAFIAKSingleton(mutex);
			return instance;
		}
	}
	
	public YAFIAKDatabaseManager getDatabaseManager() {
		return this.dbManager;
	}
	
	public YAFIAKHttpClient getHttpClient() {
		return this.httpClient;
	}
	
}
