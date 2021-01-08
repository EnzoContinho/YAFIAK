package com.yafiak.simulation;

import com.yafiak.simulation.restservice.RestMainThread;

public class YAFIAKSimulationApplication {

	public static void main(String[] args) {
		long startTimeMillis = System.currentTimeMillis();
		System.out.println("###### --- [DEMARRAGE] L'application YAFIAK Simulation démarre --- ######");
		
		YAFIAKSingleton yafiak = YAFIAKSingleton.getInstance();
		
		Thread restAPI = new Thread(new RestMainThread());
		restAPI.start();
		
		long endTimeMillis = System.currentTimeMillis();
		System.out.println("###### --- [PRÊT] L'application YAFIAK Simulation a démarré en "+Long.toString(endTimeMillis-startTimeMillis)+" millisecondes --- ######");
	}

}
