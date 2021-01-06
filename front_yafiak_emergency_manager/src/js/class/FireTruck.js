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
    * @param {string} itinerary
    * @param {string} icon
    */

   constructor(id, name, latitude, longitude, capacity, waterRate, fireStation, sensor, itinerary) {
      this.id = id;
      this.name = name;
      this.latitude = latitude;
      this.longitude = longitude;
      this.capacity = capacity;
      this.waterRate = waterRate;
      this.fireStation = fireStation;
      this.sensor = sensor;
      this.itinerary = [[4.832767,45.752254],[4.832998,45.752191],[4.831326,45.74976],[4.830319,45.748656],[4.829955,45.748449],[4.829627,45.7485],[4.828779,45.749039],[4.825368,45.750113],[4.823829,45.750966],[4.821107,45.751912],[4.820735,45.752271],[4.820666,45.752766],[4.820434,45.752925],[4.820107,45.752939],[4.819477,45.752536],[4.819145,45.752527],[4.819112,45.753815],[4.818528,45.754476],[4.817978,45.754433],[4.816966,45.753585],[4.817204,45.752622],[4.817113,45.752123],[4.816822,45.751989],[4.816352,45.752121],[4.816117,45.753254],[4.816407,45.755146],[4.815878,45.755691],[4.817549,45.756252],[4.8186,45.75602],[4.819032,45.756073],[4.820096,45.756465],[4.820722,45.756876],[4.821423,45.759294],[4.821629,45.759579],[4.822415,45.759783],[4.822586,45.759949],[4.822597,45.760284],[4.821892,45.760346],[4.822199,45.761315],[4.82194,45.761684],[4.821994,45.761964],[4.821475,45.762067],[4.821648,45.762665],[4.822101,45.762681],[4.822194,45.763188]];
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
   getItinerary() { return this.itinerary; }
   getSensor() { return this.sensor; }
   getIcon() { return this.icon}
}

 export { FireTruck };