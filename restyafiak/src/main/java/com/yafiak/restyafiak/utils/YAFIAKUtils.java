package com.yafiak.restyafiak.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.Math;

import com.yafiak.restyafiak.controller.FireStationController;
import com.yafiak.restyafiak.model.Sensor;
import com.yafiak.restyafiak.model.FireStation;
import com.yafiak.restyafiak.model.FireTruck;

public final class YAFIAKUtils {
	
	private static final int EARTH_RADIUS = 6371; // Kilometers
	
	/**
	 * Amount of liters of water needed to decrease by one unit the intensity of a fire when the intensity is equal to 100%
	 */
	private static final int WATER_RATIO = 1500; // Liters/minute
	
	private YAFIAKUtils() {
		throw new IllegalStateException("Class d'outils");
	}
	
	public static Set<FireStation> findNearestFireStations(FireStationController controller, Sensor sensor) {
		int sensorIntensity = sensor.getIntensity();
		double sensorLatitude = sensor.getLatitude();
		double sensorLongitude = sensor.getLongitude();
		double waterNeeded = waterNeeded(sensorIntensity);
		double capacityRequired = sensorIntensity * waterNeeded;
		List<FireStation> fireStations = controller.getFireStations();
		keepTheNearestFireStations(sensorLatitude, sensorLongitude, fireStations, capacityRequired);
		return new HashSet<FireStation>(fireStations);
	}
	
	public static Set<FireTruck> deployFireTrucks(Set<FireStation> fireStations, Sensor sensor) {
		int sensorIntensity = sensor.getIntensity();
		double waterNeeded = waterNeeded(sensorIntensity);
		double capacityRequired = sensorIntensity * waterNeeded;
		List<FireTruck> fireTrucks = new ArrayList<>();
		for (FireStation fs: fireStations) {
			for (FireTruck ft: fs.getFireTrucks())
				fireTrucks.add(ft);
		}
		keepTheMostRelevantFireTrucks(capacityRequired, fireTrucks);
		return new HashSet<FireTruck>(fireTrucks);
	}
	
	private static void keepTheNearestFireStations(double sensorLatitude, double sensorLongitude, List<FireStation> fireStations, double capacityRequired) {
		double[] distances = new double[fireStations.size()]; // Kilometers
		double totalCapacity = 0;
		List<FireStation> tmp = new ArrayList<>();
		for (int i = 0; i < distances.length; i++) {
			double stationLongitude = fireStations.get(i).getLongitude();
			double stationLatitude = fireStations.get(i).getLatitude();
			distances[i] = computeDistance(sensorLatitude, sensorLongitude, stationLongitude, stationLatitude); // Kilometers
		}
		for (int i = 0; i < distances.length; i++) {
			FireStation fireStation = fireStations.get(closestDistance(distances));
			if (totalCapacity < capacityRequired) {
				tmp.add(fireStation);
				totalCapacity += fireStation.getTotalCapicity();
			} else
				break;
		}
		fireStations.retainAll(tmp);
	}
	
	private static void keepTheMostRelevantFireTrucks(double capacityRequired, List<FireTruck> fireTrucks) {
		List<FireTruck> tmp = new ArrayList<>();
		double totalCapacity = 0;
		Collections.sort(fireTrucks, new Comparator<FireTruck>() {
			@Override
			public int compare(FireTruck ft1, FireTruck ft2) {
				return Double.compare(ft1.getCapacity(), ft2.getCapacity());
			}
		});
		for (FireTruck ft: fireTrucks) {
			tmp.add(ft);
			totalCapacity += ft.getCapacity();
			if (totalCapacity >= capacityRequired)
				break;
		}
		fireTrucks.retainAll(tmp);
	}
	
	private static double degreesToRadians(double degrees) {
		return degrees * Math.PI / 180;
	}
	
	private static double waterNeeded(int intensity) {
		return ((intensity * WATER_RATIO) / 100);
	}
	
	private static double computeDistance(double lat1, double lon1, double lat2, double lon2) {
		double deltaLatitude = degreesToRadians(lat2 - lat1);
		double deltaLongitude = degreesToRadians(lon2 - lon1);
		double lat1_inRadians = degreesToRadians(lat1);
		double lat2_inRadians = degreesToRadians(lat2);
		double a = Math.sin(deltaLatitude/2) * Math.sin(deltaLatitude/2) + Math.sin(deltaLongitude/2) * Math.sin(deltaLongitude/2) * Math.cos(lat1_inRadians) * Math.cos(lat2_inRadians);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return EARTH_RADIUS * c; // Kilometers
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
