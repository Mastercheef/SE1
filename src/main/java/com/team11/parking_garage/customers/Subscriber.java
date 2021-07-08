package com.team11.parking_garage.customers;

import com.team11.parking_garage.Ticket;

import java.math.BigDecimal;
import java.util.List;

public class Subscriber extends Customer {
    public Subscriber(int nr) {
        super(nr);
    }

    @Override
    public BigDecimal calculatePrice(List<Ticket> tickets, BigDecimal price) {
        return BigDecimal.valueOf(0);
    }
}
