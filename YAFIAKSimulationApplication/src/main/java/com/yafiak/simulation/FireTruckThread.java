package com.yafiak.simulation;

import java.util.Timer;

import com.yafiak.simulation.model.FireTruck;

public class FireTruckThread implements Runnable {
	
	private FireTruck fireTruck;
	
	public FireTruckThread() {;}
	
	public FireTruckThread(FireTruck fireTruck) {
		this.fireTruck = fireTruck;
	}
	
	@Override
	public void run() {
		
		Timer timer = new Timer();
		timer.schedule(new FireTruckTask(this.fireTruck), 0, 5000);
		
	}

}
