import { MapService } from './service/MapService.js';
import { Sensor } from './class/Sensor.js';
import { FireStation } from './class/FireStation.js';
import { FireTruck } from './class/FireTruck.js';

var mapService;
var map;
var tmpSensorsList;
var tmpFireStationList;
var tmpFireTruckList;
var firstpolyline;

window.onload = function(){
    mapService = new MapService();
    map = mapService.getMap();
    map.render();
    refreshFront();
    setInterval(refreshFront, 10000);
}

function refreshFront() {
    clean();
    refresh();
}

function clean() {
    tmpSensorsList = null;
    tmpFireStationList = null;
    tmpFireTruckList = null;
    firstpolyline = null;
    mapService.setSensorList(null);
    mapService.setFireStationList(null);
    mapService.setFireTruckList(null);
}

function refresh() {

    var JSON_SENSORS;
    var JSON_FIRESTATIONS;
    var JSON_FIRETRUCKS;

    // Open a new connection to get all sensors
    var requestSENSORS = new XMLHttpRequest();
    requestSENSORS.open('GET', 'http://localhost:8080/api/sensors', false);

    // Open a new connection to get all firestations
    var requestFIRESTATIONS = new XMLHttpRequest();
    requestFIRESTATIONS.open('GET', 'http://localhost:8080/api/firestations', false);

    // Open a new connection to get all firetrucks
    var requestFIRETRUCKS = new XMLHttpRequest();
    requestFIRETRUCKS.open('GET', 'http://localhost:8080/api/firetrucks', false);


    requestSENSORS.onload = function () {
        JSON_SENSORS = JSON.parse(this.response);
    }

    requestFIRESTATIONS.onload = function () {
        JSON_FIRESTATIONS = JSON.parse(this.response);
    }

    requestFIRETRUCKS.onload = function () {
        JSON_FIRETRUCKS = JSON.parse(this.response);
    }

    // Send requests
    requestSENSORS.send();
    requestFIRESTATIONS.send();
    requestFIRETRUCKS.send();

    tmpSensorsList = new Array();
    tmpFireStationList = new Array();
    tmpFireTruckList = new Array();


    // TRAITEMENT SUR LES SENSORS
    for (let i = 0; i < JSON_SENSORS.length; i++) {
        var object = JSON_SENSORS[i];
        var id = object.id;
        var lX = object.lX;
        var cY = object.cY;
        var latitude = object.latitude;
        var longitude = object.longitude;
        var intensity = object.intensity;

        tmpSensorsList.push(
            new Sensor(id,lX,cY,latitude,longitude,intensity)
        );

        mapService.setSensorList(tmpSensorsList);
        mapService.getMap().addListSensor(tmpSensorsList);
    }

    for (let i = 0; i < mapService.getSensorListSize(); i++) {
        var sensor = mapService.getSensorList(i);
        var intensity = sensor.getIntensity();

        if (intensity > 0) { //capteur, feu en cours

            L.marker([sensor.getLatitude(),sensor.getLongitude()], {
                icon: new L.Icon({
                    iconUrl: 'src/img/fire.png',
                    iconSize: [25, 25],
                    shadowUrl: '',
                    shadowSize: [0, 0]
                })
            }).bindPopup(
                '<p><b> FEU EN COURS ! ID : ' + sensor.getId() + ' </b>, ('
                + sensor.getLatitude() + ';'
                + sensor.getLongitude() + '), <b> INTENSITY : '
                + intensity + '</b></p>'
            ).addTo(map.getMap());

            var color;
            if(intensity < 33) {
                color = 'yellow';
            } else if(intensity < 66 && intensity > 33) {
                color = 'orange';
            } else {
                color = 'red';
            }
            
            L.circle([sensor.getLatitude(),sensor.getLongitude()], {
                color: color,
                fillColor: color,
                fillOpacity: 0.5,
                radius: intensity*(300/100)
            }).addTo(map.getMap());

        } //else { }; capteur, pas de feu en cours
    }

    // TRAITEMENT SUR LES FIRESTATIONS
    for (let i = 0; i < JSON_FIRESTATIONS.length; i++) {
        var object = JSON_FIRESTATIONS[i];
        var id = object.id;
        var name = object.name;
        var latitude = object.latitude;
        var longitude = object.longitude;

        tmpFireStationList.push(
            new FireStation(id,name,latitude,longitude)
        );

        mapService.setFireStationList(tmpFireStationList);
        mapService.getMap().addListFireStation(tmpFireStationList);
    }

    for (let i = 0; i < mapService.getFireStationListSize(); i++) {
        var fireStation = mapService.getFireStationList(i);

        var JSON_FIRETRUCKSbyFIRESTATIONID;
        var requestFIRETRUCKSbyFIRESTATIONID = new XMLHttpRequest();
        requestFIRETRUCKSbyFIRESTATIONID.open('GET', 'http://localhost:8080/api/firetrucks/firestationId/' + fireStation.getId(), false);
        requestFIRETRUCKSbyFIRESTATIONID.onload = function () {
            JSON_FIRETRUCKSbyFIRESTATIONID = JSON.parse(this.response);
        }
        requestFIRETRUCKSbyFIRESTATIONID.send();
        var formattedTrucks = mapService.formatTrucks(JSON_FIRETRUCKSbyFIRESTATIONID);

        L.marker([fireStation.getLatitude(),fireStation.getLongitude()], {
            icon: new L.Icon({
                iconUrl: 'src/img/firestation.png',
                iconSize: [50, 50],
                shadowUrl: '',
                shadowSize: [0, 0]
            })
        }).bindPopup(
            '<p><b> ID : ' + fireStation.getId() + ', '
            + fireStation.getName() + ' </b>, ('
            + fireStation.getLatitude() + '; '
            + fireStation.getLongitude() + ') '
            + '<br> <b>Camions associés à cette firestation : </b><br>'
            + formattedTrucks + '</p>'
        ).addTo(map.getMap());

    };

    // TRAITEMENT SUR LES FIRETRUCKS
    for (let i = 0; i < JSON_FIRETRUCKS.length; i++) {
        var object = JSON_FIRETRUCKS[i];
        var id = object.id;
        var name = object.name;
        var latitude = object.latitude;
        var longitude = object.longitude;
        var capacity = object.capacity;
        var waterRate = object.waterRate;
        var fireStation = object.fireStation;
        var sensor = object.sensor;
        var journey = object.journey;

        tmpFireTruckList.push(
            new FireTruck(id,name,latitude,longitude,capacity,waterRate,fireStation,sensor,journey)
        );

        mapService.setFireTruckList(tmpFireTruckList);
        mapService.getMap().addListFireTruck(tmpFireTruckList);
    }

    for (let i = 0; i < mapService.getFireTruckListSize(); i++) {
        var fireTruck = mapService.getFireTruckList(i);

        if (fireTruck.getFireStation().latitude == fireTruck.getLatitude() && fireTruck.getFireStation().longitude == fireTruck.getLongitude()) {
            console.log("Camion garé");     
        } else {
            console.log("Camion non garé");
            L.marker([fireTruck.getLatitude(),fireTruck.getLongitude()], {
                icon: new L.Icon({
                    iconUrl: 'src/img/' + fireTruck.getIcon(),
                    iconSize: [40, 40],
                    shadowUrl: '',
                    shadowSize: [0, 0]
                })
            }).bindPopup(
                '<p><b> ID : ' + fireTruck.getId() + ', '
                + fireTruck.getName() + ' </b>, ('
                + fireTruck.getLatitude() + '; '
                + fireTruck.getLongitude() + '), Capacité : '
                + fireTruck.getCapacity() + ', Débit : '
                + fireTruck.getWaterRate() + ', ID caserne : '
                + fireTruck.getFireStation().id + '</p>'
            ).addTo(map.getMap()); 

            // calcul de son itinéraire 

            // si le firesensor id associé est vide il rentre à la caserne, sinon il part en intervention  
            if (fireTruck.getSensor() != null) {
                var journey = fireTruck.getJourney();
                var pointList = [];
                // point initial
                pointList.push(new L.LatLng(fireTruck.getLatitude(),fireTruck.getLongitude()));
                // points route
                JSON.parse(journey["waypoints"]).forEach(waypoint => {
                    pointList.push(new L.LatLng(waypoint[1], waypoint[0]));
                });
                //point final
                pointList.push(new L.LatLng(fireTruck.getSensor().latitude,fireTruck.getSensor().longitude));

                firstpolyline = new L.Polyline(pointList, {
                    color: 'red',
                    weight: 3,
                    opacity: 0.5,
                    smoothFactor: 1
                });
                firstpolyline.addTo(map.getMap());
            } else {
                var journey = fireTruck.getJourney();
                var pointList = [];
                // point initial
                pointList.push(new L.LatLng(fireTruck.getLatitude(),fireTruck.getLongitude()));
                // points route inversés à l'extinction côté simulation
                journey.forEach(waypoint => {
                    pointList.push(new L.LatLng(waypoint[1], waypoint[0]));
                });
                //point final
                pointList.push(new L.LatLng(fireTruck.getFireStation().latitude,fireTruck.getFireStation().longitude));

                firstpolyline = new L.Polyline(pointList, {
                    color: 'green',
                    weight: 1,
                    opacity: 0.5,
                    smoothFactor: 1
                });
                firstpolyline.addTo(map.getMap());
            }
        }
    }
    
};