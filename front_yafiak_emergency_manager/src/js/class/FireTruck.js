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
    * @param {string} icon
    */

   constructor(id, name, latitude, longitude, capacity, waterRate, fireStation) {
      this.id = id;
      this.name = name;
      this.latitude = latitude;
      this.longitude = longitude;
      this.capacity = capacity;
      this.waterRate = waterRate;
      this.fireStation = fireStation;
      if (capacity == 50) {
         this.icon = "firetruck4.png";
      } else if (capacity == 30) { 
         this.icon = "firetruck3.png";
      } else if (capacity == 20) { 
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
   getIcon() { return this.icon}
}

 export { FireTruck };