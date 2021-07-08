package com.team11.parking_garage.customers;

import java.math.BigDecimal;

public class Subscriber extends Customer {
    public Subscriber(int nr) {
        super(nr);
    }

    @Override
    public BigDecimal calculatePrice(BigDecimal price) {
        return BigDecimal.valueOf(0);
    }
}
