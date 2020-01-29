package com.company;

public class AvioComercial extends Avio {

    private int passatgers;

    public AvioComercial(String nom, Coordenada coordenada, int passatgers, double maximaVelocitat, double distanciaSeguretat) {
        super(nom, coordenada, maximaVelocitat, distanciaSeguretat);
        this.passatgers = passatgers;
    }

    public String toString() { //Caca

        String avioToString = String.join(" , ");

        return avioToString;
    }

    public int getPassatgers() {
        return passatgers;
    }

    public void setPassatgers(int passatgers) {
        this.passatgers = passatgers;
    }
}
