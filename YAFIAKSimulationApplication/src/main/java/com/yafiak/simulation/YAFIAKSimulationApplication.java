package com.yafiak.simulation;

import java.util.concurrent.Semaphore;

import com.yafiak.simulation.restservice.RestMainThread;

/**
 * YAFIAKSimulationApplication
 * The class which is the entry point of the YAFIAK Simulation Application
 * @author Hugo Ferrer
 *
 */
public class YAFIAKSimulationApplication {
	
	/**
	 * singletonPrivateMutex and mainPrivateMutex are semaphores used to synchronized the main thread with the REST API Threads at launch
	 */
	private static Semaphore singletonPrivateMutex = new Semaphore(0);
	private static Semaphore mainPrivateMutex = new Semaphore(0);
	
	/**
	 * dbMutex is the semaphore which is used to not compromise the data in the database
	 * Especially, it avoids that one thread reads data whereas one other writes data
	 */
	private static Semaphore dbMutex = new Semaphore(1);
	
	/**
	 * Main thread of the YAFIAK Simulation Application
	 * Starts the application in the following order:
	 * 	1st: The singleton of the application is initialized
	 * 	2nd: The REST API thread which embeds a TOMCAT server starts and listens on the port entered in the RestMainThread class
	 * 	3rd: The simulation thread starts, it is in charge of updating the sensor value according to the different interventions of the fire trucks
	 * 		 and updating fire trucks data stored in the YAFIAK Emergency Manager Application
	 * @param args
	 * 	No arguments are needed at launch
	 */
	public static void main(String[] args) {
		long startTimeMillis = System.currentTimeMillis();
		System.out.println("[THREAD][YAFIAKSimulation]: ###### --- [DEMARRAGE] L'application YAFIAK Simulation démarre --- ######");
		
		YAFIAKSingleton.getInstance(singletonPrivateMutex, dbMutex);
		Thread restAPI = new Thread(new RestMainThread(singletonPrivateMutex, mainPrivateMutex));
		Thread simulation = new Thread(new YAFIAKSimulationThread());
		
		restAPI.start();
		
		while(!mainPrivateMutex.tryAcquire());
		
		simulation.start();
		
		System.out.println(
				"[THREAD][YAFIAKSimulation]: ###### --- [PRÊT] L'application YAFIAK Simulation a démarré en "
				+ Long.toString(System.currentTimeMillis() - startTimeMillis)
				+ " millisecondes --- ######"
		);
	}

}
