package com.team11.Parkhaus.Investor;

import com.team11.Parkhaus.CarIF;
import com.team11.Parkhaus.Stats;

public class Investor {
    private CarIF[] cars;
    private ROIRechner rechner;
    private Stats stats = new Stats();
    double invest;

    public Investor(CarIF[] cars){
        this.cars = cars;
    }

    public ROIRechner getRechner() {
        return rechner;
    }

    public void generateRechner(double invest){
        setInvest(invest);
        rechner = new ROIRechner(getInvest(), getGewinnTag());
    }


    public double getGewinnTag(){
        double gewinn_tag = stats.getSum(cars);
        return Math.round(gewinn_tag*100.0)/100.0;
    }

    // hier wir das Invest über einen Button übergeben, noch zu implementieren.
    public void setInvest(double invest){
        this.invest = invest;
    }

    public double getInvest(){
        return this.invest;
    }

}
