package com.yafiak.simulation;

import com.yafiak.simulation.restservice.RestMainThread;

public class YAFIAKSimulationApplication {

	public static void main(String[] args) {
		long startTimeMillis = System.currentTimeMillis();
		System.out.println("###### --- [DEMARRAGE] L'application YAFIAK Simulation d�marre --- ######");
		
		YAFIAKSingleton yafiak = YAFIAKSingleton.getInstance();
		
		// TODO Initialize the different threads
		Thread restAPI = new Thread(new RestMainThread());
		
		long endTimeMillis = System.currentTimeMillis();
		System.out.println("###### --- [PR�T] L'application YAFIAK Simulation a d�marr� en "+Long.toString(endTimeMillis-startTimeMillis)+" millisecondes --- ######");
	}

}
