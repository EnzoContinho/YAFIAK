package com.yafiak.restyafiak.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yafiak.restyafiak.model.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
