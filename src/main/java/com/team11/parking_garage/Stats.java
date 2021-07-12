package com.team11.parking_garage;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author: ecetin2s
 * @author: eauten2s
 * @author: mhoens2s
 */
public class Stats {
    private static final Stats instance = new Stats();
    private final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    /**
     * @author: mhoens2s
     */
    private Stats() {}

    /**
     * @author: mhoens2s
     */
    public static Stats getInstance() {
        return instance;
    }

    /**
     * @author: ecetin2s
     * @author: eauten2s
     * @author: mhoens2s
     */
    public float getSum(List<Ticket> tickets) {
        return tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add).round(mc).floatValue();
    }

    /**
     * @author: ecetin2s
     * @author: eauten2s
     * @author: mhoens2s
     */
    public float getAvg(List<Ticket> tickets) {
        long count = tickets.stream().filter(ticket -> ticket.getPrice().floatValue() > 0).count();
        BigDecimal sum = tickets.stream()
                .map(Ticket::getPrice)
                .filter(price -> price.floatValue() > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(count), mc).round(mc).floatValue();
    }

    /**
     * @author: ecetin2s
     * @author: mhoens2s
     */
    public int getCarCount(List<Ticket> tickets) {
        return tickets.size();
    }
}
