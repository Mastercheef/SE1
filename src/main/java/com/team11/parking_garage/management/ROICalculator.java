/**
 * @author: ecetin2s
 */

package com.team11.parking_garage.management;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ROICalculator {
    private final String dailyProfit;
    private final String investment;
    private final String share;
    private final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    public ROICalculator(String investment, String dailyProfit, String share) {
        this.dailyProfit = dailyProfit;
        this.investment = investment;
        this.share = share;
    }

    private BigDecimal extrapolationYear() {
        return new BigDecimal(dailyProfit).multiply(BigDecimal.valueOf(365)).multiply(new BigDecimal(share));
    }

    public String returnInvest() {
        BigDecimal roiFloating = extrapolationYear().divide(new BigDecimal(investment), mc);
        return roiFloating.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String amortisationMonths() {
        BigDecimal monthly = extrapolationYear().divide(BigDecimal.valueOf(12), mc);
        return new BigDecimal(investment).divide(monthly, mc).setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String amortisationYears() {
       return new BigDecimal(investment).divide(extrapolationYear(), mc).setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String getAsJson() {
        return "{" +
                "\"roi\": " + returnInvest() + "," +
                "\"months\": " + amortisationMonths() + "," +
                "\"years\": " + amortisationYears() +
                "}";
    }
}
