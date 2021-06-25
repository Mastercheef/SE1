package com.team11.Parkhaus;



public class ROIRechner {
    private double gewinn_tag;
    private double umsatz;
    private double invest_kapital;

    public ROIRechner(double invest_kapital, double gewinn){
        this.invest_kapital = invest_kapital;
        this.gewinn_tag = gewinn;
    }

    public double extrapolationJahr(){
        return gewinn_tag *365;
    }

    // ROI in % pro Jahr.
    public double returnInvest(){
        double roi = (extrapolationJahr() / invest_kapital) * 100 ;
        return Math.round(roi*1000.0)/1000.0;
    }

    public double amortisationMonat(){
        return 0 ;
    }





}
