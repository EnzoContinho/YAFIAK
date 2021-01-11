package com.yafiak.simulation;

import com.yafiak.simulation.database.YAFIAKDatabaseManager;
import com.yafiak.simulation.http.YAFIAKHttpClient;

/**
 * Simulation thread
 * @author Hugo Ferrer
 *
 */
public class YAFIAKSimulationThread implements Runnable {

	private YAFIAKDatabaseManager yafiakDBManager;
	private YAFIAKHttpClient yafiakHttpClient;
	
	public YAFIAKSimulationThread() {
		this.yafiakDBManager = YAFIAKSingleton.getInstance().getDatabaseManager();
		this.yafiakHttpClient = YAFIAKSingleton.getInstance().getHttpClient();
	}
	
	@Override
	public void run() {
		// TODO Simulate fires and trucks
	}

}
