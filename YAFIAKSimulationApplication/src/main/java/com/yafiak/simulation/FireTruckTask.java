package com.yafiak.simulation;

import java.util.TimerTask;

import com.yafiak.simulation.model.FireTruck;

public class FireTruckTask extends TimerTask {

	private static final int WATER_RATIO = 1500;
	
	private FireTruck fireTruck;
	
	private double arrivalLongitude = 0;
	private double arrivalLatitude = 0;
	private double waterNeeded = 0;
	
	private String currentWaypoint;
	
	public FireTruckTask() {;}

	public FireTruckTask(FireTruck fireTruck) {
		this.fireTruck = fireTruck;
		double[] arrivalPoint = fireTruck.getWaypoints().get(fireTruck.getLastWaypointIndex());
		this.arrivalLongitude = arrivalPoint[0];
		this.arrivalLatitude = arrivalPoint[1];
		this.waterNeeded = waterNeeded(this.fireTruck.getLinkedSensor().getIntensity());
		this.currentWaypoint = "0";
	}
	
	@Override
	public void run() {
		double fireTruckLongitude = this.fireTruck.getLongitude();
		double fireTruckLatitude = this.fireTruck.getLatitude();
		
		// If the fire truck have to go to its fire station
		if (fireTruck.getCapacity() == 0) {
			boolean forward = false;
			moveTruck(forward);
		}
		
		// If the fire truck is not on the intervention site
		if(fireTruck.getCapacity() != 0 && fireTruckLongitude != this.arrivalLongitude && fireTruckLatitude != this.arrivalLatitude) {
			boolean forward = true;
			moveTruck(forward);
		}
		
		// If the fire truck is arrived
		if(fireTruck.getCapacity() != 0 && fireTruckLongitude == this.arrivalLongitude && fireTruckLatitude == this.arrivalLatitude) {
			// Subtract the capacity of the truck by the water needed to decrease by one the intensity of the linked sensor
			this.fireTruck.setCapacity(this.fireTruck.getCapacity() - this.waterNeeded);
			this.fireTruck.getLinkedSensor().setIntensity(this.fireTruck.getLinkedSensor().getIntensity() - 1);
			
			// Update the sensor in the database
			YAFIAKSingleton.getInstance().getDatabaseManager().updateSensor(this.fireTruck.getLinkedSensor());
			// Update the truck
			YAFIAKSingleton.getInstance().getHttpClient().postFireTruck(fireTruck);
		}
		
	}
	
	private double waterNeeded(int intensity) {
		return ((intensity * WATER_RATIO) / 100);
	}
	
	private void moveTruck(boolean forward) {
		// Move the truck to the next point
		int tmpWaypoint = Integer.decode(this.currentWaypoint);
		
		if (forward)
			this.currentWaypoint = Integer.toString(++tmpWaypoint);
		else
			this.currentWaypoint = Integer.toString(--tmpWaypoint);
		
		this.fireTruck.setLongitude(this.fireTruck.getWaypoints().get(this.currentWaypoint)[0]);
		this.fireTruck.setLatitude(this.fireTruck.getWaypoints().get(this.currentWaypoint)[1]);
		
		// Update the truck
		YAFIAKSingleton.getInstance().getHttpClient().postFireTruck(fireTruck);
	}

}
