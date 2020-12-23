package com.yafiak.restyafiak.utils;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import com.yafiak.restyafiak.controller.FireStationController;
import com.yafiak.restyafiak.model.Sensor;
import com.yafiak.restyafiak.model.FireStation;

public final class YAFIAKUtils {
	
	private static final int EARTH_RADIUS = 6371; // Kilometer
	
	private YAFIAKUtils() {
		throw new IllegalStateException("Class d'outils");
	}
	
	public static List<FireStation> findNearestFireStations(FireStationController controller, Sensor sensor) {
		List<FireStation> fireStations = controller.getFireStations();
		double sensorLatitude = sensor.getLatitude();
		double sensorLongitude = sensor.getLongitude();
		// TODO The number of fire stations to retain
		keepTheNearestFireStations(sensorLatitude, sensorLongitude, fireStations, 1);
		return fireStations;
	}
	
	private static void keepTheNearestFireStations(double sensorLatitude, double sensorLongitude, List<FireStation> fireStations, int howMany) {
		double[] distances = new double[fireStations.size()]; // Kilometer
		List<FireStation> tmp = new ArrayList<>();
		for (int i = 0; i < distances.length; i++) {
			double stationLongitude = fireStations.get(i).getLongitude();
			double stationLatitude = fireStations.get(i).getLatitude();
			distances[i] = computeDistance(sensorLatitude, sensorLongitude, stationLongitude, stationLatitude); // Kilometer
		}
		for (int i = 0; i < howMany; i++)
			tmp.add(fireStations.get(closestDistance(distances)));
		fireStations.retainAll(tmp);
	}
	
	private static double degreesToRadians(double degrees) {
		return degrees * Math.PI / 180;
	}
	
	private static double computeDistance(double lat1, double lon1, double lat2, double lon2) {
		double deltaLatitude = degreesToRadians(lat2 - lat1);
		double deltaLongitude = degreesToRadians(lon2 - lon1);
		double lat1_inRadians = degreesToRadians(lat1);
		double lat2_inRadians = degreesToRadians(lat2);
		double a = Math.sin(deltaLatitude/2) * Math.sin(deltaLatitude/2) + Math.sin(deltaLongitude/2) * Math.sin(deltaLongitude/2) * Math.cos(lat1_inRadians) * Math.cos(lat2_inRadians);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return EARTH_RADIUS * c; // Kilometer
	}
	
	private static double[] deleteAt(double[] array, int index) {
        if (array == null || index < 0 || index >= array.length)
        	return array;
        double[] newArray = new double[array.length - 1];
        for (int i = 0, k = 0; i < array.length; i++) {
            if (i == index)
            	continue;
            newArray[k++] = array[i];
        }
        return newArray;
	}
	
	private static int closestDistance(double[] distances) {
		int index = 0;
		for (int i = 1; i < distances.length; i++) {
			if (distances[index] > distances[i])
				index = i;
		}
		distances = deleteAt(distances, index);
		return index;
	}
	
}
