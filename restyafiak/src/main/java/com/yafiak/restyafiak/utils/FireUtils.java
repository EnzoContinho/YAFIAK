package com.yafiak.restyafiak.utils;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import com.yafiak.restyafiak.controller.FireStationController;
import com.yafiak.restyafiak.model.Fire;
import com.yafiak.restyafiak.model.FireStation;

public final class FireUtils {
	
	private FireUtils() {
		throw new IllegalStateException("Class d'outils");
	}
	
	public static List<FireStation> findNearestFireStations(FireStationController controller, Fire fire) {
		List<FireStation> fireStations = controller.getFireStations();
		double xFire = fire.getLocationX();
		double yFire = fire.getLocationY();
		keepTheNearestOne(xFire, yFire, fireStations);
		return fireStations;
	}
	
	private static void keepTheNearestOne(double xFire, double yFire, List<FireStation> fireStations) {
		double[] distances = new double[fireStations.size()];
		List<FireStation> tmp = new ArrayList<>();
		for (int i = 0; i < distances.length; i++) {
			double xStation = fireStations.get(i).getLocationX();
			double yStation = fireStations.get(i).getLocationY();
			distances[i] = computeDistance(xFire, yFire, xStation, yStation);
		}
		tmp.add(fireStations.get(getIndexOfTheClosestDistance(distances)));
		fireStations.retainAll(tmp);
	}
	
	private static double computeDistance(double xA, double yA, double xB, double yB) {
		double xB_sub_xA_2 = Math.pow((xB - xA), 2);
		double yB_sub_yA_2 = Math.pow((yB - yA), 2);
		return Math.sqrt(xB_sub_xA_2 + yB_sub_yA_2);
	}
	
	private static int getIndexOfTheClosestDistance(double[] distances) {
		int index = 0;
		for (int i = 1; i < distances.length; i++) {
			if (distances[index] > distances[i])
				index = i;
		}
		return index;
	}
	
}
