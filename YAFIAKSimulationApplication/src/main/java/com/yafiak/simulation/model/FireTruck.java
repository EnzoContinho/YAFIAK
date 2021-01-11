package com.yafiak.simulation.model;

import java.util.Map;

/**
 * Fire truck POJO Class
 * @author Hugo Ferrer
 *
 */
public class FireTruck {

	private double longitude;
	private double latitude;
	private double capacity;
	private double waterRate;
	
	private Map<String, double[]> waypoints;
	
	public FireTruck() {;}
	
	public FireTruck(double longitude, double latitude, double capacity, double waterRate,
					 Map<String, double[]> waypoints) {
		this.latitude = longitude;
		this.latitude = latitude;
		this.capacity = capacity;
		this.waterRate = waterRate;
		this.waypoints = waypoints;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public double getWaterRate() {
		return waterRate;
	}

	public void setWaterRate(double waterRate) {
		this.waterRate = waterRate;
	}

	public Map<String, double[]> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(Map<String, double[]> waypoints) {
		this.waypoints = waypoints;
	}
	
}
