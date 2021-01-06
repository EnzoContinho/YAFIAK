/**
 * @class FireTruck.js
 * Describe what is a FireTruck for App
 */

 class FireTruck {

     /**
    * @param {string} id
    * @param {string} name
    * @param {string} latitude
    * @param {string} longitude
    * @param {string} capacity
    * @param {string} waterRate
    * @param {FireStation} fireStation
    * @param {Sensor} sensor
    * @param {string} journey
    * @param {string} icon
    */

   constructor(id, name, latitude, longitude, capacity, waterRate, fireStation, sensor, journey) {
      this.id = id;
      this.name = name;
      this.latitude = latitude;
      this.longitude = longitude;
      this.capacity = capacity;
      this.waterRate = waterRate;
      this.fireStation = fireStation;
      this.sensor = sensor;
      this.journey = journey;
      if (capacity > 60000) {
         this.icon = "firetruck4.png";
      } else if (capacity < 60000 && capacity > 40000) { 
         this.icon = "firetruck3.png";
      } else if (capacity < 40000 && capacity > 26000) { 
         this.icon = "firetruck2.png";
      } else {
         this.icon = "firetruck1.png";
      }
   }

   getId() { return this.id; }
   getName() { return this.name; }
   getLatitude() { return this.latitude; }
   getLongitude() { return this.longitude; }
   getCapacity() { return this.capacity; }
   getWaterRate() { return this.waterRate; }
   getFireStation() { return this.fireStation; }
   getJourney() { return this.journey; }
   getSensor() { return this.sensor; }
   getIcon() { return this.icon}
}

 export { FireTruck };