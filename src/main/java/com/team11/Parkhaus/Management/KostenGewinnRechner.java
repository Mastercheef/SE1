package com.team11.Parkhaus.Management;

public class KostenGewinnRechner {
    private double umsatzTag, umsatzNachSteuer, kostenTag, gewinnTag, steuerTag;
    private final double MwSt = 0.19;

    public KostenGewinnRechner(float umsatz, double kostenFaktor) {
        this.umsatzTag = umsatz;
        this.steuerTag = this.umsatzTag *  MwSt;
        this.umsatzNachSteuer = this.umsatzTag -  this.steuerTag;
        this.kostenTag = this.umsatzNachSteuer * kostenFaktor;
        this.gewinnTag = umsatzTag - kostenTag - steuerTag;
    }

    public String roundThree(double wert) {
        return String.valueOf(Math.round(wert * 100.0) / 100.0);
    }

    public String[] ausgabe() {
        return new String[] { roundThree(umsatzTag), roundThree(kostenTag), roundThree(gewinnTag), roundThree(steuerTag) };
    }
}
