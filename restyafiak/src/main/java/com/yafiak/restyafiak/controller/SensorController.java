package com.yafiak.restyafiak.controller;

import com.yafiak.restyafiak.model.FireStation;
import com.yafiak.restyafiak.model.FireTruck;
import com.yafiak.restyafiak.model.Journey;
import com.yafiak.restyafiak.model.Sensor;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yafiak.restyafiak.repository.SensorRepository;
import com.yafiak.restyafiak.utils.YAFIAKUtils;
import com.yafiak.restyafiak.utils.http.YAFIAKHttpClient;

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
		YAFIAKHttpClient httpClient = new YAFIAKHttpClient();
		Sensor sensorToUpdate = null;
		// We try to find out the right sensor
		for (Sensor s: sensors) {
			if (s.getlX() == sensor.getlX() && s.getcY() == sensor.getcY()) {
				sensorToUpdate = s;
				break;
			}
		}
		
		// Tell to the simulation API to recover the trucks
		if (sensorToUpdate == null) {
			try {
				// Simple POST with no content
				httpClient.postEndOfTransmission();
			} catch (IOException e) {
				e.printStackTrace();
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
					ft.setJourney(null);
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
					// Start and end points for the journey
					String departurePoint = Double.toString(ft.getFireStation().getLongitude())
							+","
							+Double.toString(ft.getFireStation().getLatitude());
					String arrivalPoint = Double.toString(sensorToUpdate.getLongitude())
							+","
							+Double.toString(sensorToUpdate.getLatitude());
					
					String waypoints = "";
					try {
						// Get the journey from the OSRM API
						waypoints = httpClient.getPath(departurePoint, arrivalPoint);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (waypoints != "")
						waypoints = Base64.getEncoder().encodeToString(waypoints.getBytes());
					// Set the sensor and a journey
					ft.setSensor(sensorToUpdate);
					ft.setJourney(new Journey(waypoints, ft));
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
