package com.team11.parking_garage.customers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class Customer {
    private final int nr;
    private final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    public Customer(int nr) {
        this.nr = nr;
    }

    public BigDecimal calculatePrice(BigDecimal price) {
        return price
                .divide(BigDecimal.valueOf(100), mc)
                .round(mc);
    }

    public int getNr() {
        return nr;
    }
}
