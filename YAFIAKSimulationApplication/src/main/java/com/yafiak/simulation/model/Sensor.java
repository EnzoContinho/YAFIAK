package com.yafiak.simulation.model;

import java.io.Serializable;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 * 
 * @author Hugo Ferrer
 * Sensor POJO class
 *
 */
public class Sensor {

	private int x;
	private int y;
	private int intensity;
	
	public Sensor() {;}
	
	public Sensor(int x, int y, int intensity) {
		this.x = x;
		this.y = y;
		this.intensity = intensity;
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
	
	public String toJSON() {
		Jsonb jsonb = JsonbBuilder.create();
	    String result = jsonb.toJson(this);
	}
	
}
