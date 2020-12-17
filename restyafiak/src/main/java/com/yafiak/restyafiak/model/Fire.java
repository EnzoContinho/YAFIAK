package com.yafiak.restyafiak.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "T_FIRE_FIR")
public class Fire implements Serializable {

	/**
	 * Class Fire : represent a fire in the database
	 */
	private static final long serialVersionUID = 9115552225103191557L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FIR_ID", unique=true, nullable=false)
    private Long id;
	
	@Column(name="FIR_LOCATIONX", columnDefinition="real", nullable=false)
	private float locationX;
	
	@Column(name="FIR_LOCATIONY", columnDefinition="real", nullable=false)
	private float locationY;
	
	@Column(name="FIR_INTENSITY", columnDefinition="integer", nullable=false)
	private int intensity;

	@ManyToMany(mappedBy="fires")
    private Set<FireStation> fireStations = new HashSet<>();
	
	public Long getId() {
		return id;
	}

	public float getLocationX() {
		return locationX;
	}

	public float getLocationY() {
		return locationY;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLocationX(float locationX) {
		this.locationX = locationX;
	}

	public void setLocationY(float locationY) {
		this.locationY = locationY;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}
}