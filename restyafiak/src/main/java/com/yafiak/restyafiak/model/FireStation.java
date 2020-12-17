package com.yafiak.restyafiak.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "T_FIRESTATION_FIS")
public class FireStation implements Serializable {

	/**
	 * Class FireStation : represent a fire station in the database
	 */
	private static final long serialVersionUID = -4030225403789127661L;
	
	
	@Id
	@Column(name="FIS_ID", unique=true, nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="FIS_NAME", columnDefinition="varchar", nullable=false)
	private String name;
	
	@Column(name="FIS_LOCATIONX", columnDefinition="real", nullable=false)
	private float locationX;
	
	@Column(name="FIS_LOCATIONY", columnDefinition="real", nullable=false)
	private float locationY;
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "J_FIRE_FIRESTATION", 
        joinColumns = { @JoinColumn(name="FIRESTATION_ID") }, 
        inverseJoinColumns = { @JoinColumn(name="FIRE_ID") }
    )
	Set<Fire> fires = new HashSet<>();
	
	@OneToMany(mappedBy="fireStation")
    private Set<FireTruck> fireTrucks;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getLocationX() {
		return locationX;
	}

	public void setLocationX(float locationX) {
		this.locationX = locationX;
	}

	public float getLocationY() {
		return locationY;
	}

	public void setLocationY(float locationY) {
		this.locationY = locationY;
	}
	
}
