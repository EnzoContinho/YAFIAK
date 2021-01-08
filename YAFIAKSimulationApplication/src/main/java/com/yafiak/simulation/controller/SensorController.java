package com.yafiak.simulation.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.yafiak.simulation.YAFIAKSingleton;
import com.yafiak.simulation.database.YAFIAKDatabaseManager;
import com.yafiak.simulation.model.Sensor;

@Path("api")
public class SensorController {
	
	private YAFIAKDatabaseManager yafiakDatabaseManager;
	
	public SensorController() {
		YAFIAKSingleton yafiak = YAFIAKSingleton.getInstance();
		yafiakDatabaseManager = yafiak.getDatabaseManager();
	}
	
	@GET
	@Path("api/sensors")
	public List<Sensor> getAllSensors(){
		return yafiakDatabaseManager.getAll();
	}
	
}
