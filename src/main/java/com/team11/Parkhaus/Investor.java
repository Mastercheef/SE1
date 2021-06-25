package com.team11.Parkhaus;

public class Investor {
    CarIF[] cars;
    ROIRechner rechner;
    private Stats stats = new Stats();
    public Investor(CarIF[] cars){
        this.cars = cars;
        rechner = new ROIRechner(getInvest(), getGewinnTag());
    }

    double getGewinnTag(){
        double gewinn_tag = stats.getSum(cars);
        return Math.round(gewinn_tag*10000.0)/10000.0;
    }

    // hier wir das Invest über einen Button übergeben, noch zu implementieren.
    private double getInvest(){
        return  100000.0;
    }

}
