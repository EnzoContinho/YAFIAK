package com.yafiak.restyafiak.controller;

import com.yafiak.restyafiak.model.FireStation;
import com.yafiak.restyafiak.model.FireTruck;
import com.yafiak.restyafiak.model.Sensor;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yafiak.restyafiak.repository.SensorRepository;
import com.yafiak.restyafiak.utils.YAFIAKUtils;

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
	public Sensor createSensor(@RequestBody Sensor sensor) {
		return SensorRepository.save(sensor);
	}
	
	@PutMapping("api/sensors")
	public ResponseEntity<Object> updateSensor(@RequestBody Sensor sensor) {
		List<Sensor> sensors = SensorRepository.findAll();
		Sensor sensorToUpdate = null;
		// We try to find out the right sensor
		for (Sensor s: sensors) {
			if (s.getlX() == sensor.getlX() && s.getcY() == sensor.getcY())
				sensorToUpdate = s;
		}
		// If the sensor exists
		if (sensorToUpdate != null) {
			// If a fire has been switched off
			if (sensor.getIntensity() == 0) {
				// Update intensity
				sensorToUpdate.setIntensity(sensor.getIntensity());
				// Delete associations
				sensorToUpdate.setFireStations(new HashSet<FireStation>());
				sensorToUpdate.setFireTrucks(new HashSet<FireTruck>());
			}
			// If it is a simple update of the intensity
			if (sensor.getIntensity() > 0 && sensor.getIntensity() != sensorToUpdate.getIntensity()) {
				// Update intensity
				sensorToUpdate.setIntensity(sensor.getIntensity());
			}
			// If a fire is detected
			if (sensor.getIntensity() > 0 && sensorToUpdate.getIntensity() == 0) {
				// Update intensity
				sensorToUpdate.setIntensity(sensor.getIntensity());
				// Creation of the associations
				Set<FireStation> associatedFireStations = YAFIAKUtils.findNearestFireStations(new FireStationController(), sensor);
				sensorToUpdate.setFireStations(associatedFireStations);
				Set<FireTruck> associatedFireTrucks = YAFIAKUtils.deployFireTrucks(sensorToUpdate.getFireStations(), sensor);
				sensorToUpdate.setFireTrucks(associatedFireTrucks);
			}
			// Saving the update
			SensorRepository.save(sensorToUpdate);
		}
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("api/sensors/{id}")
	public void deleteSensor(@PathVariable long id) {
		SensorRepository.deleteById(id);
	}
	
}
