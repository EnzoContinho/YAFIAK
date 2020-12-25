package com.yafiak.restyafiak.controller;

import com.yafiak.restyafiak.model.FireTruck;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yafiak.restyafiak.repository.FireTruckRepository;

@RestController
public class FireTruckController {

	@Autowired
	private FireTruckRepository fireTruckRepository;
	
	@GetMapping("api/firetrucks")
    public List<FireTruck> getFireTrucks() {
        return fireTruckRepository.findAll();
    }
	
	@GetMapping("api/firetrucks/{id}")
	@ResponseBody
    public Optional<FireTruck> getFireTruckById(@PathVariable Long id) {
        return fireTruckRepository.findById(id);
    }
	
	@GetMapping("api/firetrucks/firestationId/{firestationId}")
	@ResponseBody
    public List<FireTruck> getFireTruckByFireStationId(@PathVariable Long firestationId) {
        return fireTruckRepository.findFireTrucksByFireStationId(firestationId);
    }
	
	@PostMapping("api/firetrucks")
	public FireTruck createFireTruck(@RequestBody FireTruck fireTruck) {
		return fireTruckRepository.save(fireTruck);
	}
	
	@PutMapping("api/firetrucks/{id}")
	public ResponseEntity<Object> updateFireTruck(@RequestBody FireTruck fireTruck, @PathVariable long id) {
		Optional<FireTruck> fireTrucksOptional = fireTruckRepository.findById(id);
		if (!fireTrucksOptional.isPresent())
			return ResponseEntity.notFound().build();
		fireTruckRepository.save(fireTruck);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("api/firetrucks/{id}")
	public void deleteFireTruck(@PathVariable long id) {
		fireTruckRepository.deleteById(id);
	}
	
}
