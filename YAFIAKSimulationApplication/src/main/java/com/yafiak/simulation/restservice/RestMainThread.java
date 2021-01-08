package com.yafiak.simulation.restservice;

import java.lang.System.Logger.Level;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.yafiak.simulation.controller.SensorController;

public class RestMainThread implements Runnable {
	
	public static final URI BASE_URI = getBaseURI();

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/api/").port(9991).build();
    }
	
	public void run() {
		ResourceConfig rc = new ResourceConfig();
		rc.registerClasses(SensorController.class);
		rc.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, Level.WARNING.getName());

		try {
			System.out.println(BASE_URI.toString());
			HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
			server.start();

			System.out.println(String.format(
					"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
					BASE_URI, BASE_URI));

			System.in.read();
			server.shutdownNow();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
