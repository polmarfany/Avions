package com.company;

public class Coordenada {
    private String name;
    private double X;
    private double Y;
    private double Z;

    public Coordenada(String name, double X, double Y, double Z) {
        this.name = name + " (" + X + ", "+ Y + ", " + Z + ")";
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public Coordenada(Coordenada objectivePoint) {
        this.X = objectivePoint.getX();
        this.Y = objectivePoint.getY();
        this.Z = objectivePoint.getZ();
    }

    public Coordenada(double X, double Y, double Z) {
        this.name = "Espai Aeri" + " (" + X + ", "+ Y + ", " + Z + ")";
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public String toString(){
        return "("+ this.X+", "+this.Y+", "+this.Z+") ";
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getZ() {
        return Z;
    }


}


