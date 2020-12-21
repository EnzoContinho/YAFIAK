/**
 * @class FireStation.js
 * Describe what is a FireStation for App
 */

 class FireStation {

    /**
     * @param {string} id
     * @param {string} name 
     * @param {string} locationX
     * @param {string} locationY
     */

   constructor(id, name, locationX, locationY) {
      this.id = id;
      this.name = name;
      this.locationX = locationX;
      this.locationY = locationY;
   }

   getId() { return this.id; }
   getName() { return this.name; }
   getLocationX() { return this.locationX; }
   getLocationY() { return this.locationY; }
}

 export { FireStation };