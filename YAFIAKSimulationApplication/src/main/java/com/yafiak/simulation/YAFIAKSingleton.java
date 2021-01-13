package com.yafiak.simulation;

import java.util.concurrent.Semaphore;

import com.yafiak.simulation.database.YAFIAKDatabaseManager;
import com.yafiak.simulation.http.YAFIAKHttpClient;

public class YAFIAKSingleton {

	private static volatile YAFIAKSingleton instance;
	
	private YAFIAKDatabaseManager dbManager;
	private YAFIAKHttpClient httpClient;
	
	private boolean firesDetected = false;
	
	private YAFIAKSingleton() {;}
	
	private YAFIAKSingleton(Semaphore singletonMutex, Semaphore dbMutex) {
		System.out.println("[INFO] --- Initialisation du singleton de l'application ---");
		this.dbManager = new YAFIAKDatabaseManager(dbMutex);
		this.httpClient = new YAFIAKHttpClient();
		System.out.println("[INFO] --- Initialisation du singleton de l'application effectuée correctement ---");
		singletonMutex.release();
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
	
	public static YAFIAKSingleton getInstance(Semaphore singletonMutex, Semaphore dbMutex) {
		YAFIAKSingleton result = instance;
		if (result != null)
			return result;
		synchronized(YAFIAKSingleton.class) {
			if (instance == null)
				instance = new YAFIAKSingleton(singletonMutex, dbMutex);
			return instance;
		}
	}
	
	public YAFIAKDatabaseManager getDatabaseManager() {
		return this.dbManager;
	}
	
	public YAFIAKHttpClient getHttpClient() {
		return this.httpClient;
	}
	
	public boolean getFiresDetected() {
		return this.firesDetected;
	}
	
	public void setFiresDetected(boolean firesDetected) {
		this.firesDetected = firesDetected;
	}
	
}
