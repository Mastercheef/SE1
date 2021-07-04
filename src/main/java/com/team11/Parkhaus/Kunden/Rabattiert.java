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
    public float calculatePrice(List<Ticket> tickets, float price, long departure) {
        return BigDecimal.valueOf(super.calculatePrice(tickets, price, departure))
                .multiply(BigDecimal.valueOf(0.85))
                .setScale(2, RoundingMode.HALF_UP)
                .floatValue();
    }

    public String getType() {
        return type;
    }
}
