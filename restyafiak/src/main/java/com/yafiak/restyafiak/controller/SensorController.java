package com.yafiak.restyafiak.controller;

import com.yafiak.restyafiak.model.FireStation;
import com.yafiak.restyafiak.model.FireTruck;
import com.yafiak.restyafiak.model.Sensor;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yafiak.restyafiak.repository.SensorRepository;
import com.yafiak.restyafiak.utils.YAFIAKUtils;

@RestController
public class SensorController {

	@Autowired
	private SensorRepository SensorRepository;
	@Autowired
	private FireStationController fireSationController;
	
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
			if (s.getlX() == sensor.getlX() && s.getcY() == sensor.getcY()) {
				sensorToUpdate = s;
				break;
			}
		}
		// If the sensor exists
		if (sensorToUpdate != null) {
			int savedIntensity = sensorToUpdate.getIntensity();
			int newIntensity = sensor.getIntensity();
			// If a fire has been switched off
			if (newIntensity == 0) {
				// Update intensity
				sensorToUpdate.setIntensity(newIntensity);
				// Delete associations
				for (FireStation fs: sensorToUpdate.getFireStations()) {
					fs.removeSensor(sensorToUpdate);
				}
				for (FireTruck ft: sensorToUpdate.getFireTrucks()) {
					ft.setSensor(null);
				}
			}
			// If it is a simple update of the intensity
			if (savedIntensity != 0 && newIntensity > 0 && newIntensity != savedIntensity) {
				// Update intensity
				sensorToUpdate.setIntensity(sensor.getIntensity());
			}
			// If a fire is detected
			if (savedIntensity == 0 && newIntensity > 0) {
				// Update intensity
				sensorToUpdate.setIntensity(sensor.getIntensity());
				// Creation of the associations
				sensorToUpdate.setFireStations(YAFIAKUtils.findNearestFireStations(this.fireSationController, sensor));
				for (FireStation fs: sensorToUpdate.getFireStations()) {
					fs.addSensor(sensorToUpdate);
				}
				sensorToUpdate.setFireTrucks(YAFIAKUtils.deployFireTrucks(sensorToUpdate.getFireStations(), sensor));
				for (FireTruck ft: sensorToUpdate.getFireTrucks()) {
					ft.setSensor(sensorToUpdate);
				}
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
