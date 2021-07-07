package com.team11.Parkhaus.Kunden;

import com.team11.Parkhaus.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public abstract class Kunde {
    private final int nr;

    public Kunde(int nr) {
        this.nr = nr;
    }

    public BigDecimal calculatePrice(List<Ticket> tickets, BigDecimal price, long departure) {
        return price
                .divide(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public int getNr() {
        return nr;
    }
}
