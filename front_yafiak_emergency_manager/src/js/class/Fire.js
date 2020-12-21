/**
 * @class Fire.js
 * Describe what is a Fire for App
 */

 class Fire {

    /**
     * @param {string} id
     * @param {string} locationX
     * @param {string} locationY
     * @param {number} intensity 
     */

   constructor(id, locationX, locationY, intensity) {
      this.id = id;
      this.locationX = locationX;
      this.locationY = locationY;
      this.intensity = intensity;
   }

   getId() { return this.id; }
   getLocationX() { return this.locationX; }
   getLocationY() { return this.locationY; }
   getIntensity() { return this.intensity; }
 }

 export { Fire };