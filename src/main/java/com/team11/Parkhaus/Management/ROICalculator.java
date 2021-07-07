package com.team11.Parkhaus.Management;

import java.math.BigDecimal;
import java.math.MathContext;

public class ROICalculator {
    private final String dailyProfit;
    private final String investment;
    private final String share;
    private final MathContext mc = new MathContext(3);

    public ROICalculator(String investment, String dailyProfit, String share) {
        this.dailyProfit = dailyProfit;
        this.investment = investment;
        this.share = share;
    }

    private BigDecimal extrapolationYear() {
        return new BigDecimal(dailyProfit).multiply(BigDecimal.valueOf(365)).multiply(new BigDecimal(share));
    }

    public float returnInvest() {
        BigDecimal roiFloating = extrapolationYear().divide(new BigDecimal(investment), mc);
        return roiFloating.multiply(BigDecimal.valueOf(100)).round(mc).floatValue();
    }

    public float amortisationMonths() {
        BigDecimal monthly = extrapolationYear().divide(BigDecimal.valueOf(12));
        return new BigDecimal(investment).divide(monthly, mc).floatValue();
    }

    public float amortisationYears() {
       return new BigDecimal(investment).divide(extrapolationYear(), mc).floatValue();
    }

    public String getAsJson() {
        return "{" +
                "\"roi\": " + returnInvest() + "," +
                "\"months\": " + amortisationMonths() + "," +
                "\"years\": " + amortisationYears() +
                "}";
    }
}
