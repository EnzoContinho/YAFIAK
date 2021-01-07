package com.yafiak.simulation;

public class YAFIAKSimulationApplication {

	public static void main(String[] args) {
		long startTimeMillis = System.currentTimeMillis();
		System.out.println("###### --- [DEMARRAGE] L'application YAFIAK Simulation démarre --- ######");
		
		YAFIAKSingleton yafiak = YAFIAKSingleton.getInstance();
		
		// TODO Initialize the different threads
		
		long endTimeMillis = System.currentTimeMillis();
		System.out.println("###### --- [PRÊT] L'application YAFIAK Simulation a démarré en "+Long.toString(endTimeMillis-startTimeMillis)+" millisecondes --- ######");
	}

}
