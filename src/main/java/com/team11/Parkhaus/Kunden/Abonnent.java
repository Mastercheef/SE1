package com.team11.Parkhaus.Kunden;

import com.team11.Parkhaus.Ticket;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class Abonnent extends Kunde {
    private final int maxVisits;

    public Abonnent(int nr, int maxVisits) {
        super(nr);
        this.maxVisits = maxVisits;
    }

    @Override
    public BigDecimal calculatePrice(List<Ticket> tickets, BigDecimal price, long departure) {
        if (maxVisits > 0) {
            long yesterday = new Date(departure).toInstant().minus(1, ChronoUnit.DAYS).toEpochMilli();

            return tickets.stream().filter(ticket -> ticket.getNr() == this.getNr() && ticket.getDeparture() > yesterday && ticket.getPrice().floatValue() == 0f).count() < maxVisits ? BigDecimal.valueOf(0) : price;
        }
        return BigDecimal.valueOf(0);
    }
}
