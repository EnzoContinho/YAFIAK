package com.yafiak.restyafiak.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yafiak.restyafiak.model.FireTruck;

public interface FireTruckRepository extends JpaRepository<FireTruck, Long> {
	
	@Query(value = "SELECT * FROM t_firetruck_ftr ftr INNER JOIN t_firestation_fst fst ON ftr.ftr_firestation_id = fst.fst_id WHERE ftr.ftr_firestation_id = :firestationId", nativeQuery = true)
	List<FireTruck> findFireTrucksByFireStationId(@Param("firestationId") Long firestationId);
}
