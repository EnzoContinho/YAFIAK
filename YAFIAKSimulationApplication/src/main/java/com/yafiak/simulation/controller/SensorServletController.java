package com.yafiak.simulation.controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import com.yafiak.simulation.YAFIAKSingleton;
import com.yafiak.simulation.database.YAFIAKDatabaseManager;

/**
 * Sensor servlet controller class
 * @author Hugo Ferrer
 *
 */
public class SensorServletController extends HttpServlet {
	
	private static final long serialVersionUID = 763090170458183720L;
	
	private YAFIAKDatabaseManager yafiakDBManager;
	private Gson gson;
	
	public SensorServletController() {
		super();
		yafiakDBManager = YAFIAKSingleton.getInstance().getDatabaseManager();
		gson = new Gson();
	}
	
	/**
	 * Used when a client reach /api/sensors
	 */
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("[THREAD][REST API] --- Un client a atteint: GET /api/sensors ---");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(yafiakDBManager.getAll(true)));
        System.out.println("[THREAD][REST API] --- Les données des capteurs ont bien été transmises ---");
        response.getWriter().flush();
        response.getWriter().close();
    }
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("[THREAD][REST API] --- Un client a atteint: POST /api/sensors ---");
		System.out.println("[THREAD][REST API] --- Les microbits ont bien transmis les données des capteurs ---");
		
		YAFIAKSingleton.getInstance().setFiresDetected(true);
		
	}
	
}
