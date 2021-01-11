import { MapService } from './service/MapService.js';
import { Sensor } from './class/Sensor.js';

var mapService;
var map;
var tmpSensorsList;

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
    mapService.setSensorList(null);
}

function refresh() {

    var JSON_SENSORS;
    
    // Open a new connection to get all sensors
    var requestSENSORS = new XMLHttpRequest();
    requestSENSORS.open('GET', 'http://localhost:8080/api/front/sensors', false);

    requestSENSORS.onload = function () {
        JSON_SENSORS = JSON.parse(this.response);
    }

    // Send requests
    requestSENSORS.send();

    tmpSensorsList = new Array();


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

        } else { //capteur, pas de feu en cours

            L.marker([sensor.getLatitude(),sensor.getLongitude()], {
                icon: new L.Icon({
                    iconUrl: 'src/img/sensor.png',
                    iconSize: [25, 25],
                    shadowUrl: '',
                    shadowSize: [0, 0]
                })
            }).bindPopup(
                '<p><b> RAS ! ID : ' + sensor.getId() + ' </b>, ('
                + sensor.getLatitude() + ';'
                + sensor.getLongitude() + '), <b> INTENSITY : '
                + intensity + '</b></p>'
            ).addTo(map.getMap());
        }
    }
    
};