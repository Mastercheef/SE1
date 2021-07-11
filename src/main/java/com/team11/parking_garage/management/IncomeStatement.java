package com.team11.parking_garage.management;

import com.team11.parking_garage.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author: ecetin2s
 * @author: mhoens2s
 */
public class IncomeStatement {
    private final BigDecimal turnover;
    private final BigDecimal taxes;
    private final BigDecimal cost;
    private final BigDecimal turnoverAfterTax;
    private final BigDecimal profit;

    /**
     * @author: ecetin2s
     * @author: mhoens2s
     */
    public IncomeStatement(List<Ticket> tickets, String costFactor) {
        long yesterday = LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)).minus(1, ChronoUnit.DAYS).toEpochMilli();
        turnover = tickets.stream().
                    filter(ticket -> ticket.getDeparture() > yesterday)
                    .map(Ticket::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP);

        taxes = turnover.multiply(new BigDecimal("0.19")).setScale(2, RoundingMode.HALF_UP);

        long visitors = tickets.stream().filter(ticket -> ticket.getDeparture() > yesterday).count();
        cost = new BigDecimal(visitors).multiply(new BigDecimal(costFactor)).setScale(2, RoundingMode.HALF_UP);

        turnoverAfterTax = turnover.subtract(taxes);
        profit = turnoverAfterTax.subtract(cost);
    }

    /**
     * @author: ecetin2s
     */
    public String getProfit() {
        return profit.setScale(2, RoundingMode.HALF_UP).toString();
    }

    /**
     * @author: ecetin2s
     */
    public String getAsJson() {
        return "{" +
                "\"turnover\": " + turnover.toString() + "," +
                "\"taxes\": " + taxes.toString() + "," +
                "\"turnoverAfterTax\": " + turnoverAfterTax.toString() + "," +
                "\"cost\": " + cost.toString() + "," +
                "\"profit\": " + profit.toString() +
                "}";
    }
}
