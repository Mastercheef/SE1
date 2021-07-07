package com.team11.Parkhaus.Management;

import com.team11.Parkhaus.Ticket;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class IncomeStatement {
    private final BigDecimal turnover;
    private final BigDecimal taxes;
    private final BigDecimal cost;
    private final BigDecimal turnoverAfterTax;
    private final BigDecimal profit;

    public IncomeStatement(List<Ticket> tickets, String costFactor) {
        MathContext mc = new MathContext(3);
        long yesterday = new Date().toInstant().minus(1, ChronoUnit.DAYS).toEpochMilli();
        turnover = tickets.stream().
                    filter(ticket -> ticket.getDeparture() > yesterday)
                    .map(Ticket::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .round(mc);

        taxes = turnover.multiply(new BigDecimal("0.19")).round(mc);

        long visitors = tickets.stream().filter(ticket -> ticket.getDeparture() > yesterday).count();
        cost = new BigDecimal(visitors).multiply(new BigDecimal(costFactor)).round(mc);

        turnoverAfterTax = turnover.subtract(taxes).round(mc);
        profit = turnoverAfterTax.subtract(cost).round(mc);
    }

    public String getProfit() {
        return String.valueOf(profit.floatValue());
    }

    public String getAsJson() {
        return "{" +
                "\"turnover\": " + turnover.floatValue() + "," +
                "\"taxes\": " + taxes.floatValue() + "," +
                "\"turnoverAfterTax\": " + turnoverAfterTax.floatValue() + "," +
                "\"cost\": " + cost.floatValue() + "," +
                "\"profit\": " + profit.floatValue() +
                "}";
    }
}
