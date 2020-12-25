import { Map } from '../class/Map.js';

/**
 * @class
 * A service that provide needed data for the map
 */
class MapService {

    /**
    * @param {Map} map
    * @param {string} apiEntryPoint
    */

    constructor() {
        this.sensorList = new Array();
        this.map = new Map();
    }

    setEntryPoint(entryPoint) {
        this.apiEntryPoint = entryPoint;
    }

    setSensorList(list) {
        this.sensorList = list;
    }

    getSensorList(id) {
        return this.sensorList[id];
    }

    getSensorListSize() {
        return this.sensorList.length;
    } 

    setFireStationList(list) {
        this.fireStationList = list;
    }

    getFireStationList(id) {
        return this.fireStationList[id];
    }

    getFireStationListSize() {
        return this.fireStationList.length;
    } 

    setFireTruckList(list) {
        this.fireTruckList = list;
    }

    getFireTruckList(id) {
        return this.fireTruckList[id];
    }

    getFireTruckListSize() {
        return this.fireTruckList.length;
    } 

    getMap() {
        return this.map;
    }

    formatTrucks(JSON) {
        var formattedTrucks;
        formattedTrucks += "<br>----------------------------<br>";
        JSON.forEach(truck => {
            formattedTrucks += "ID camion : " + truck.id + "<br>";
            formattedTrucks += "Nom camion : " + truck.name + "<br>";
            formattedTrucks += "Capacité camion : " + truck.capacity + "<br>";
            formattedTrucks += "----------------------------<br>";
        });
        return formattedTrucks;
    }
}

export { MapService };


