package com.yafiak.simulation;

import java.util.ArrayList;
import java.util.List;

import com.yafiak.simulation.database.YAFIAKDatabaseManager;
import com.yafiak.simulation.http.YAFIAKHttpClient;
import com.yafiak.simulation.model.FireTruck;
import com.yafiak.simulation.model.Sensor;

/**
 * Simulation thread
 * @author Hugo Ferrer
 *
 */
public class YAFIAKSimulationThread implements Runnable {

	private YAFIAKDatabaseManager yafiakDBManager;
	private YAFIAKHttpClient yafiakHttpClient;
	
	private List<FireTruck> fireTrucks;
	private List<Thread> fireTruckThreads;
	
	public YAFIAKSimulationThread() {
		this.yafiakHttpClient = YAFIAKSingleton.getInstance().getHttpClient();
		this.yafiakDBManager = YAFIAKSingleton.getInstance().getDatabaseManager();
		this.fireTrucks = new ArrayList<FireTruck>();
		this.fireTruckThreads = new ArrayList<Thread>();
	}
	
	@Override
	public void run() {
		System.out.println("[THREAD][SIMULATION]: ###### --- [DEMARRAGE] La simulation démarre --- ######");
		
		while (true) {
			while(isCycleTerminated());
			
			generateRandomFires();
			System.out.println("[THREAD][SIMULATION]: --- En attente de détection des feux par l'application YAFIAK Emergency Manager ---");
			while(!YAFIAKSingleton.getInstance().getFiresDetected());
			
			YAFIAKSingleton.getInstance().setFiresDetected(false);
			System.out.println("[THREAD][SIMULATION]: --- Récupération des camions en cours ---");
			// We recover the fire trucks associated to the fires detected by the YAFIAK Emergency Manager application
			this.fireTrucks = this.yafiakHttpClient.getFireTrucks();
			
			// For each fire truck recovered, we create a new thread for it and we add that thread to a list.
			for (int i = 0; i < this.fireTrucks.size(); i++) {
				this.fireTruckThreads.add(new Thread(new FireTruckThread(this.fireTrucks.get(i))));
				this.fireTruckThreads.get(i).start();
			}
			
		}
		
	}
	
	private void generateRandomFires() {
		List<Sensor> sensors = YAFIAKSingleton.getInstance().getDatabaseManager().getAll(true);
		System.out.println("[THREAD][SIMULATION]: --- [DEBUT] Génération aléatoire des feux en cours... ---");
		int randomLimit = 1 + (int)(Math.random() * ((5 - 1) + 1));
		for (int i = 0; i < randomLimit; i++) {
			int randomIndex = 0 + (int)(Math.random() * ((sensors.size()-1 - 0) + 1));
			Sensor sensor = sensors.get(randomIndex);
			int randomIntensity = 10 + (int)(Math.random() * ((100 - 10) + 1));
			sensor.setIntensity(randomIntensity);
			this.yafiakDBManager.updateSensor(sensor);
		}
		System.out.println("[THREAD][SIMULATION]: --- [FIN] Génération aléatoire des feux terminée ---");
	}
	
	private boolean isCycleTerminated() {
		List<Sensor> sensors = YAFIAKSingleton.getInstance().getDatabaseManager().getAll(false);
		int intensitySum = 0;
		for (Sensor sensor: sensors) {
			intensitySum += sensor.getIntensity();
		}
		if (intensitySum != 0)
			return true;
		return false;
	}

}
