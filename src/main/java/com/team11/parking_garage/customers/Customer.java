package com.team11.parking_garage.customers;

import com.team11.parking_garage.Ticket;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public abstract class Customer {
    private final int nr;
    private final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    public Customer(int nr) {
        this.nr = nr;
    }

    public BigDecimal calculatePrice(List<Ticket> tickets, BigDecimal price) {
        return price
                .divide(BigDecimal.valueOf(100), mc)
                .round(mc);
    }

    public int getNr() {
        return nr;
    }
}
