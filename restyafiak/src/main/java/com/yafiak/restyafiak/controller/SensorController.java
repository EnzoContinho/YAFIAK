package com.yafiak.restyafiak.controller;

import com.yafiak.restyafiak.model.Sensor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yafiak.restyafiak.repository.SensorRepository;

@RestController
public class SensorController {

	@Autowired
	private SensorRepository SensorRepository;
	
	@GetMapping("api/sensors")
    public List<Sensor> getSensors() {
        return SensorRepository.findAll();
    }
	
	@GetMapping("api/sensors/{id}")
	@ResponseBody
    public Optional<Sensor> getSensorById(@PathVariable Long id) {
        return SensorRepository.findById(id);
    }
	
	@PostMapping("api/sensors")
	public Sensor createSensor(@RequestBody Sensor Sensor) {
		return SensorRepository.save(Sensor);
	}
	
	@PutMapping("api/sensors/{id}")
	public ResponseEntity<Object> updateSensor(@RequestBody Sensor Sensor, @PathVariable long id) {
		Optional<Sensor> SensorOptional = SensorRepository.findById(id);
		if (!SensorOptional.isPresent())
			return ResponseEntity.notFound().build();
		SensorRepository.save(Sensor);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("api/sensors/{id}")
	public void deleteSensor(@PathVariable long id) {
		SensorRepository.deleteById(id);
	}
	
}
