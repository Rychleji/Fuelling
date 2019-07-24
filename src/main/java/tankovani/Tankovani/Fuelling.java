/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankovani.Tankovani;

public class Fuelling {
    private long id;
    private double litres;
    private double pricePerLitre;
    private String city;
    private String refuelledCar;
    
    public Fuelling(){
        this(0, 0, 0, "", "");
    }

    public Fuelling(long id, double litres, double pricePerLitre, String city, String refuelledCar) {
        this.id = id;
        this.litres = litres;
        this.pricePerLitre = pricePerLitre;
        this.city = city;
        this.refuelledCar = refuelledCar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLitres() {
        return litres;
    }

    public void setLitres(double litres) {
        this.litres = litres;
    }

    public double getPricePerLitre() {
        return pricePerLitre;
    }

    public void setPricePerLitre(double pricePerLitre) {
        this.pricePerLitre = pricePerLitre;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRefuelledCar() {
        return refuelledCar;
    }

    public void setRefuelledCar(String refuelledCar) {
        this.refuelledCar = refuelledCar;
    }
    
    
}
