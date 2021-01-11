package com.yafiak.simulation.restservice;

import java.io.File;
import java.util.concurrent.Semaphore;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import com.yafiak.simulation.controller.SensorServletController;

public class RestMainThread implements Runnable {
	
	private static final int PORT = 8081;
	
	private Semaphore singletonMutex;
	private Semaphore mainMutex;
	
	public RestMainThread() {;}
	
	public RestMainThread(Semaphore singletonMutex, Semaphore mainMutex) {
		this.singletonMutex = singletonMutex;
		this.mainMutex = mainMutex;
	}
	
	public void run() {
		
		while(!singletonMutex.tryAcquire());
		
		long startTime = System.currentTimeMillis();
		System.out.println("[THREAD][REST API] --- [DEMARRAGE] L'API REST démarre ---");
		
		Tomcat tomcat = new Tomcat();
		
		tomcat.setPort(PORT);
		tomcat.setHostname("localhost");
		String appBase = ".";
		tomcat.getHost().setAppBase(appBase);
		
		File docBase = new File(System.getProperty("java.io.tmpdir"));
		Context context = tomcat.addContext("", docBase.getAbsolutePath());
		
		Class<SensorServletController> servletClass = SensorServletController.class;
		Tomcat.addServlet(context, servletClass.getSimpleName(), servletClass.getName());
		context.addServletMappingDecoded("/api/sensors/*", servletClass.getSimpleName());

		try {
			tomcat.start();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
		
		System.out.println("[THREAD][REST API] --- Le serveur Tomcat est en écoute sur le port "+Integer.toString(PORT));
		System.out.println("[THREAD][REST API] --- [PRÊT] Le service REST a démarré en "+ Long.toString(System.currentTimeMillis() - startTime) +" millisecondes ---");
		mainMutex.release();
		
		tomcat.getServer().await();
		
	}
}
