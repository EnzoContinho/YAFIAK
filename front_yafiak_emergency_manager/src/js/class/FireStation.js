/**
 * @class FireStation.js
 * Describe what is a FireStation for App
 */

 class FireStation {

    /**
     * @param {string} id
     * @param {string} name 
     * @param {string} latitude
     * @param {string} longitude
     */

   constructor(id, name, latitude, longitude) {
      this.id = id;
      this.name = name;
      this.latitude = latitude;
      this.longitude = longitude;
   }

   getId() { return this.id; }
   getName() { return this.name; }
   getLatitude() { return this.latitude; }
   getLongitude() { return this.longitude; }
}

 export { FireStation };