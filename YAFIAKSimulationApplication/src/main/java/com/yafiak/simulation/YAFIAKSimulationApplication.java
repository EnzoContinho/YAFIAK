package com.yafiak.simulation;

import java.util.concurrent.Semaphore;

import com.yafiak.simulation.restservice.RestMainThread;

public class YAFIAKSimulationApplication {
	
	private static Semaphore singletonPrivateMutex = new Semaphore(0);
	private static Semaphore mainPrivateMutex = new Semaphore(0);
	public static boolean isReady = false;
	
	public static void main(String[] args) {
		long startTimeMillis = System.currentTimeMillis();
		System.out.println("[THREAD][YAFIAKSimulation]: ###### --- [DEMARRAGE] L'application YAFIAK Simulation démarre --- ######");
		
		YAFIAKSingleton.getInstance(singletonPrivateMutex);
		Thread restAPI = new Thread(new RestMainThread(singletonPrivateMutex, mainPrivateMutex));
		restAPI.start();
		
		while(!mainPrivateMutex.tryAcquire());
		
		System.out.println("[THREAD][YAFIAKSimulation]: ###### --- [PRÊT] L'application YAFIAK Simulation a démarré en "+ Long.toString(System.currentTimeMillis() - startTimeMillis) +" millisecondes --- ######");
	}

}
