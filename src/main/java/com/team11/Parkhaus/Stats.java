package com.team11.Parkhaus;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class Stats {
    public float getSum(List<Ticket> tickets) {
        return tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add).round(new MathContext(3)).floatValue();
    }

    public float getAvg(List<Ticket> tickets) {
        long count = tickets.stream().filter(ticket -> ticket.getPrice().floatValue() > 0).count();
        BigDecimal sum = tickets.stream()
                .filter(ticket -> ticket.getPrice().floatValue() > 0)
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(count)).round(new MathContext(3)).floatValue();
    }

    public int getCarCount(List<Ticket> tickets) {
        return tickets.size();
    }
}
