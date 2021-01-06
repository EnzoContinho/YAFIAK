package com.yafiak.restyafiak.model;

import java.io.Serializable;
import java.util.Base64;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="T_JOURNEY_JOU")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fireTruck"})
public class Journey implements Serializable {

	/**
	 * Class Journey : represents the journey of a truck in the database
	 */
	private static final long serialVersionUID = 6100568181532545729L;

	@Id
	@Column(name="JOU_ID", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="JOU_WAYPOINTS", columnDefinition="text", nullable=false)
	private String waypoints;
	
	@OneToOne(mappedBy = "journey")
	private FireTruck fireTruck;
	
	public Journey() {;}
	
	public Journey(String waypoints, FireTruck fireTruck) {
		this.waypoints = waypoints;
		this.fireTruck = fireTruck;
	}
	
	public String getWaypoints() {
		byte[] decodedBytes = Base64.getDecoder().decode(waypoints);
		return new String(decodedBytes);
	}

	public void setWaypoints(String waypoints) {
		this.waypoints = waypoints;
	}

	public FireTruck getFireTruck() {
		return fireTruck;
	}

	public void setFireTruck(FireTruck fireTruck) {
		this.fireTruck = fireTruck;
	}
	
}
