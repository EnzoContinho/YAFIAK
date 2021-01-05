package com.yafiak.restyafiak.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "T_FIRESTATION_FST")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "sensors", "fireTrucks"})
public class FireStation implements Serializable {

	/**
	 * Class FireStation : represent a fire station in the database
	 */
	private static final long serialVersionUID = -4030225403789127661L;
	
	@Id
	@Column(name="FST_ID", unique=true, nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="FST_NAME", columnDefinition="varchar(100)", nullable=false)
	private String name;
	
	@Column(name="FST_LAT", columnDefinition="real", nullable=false)
	private double latitude;
	
	@Column(name="FST_LONG", columnDefinition="real", nullable=false)
	private double longitude;
	
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
        name = "J_FIRESTATION_SENSOR", 
        joinColumns = { @JoinColumn(name="FIRESTATION_ID") }, 
        inverseJoinColumns = { @JoinColumn(name="SENSOR_ID") }
    )
	private Set<Sensor> sensors = new HashSet<>();
	
	@OneToMany(mappedBy="fireStation", fetch = FetchType.LAZY)
    private Set<FireTruck> fireTrucks = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(Set<Sensor> sensors) {
		this.sensors = sensors;
	}
	
	public void addSensor(Sensor sensor) {
		this.sensors.add(sensor);
	}
	
	public void removeSensor(Sensor sensor) {
		for (Sensor s: this.sensors) {
			if (s.getlX() == sensor.getlX() && s.getcY() == sensor.getcY())
				this.sensors.remove(s);
		}
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
	
	public double getTotalCapicity() {
		double totalCapacity = 0;
		for (FireTruck ft: this.fireTrucks) {
			if (ft.getSensor() == null)
				totalCapacity += ft.getCapacity();
		}
		return totalCapacity;
	}
	
}
