package com.yafiak.restyafiak.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yafiak.restyafiak.model.FireStation;

public interface FireStationRepository extends JpaRepository<FireStation, Long> {
}
