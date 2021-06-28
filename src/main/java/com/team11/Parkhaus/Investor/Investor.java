package com.team11.Parkhaus.Investor;

import com.team11.Parkhaus.CarIF;
import com.team11.Parkhaus.Manager.KostenGewinnRechner;
import com.team11.Parkhaus.Manager.Manager;
import com.team11.Parkhaus.Stats;

public class Investor {
    private ROIRechner rechner;
    private Stats stats = new Stats();
    private double gewinn;
    double invest;

    public Investor(double gewinn){
        this.gewinn = gewinn;
    }

    public ROIRechner getRechner() {
        return rechner;
    }

    public void generateRechner(double invest){
        setInvest(invest);
        rechner = new ROIRechner(getInvest(), gewinn);
    }



    // hier wir das Invest über einen Button übergeben, noch zu implementieren.
    public void setInvest(double invest){
        this.invest = invest;
    }

    public double getInvest(){
        return this.invest;
    }
    public double getGewinnTag(){return this.gewinn;}

}
