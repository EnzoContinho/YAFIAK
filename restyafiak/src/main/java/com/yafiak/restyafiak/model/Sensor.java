package com.yafiak.restyafiak.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "T_SENSOR_SEN")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fireStations", "fireTrucks"})
public class Sensor implements Serializable {

	/**
	 * Class Sensor : represent a sensor in the database
	 */
	private static final long serialVersionUID = 9115552225103191557L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SEN_ID", unique=true, nullable=false)
    private Long id;
	
	@Column(name="SEN_X", columnDefinition="integer", nullable=false)
	private int lX;
	
	@Column(name="SEN_Y", columnDefinition="integer", nullable=false)
	private int cY;
	
	@Column(name="SEN_LAT", columnDefinition="real", nullable=false)
	private double latitude;
	
	@Column(name="SEN_LONG", columnDefinition="real", nullable=false)
	private double longitude;
	
	@Column(name="SEN_INTENSITY", columnDefinition="integer", nullable=false)
	private int intensity;

	@ManyToMany(mappedBy="sensors")
    private Set<FireStation> fireStations = new HashSet<>();

	@OneToMany(mappedBy="sensor")
	private Set<FireTruck> fireTrucks = new HashSet<>();
	
	public int getlX() {
		return lX;
	}

	public void setlX(int lX) {
		this.lX = lX;
	}

	public int getcY() {
		return cY;
	}

	public void setcY(int cY) {
		this.cY = cY;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	public Set<FireStation> getFireStations() {
		return fireStations;
	}

	public void setFireStations(Set<FireStation> fireStations) {
		this.fireStations = fireStations;
	}

	public Set<FireTruck> getFireTrucks() {
		return fireTrucks;
	}

	public void setFireTrucks(Set<FireTruck> fireTrucks) {
		this.fireTrucks = fireTrucks;
	}

	public Long getId() {
		return id;
	}
	
}
