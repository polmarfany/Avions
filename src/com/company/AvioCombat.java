package com.company;

public class AvioCombat extends Avio{
    private int tripulacio;

    public AvioCombat(String nom, Coordenada coordenada, int tripulacio) {
        super(nom, coordenada);
        this.tripulacio = tripulacio;
    }

    public int getTripulacio() {
        return tripulacio;
    }

    public void setTripulacio(int tripulacio) {
        this.tripulacio = tripulacio;
    }

    public String toString() { //caca

        String avioToString = String.join(" , ");

        return avioToString;
    }


}
