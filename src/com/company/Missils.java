package com.company;

public class Missils {

    private int numShot;
    private double abast;
    private int maxNumShot = 5;
    private double defaultAbast = 150;

    public Missils() {
        this.abast = defaultAbast;
        this.numShot = maxNumShot;
    }

    public void setNumShot(int numShot) {
        this.numShot = numShot;
    }

    public int getNumShot() {
        return numShot;
    }

    public void setAbast(double abast) {
        this.abast = abast;
    }

    public double getAbast() {
        return abast;
    }

    public void setMaxNumShot(int maxNumShot) {
        this.maxNumShot = maxNumShot;
    }

    public int getMaxNumShot() {
        return maxNumShot;
    }

    public void setDefaultAbast(double defaultAbast) {
        this.defaultAbast = defaultAbast;
    }

    public double getDefaultAbast() {
        return defaultAbast;
    }

    public int restamissil() {
        this.numShot--;
        return this.numShot;
    }
}
