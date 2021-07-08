package com.team11.parking_garage;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class Stats {
    private static final Stats instance = new Stats();
    private final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    private Stats() {}

    public static Stats getInstance() {
        return instance;
    }

    public float getSum(List<Ticket> tickets) {
        return tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add).round(mc).floatValue();
    }

    public float getAvg(List<Ticket> tickets) {
        long count = tickets.stream().filter(ticket -> ticket.getPrice().floatValue() > 0).count();
        BigDecimal sum = tickets.stream()
                .map(Ticket::getPrice)
                .filter(price -> price.floatValue() > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(count), mc).round(mc).floatValue();
    }

    public int getCarCount(List<Ticket> tickets) {
        return tickets.size();
    }
}
