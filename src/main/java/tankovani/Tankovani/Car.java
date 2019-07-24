/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankovani.Tankovani;

class Car {

    private String licencePlate;
    private String Colour;
    private int currentMileage;
    
    public Car(){
        this("", "", 0);
    }

    public Car(String licencePlate, String Colour, int currentMileage) {
        this.licencePlate = licencePlate;
        this.Colour = Colour;
        this.currentMileage = currentMileage;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String Colour) {
        this.Colour = Colour;
    }

    public int getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(int currentMileage) {
        this.currentMileage = currentMileage;
    }
    
}
