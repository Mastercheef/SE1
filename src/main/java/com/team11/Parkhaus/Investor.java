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
        return gewinn_tag;
    }
    private double getInvest(){
        return  100000.0;
    }

}
