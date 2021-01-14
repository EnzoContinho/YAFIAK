package com.yafiak.simulation.model;

public class Sensor {

	private int x;
	private int y;
	private int intensity;
	private float latitude;
	private float longitude;
	
	public Sensor() {;}
	
	public Sensor(int x, int y, int intensity, float latitude, float longitude) {
		this.x = x;
		this.y = y;
		this.intensity = intensity;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}
	
}
