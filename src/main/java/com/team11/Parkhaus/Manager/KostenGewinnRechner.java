package com.team11.Parkhaus.Manager;

import com.team11.Parkhaus.CarIF;
import com.team11.Parkhaus.Stats;

import java.io.PrintWriter;

public class KostenGewinnRechner {
    private CarIF[] cars;
    private Stats stats = new Stats();
    private double umsatzTag , umsatzNachSteuer;
    private double kostenTag;
    private double gewinnTag;
    private double kostenFaktor;
    private double SteuerTag;
    private final double MwSt = 0.19;

    public KostenGewinnRechner(CarIF[] cars, double kostenFaktor){
        this.cars = cars;
        this.kostenFaktor = kostenFaktor;
        this.umsatzTag = stats.getSum(cars);
        this.SteuerTag = (this.umsatzTag) *  MwSt;
        this.umsatzNachSteuer = this.umsatzTag -  this.SteuerTag;
        this.kostenTag = this.umsatzNachSteuer * kostenFaktor;
        this.gewinnTag = umsatzTag - kostenTag-SteuerTag;
    }
    public double roundThree(double wert){
        return Math.round(wert*100.0) / 100.0;
    }

    public void ausgabe(PrintWriter out){
        out.println("Umsatz: " + roundThree(getUmsatzTag()) + " EUR\n");
        out.println("Kosten: " + roundThree(getKostenTag()) + " EUR\n" );
        out.println("Gewinn:"  + roundThree(getGewinnTag())+ " EUR\n");
        out.println("Steuer:"  + roundThree(getSteuerTag())+ " EUR");
    }
    public double getUmsatzTag() { return umsatzTag; }
    public double getKostenTag() { return kostenTag; }
    public double getGewinnTag() { return gewinnTag; }
    public double getSteuerTag() { return SteuerTag; }
    public double getKostenFaktor() { return kostenFaktor; }



}
