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

    getMap() {
        return this.map;
    }
}

export { MapService };


