package com.team11.Parkhaus.Management;

public class ROIRechner {
    private double gewinnTag;
    private double investKapital;

    public ROIRechner(double investKapital, double gewinn) {
        this.investKapital = investKapital;
        this.gewinnTag = gewinn;
    }

    public double extrapolationJahr() {
        return this.gewinnTag * 365.0 ;
    }

    // ROI in % pro Jahr.
    // Beteiligungsvariable
    public double returnInvest() {
        double roi = (extrapolationJahr() / investKapital) * 100.0;
        return Math.round(roi * 1000.0) / 1000.0;
    }

    public double amortisationMonat() {
        double gewMonat = extrapolationJahr() / 12;
        double monate = (investKapital / gewMonat) ;
        return Math.round(monate * 100.0) / 100.0;
    }

    public double amortisationJahr() {
        double ret = amortisationMonat() / 12.0;
        if (ret < 1) {
            return ret;
        } else {
            return Math.round((amortisationMonat() / 12.0) * 1000.0 / 1000.0);
        }
    }
}
