package com.company;

public class AvioCombat extends Avio{
    private int tripulacio;
    private Missils missils;

    public AvioCombat(String nom, Coordenada coordenada, int tripulacio, double maximaVelocitat, double minDistancia) {
        super(nom, coordenada, maximaVelocitat, minDistancia);
        this.tripulacio = tripulacio;
    }

    public int getTripulacio() {
        return tripulacio;
    }

    public void setTripulacio(int tripulacio) {
        this.tripulacio = tripulacio;
    }

    public Missils getMissils() { return missils; }


    public String toString() { //caca

        String avioToString = String.join(" , ");

        return avioToString;
    }

    public boolean disparar(Avio avioObjectiu) {
        boolean boo = false;
        if (this.missils.getNumShot() == 0) {
            System.out.println("No et quede missils! Has de tornar a base.");
        }
        else {
            System.out.println("Numero missils restant: "+ this.missils.restamissil());
            if (this.missils.getAbast() >= this.calculDistancia(avioObjectiu.getCoordenadesActuals()) ) {
                boo = true;
                System.out.println("El missil ha donat de ple!");
            }
            else {
                System.out.println("El missil no ha arribat!");
            }
        }

        return boo;
    }
}
