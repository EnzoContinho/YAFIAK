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
        this.fireList = new Array();
        this.map = new Map();
    }

    setEntryPoint(entryPoint) {
        this.apiEntryPoint = entryPoint;
    }

    setFireList(list) {
        this.fireList = list;
    }

    getFireList(id) {
        return this.fireList[id];
    }

    getFireListSize() {
        return this.fireList.length;
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

    getMap() {
        return this.map;
    }
}

export { MapService };


