package com.yafiak.simulation.controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.yafiak.simulation.YAFIAKSingleton;
import com.yafiak.simulation.database.YAFIAKDatabaseManager;

public class SensorController extends HttpServlet {
	
	/**
	 * Sensor controller class
	 */
	private static final long serialVersionUID = 763090170458183720L;
	
	private YAFIAKDatabaseManager yafiakDBManager;
	private Gson gson;
	
	public SensorController() {
		super();
		yafiakDBManager = YAFIAKSingleton.getInstance().getDatabaseManager();
		gson = new Gson();
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("[THREAD][REST API] --- Un client a atteint /api/sensors ---");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(yafiakDBManager.getAll()));
        System.out.println("[THREAD][REST API] --- Les données des capteurs ont bien été transmises ---");
        response.getWriter().flush();
        response.getWriter().close();
    }
	
}
