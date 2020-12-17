package com.yafiak.restyafiak.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "T_FIRETRUCK_FIT")
public class FireTruck implements Serializable {

	/**
	 * Class FireTruck : represents a fire truck in the database
	 */
	private static final long serialVersionUID = -356829351657437356L;
	
	@Id
	@Column(name="FIT_ID", unique=true, nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="FIT_CAPACITY", columnDefinition="real", nullable=false)
	private float capacity;
	
	@Column(name="FIT_RATE", columnDefinition="real", nullable=false)
	private float waterRate;
	
	@Column(name="FIT_SPEED", columnDefinition="real", nullable=false)
	private float speed;

	@ManyToOne
    @JoinColumn(name="FIT_FIRESTATION_ID")
    private FireStation fireStation;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public float getWaterRate() {
		return waterRate;
	}

	public void setWaterRate(float waterRate) {
		this.waterRate = waterRate;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}
