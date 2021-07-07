package com.team11.Parkhaus.Kunden;

import com.team11.Parkhaus.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Rabattiert extends Kunde {
    private final String type;

    public Rabattiert(int nr, String type) {
        super(nr);
        this.type = type;
    }

    @Override
    public BigDecimal calculatePrice(List<Ticket> tickets, BigDecimal price, long departure) {
        return super.calculatePrice(tickets, price, departure)
                .multiply(new BigDecimal("0.85"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public String getType() {
        return type;
    }
}
