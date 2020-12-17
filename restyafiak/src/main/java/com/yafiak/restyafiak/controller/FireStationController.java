package com.yafiak.restyafiak.controller;

import com.yafiak.restyafiak.model.FireStation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yafiak.restyafiak.repository.FireStationRepository;

@RestController
public class FireStationController {

	@Autowired
	private FireStationRepository fireStationRepository;
	
	@GetMapping("api/fireStations")
    public List<FireStation> getFireStations() {
        return fireStationRepository.findAll();
    }
	
	@GetMapping("api/fireStations/{id}")
	@ResponseBody
    public Optional<FireStation> getFireStationById(@PathVariable Long id) {
        return fireStationRepository.findById(id);
    }
	
	@PostMapping("api/fireStations")
	public FireStation createFireStation(@RequestBody FireStation fireStation) {
		return fireStationRepository.save(fireStation);
	}
	
	@PutMapping("api/fireStations/{id}")
	public ResponseEntity<Object> updateFire(@RequestBody FireStation fireStation, @PathVariable long id) {
		Optional<FireStation> fireStationOptional = fireStationRepository.findById(id);
		if (!fireStationOptional.isPresent())
			return ResponseEntity.notFound().build();
		fireStation.setId(id);
		fireStationRepository.save(fireStation);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("api/fireStations/{id}")
	public void deleteFire(@PathVariable long id) {
		fireStationRepository.deleteById(id);
	}
	
}
