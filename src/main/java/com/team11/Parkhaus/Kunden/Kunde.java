package com.team11.Parkhaus.Kunden;

import com.team11.Parkhaus.Ticket;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public abstract class Kunde {
    private final int nr;
    private final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    public Kunde(int nr) {
        this.nr = nr;
    }

    public BigDecimal calculatePrice(List<Ticket> tickets, BigDecimal price, long departure) {
        return price
                .divide(BigDecimal.valueOf(100), mc)
                .round(mc);
    }

    public int getNr() {
        return nr;
    }
}
