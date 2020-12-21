import { MapService } from './service/MapService.js';
import { Fire } from './class/Fire.js';
import { FireStation } from './class/FireStation.js';

window.onload = function(){

    var JSON_FIRES;
    var JSON_FIRESTATIONS;

    // Open a new connection to get all fires
    var requestFIRES = new XMLHttpRequest();
    requestFIRES.open('GET', 'http://localhost:8080/api/fires', false);

    // Open a new connection to get all fireStations
    var requestFIRESTATIONS = new XMLHttpRequest();
    requestFIRESTATIONS.open('GET', 'http://localhost:8080/api/fireStations', false);

    requestFIRES.onload = function () {
        JSON_FIRES = JSON.parse(this.response);
    }

    requestFIRESTATIONS.onload = function () {
        JSON_FIRESTATIONS = JSON.parse(this.response);
    }

    // Send requests
    requestFIRES.send();
    requestFIRESTATIONS.send();

    var mapService = new MapService();
    var map = mapService.getMap();
    var tmpFireList = new Array();
    var tmpFireStationList = new Array();

    // TRAITEMENT SUR LES FIRES
    for (let i = 0; i < JSON_FIRES.length; i++) {
        var object = JSON_FIRES[i];
        var id = object.id;
        var locationX = object.locationX;
        var locationY = object.locationY;
        var intensity = object.intensity;

        tmpFireList.push(
            new Fire(id,locationX,locationY,intensity)
        );

        mapService.setFireList(tmpFireList);
        mapService.getMap().addListFire(tmpFireList);
    }

    map.render();

    // TRAITEMENT SUR LES FIRES
    for (let i = 0; i < mapService.getFireListSize(); i++) {
        var fire = mapService.getFireList(i);
        console.log(fire);
        L.marker([fire.getLocationX(),fire.getLocationY()], {
            icon: new L.Icon({
                iconUrl: 'src/img/fire.png',
                iconSize: [25, 25],
                shadowUrl: '',
                shadowSize: [0, 0]
            })
        }).bindPopup(
            '<p><b> ID : ' + fire.getId() + ' </b>, ('
            + fire.getLocationX() + ';'
            + fire.getLocationY() + '), <b> INTENSITY : '
            + fire.getIntensity() + '</b></p>'
        ).addTo(map.getMap());

        var color;
        if(fire.getIntensity() < 33) {
            color = 'yellow';
        } else if(fire.getIntensity() < 66 && fire.getIntensity() > 33) {
            color = 'orange';
        } else {
            color = 'red';
        }
        
        var associatedCircle = L.circle([fire.getLocationX(),fire.getLocationY()], {
            color: color,
            fillColor: color,
            fillOpacity: 0.5,
            radius: 50+fire.getIntensity()*(300/100)
        }).addTo(map.getMap());
    }

    
    // TRAITEMENT SUR LES FIRESTATIONS
    for (let i = 0; i < JSON_FIRESTATIONS.length; i++) {
        var object = JSON_FIRESTATIONS[i];
        var id = object.id;
        var name = object.name;
        var locationX = object.locationX;
        var locationY = object.locationY;

        tmpFireStationList.push(
            new FireStation(id,name,locationX,locationY)
        );

        mapService.setFireStationList(tmpFireStationList);
        mapService.getMap().addListFireStation(tmpFireStationList);
    }


    // TRAITEMENT SUR LES FIRESTATIONS
    for (let i = 0; i < mapService.getFireStationListSize(); i++) {
        var fireStation = mapService.getFireStationList(i);
        console.log(fireStation);

        L.marker([fireStation.getLocationX(),fireStation.getLocationY()], {
            icon: new L.Icon({
                iconUrl: 'src/img/firestation.png',
                iconSize: [50, 50],
                shadowUrl: '',
                shadowSize: [0, 0]
            })
        }).bindPopup(
            '<p><b> ID : ' + fireStation.getId() + ', '
            + fireStation.getName() + ' </b>, ('
            + fireStation.getLocationX() + '; '
            + fireStation.getLocationY() + ')</p>'
        ).addTo(map.getMap());

    };
    
};