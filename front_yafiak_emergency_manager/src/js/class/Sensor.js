/**
 * @class Sensor.js
 * Describe what is a Sensor for App
 */

 class Sensor {

    /**
     * @param {string} id
     * @param {number} lx
     * @param {number} cy
     * @param {string} latitude
     * @param {string} longitude
     * @param {number} intensity 
     */

   constructor(id, lx, cy, latitude, longitude, intensity) {
      this.id = id;
      this.lx = lx;
      this.cy = cy;
      this.latitude = latitude;
      this.longitude = longitude;
      this.intensity = intensity;
   }

   getId() { return this.id; }
   getLx() { return this.lx}
   getCy() { return this.cy}
   getLatitude() { return this.latitude; }
   getLongitude() { return this.longitude; }
   getIntensity() { return this.intensity; }
 }

 export { Sensor };