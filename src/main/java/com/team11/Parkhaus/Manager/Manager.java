package com.team11.Parkhaus.Manager;

import com.team11.Parkhaus.CarIF;
import com.team11.Parkhaus.Stats;

import java.io.PrintWriter;

public class Manager {
    private CarIF[] cars;
    private KostenGewinnRechner kgrechner;
    private double kostenFaktor;



    public void setkostenFaktor(double kostenFaktor){
        this.kostenFaktor =kostenFaktor;
    }

    public void createKGRechner(CarIF[] cars){
        kgrechner = new KostenGewinnRechner(cars, this.kostenFaktor);
    }
    public KostenGewinnRechner getKGRechner(){
        return this.kgrechner;
    }


}