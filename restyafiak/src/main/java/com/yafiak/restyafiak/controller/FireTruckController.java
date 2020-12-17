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
	
	@GetMapping("api/fireTrucks")
    public List<FireTruck> getFireTrucks() {
        return fireTruckRepository.findAll();
    }
	
	@GetMapping("api/fireTrucks/{id}")
	@ResponseBody
    public Optional<FireTruck> getFireTruckById(@PathVariable Long id) {
        return fireTruckRepository.findById(id);
    }
	
	@PostMapping("api/fireTrucks")
	public FireTruck createFireTruck(@RequestBody FireTruck fireTruck) {
		return fireTruckRepository.save(fireTruck);
	}
	
	@PutMapping("api/fireTrucks/{id}")
	public ResponseEntity<Object> updateFireTrucks(@RequestBody FireTruck fireTruck, @PathVariable long id) {
		Optional<FireTruck> fireTrucksOptional = fireTruckRepository.findById(id);
		if (!fireTrucksOptional.isPresent())
			return ResponseEntity.notFound().build();
		fireTruck.setId(id);
		fireTruckRepository.save(fireTruck);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("api/fireTrucks/{id}")
	public void deleteFireTrucks(@PathVariable long id) {
		fireTruckRepository.deleteById(id);
	}
	
}
