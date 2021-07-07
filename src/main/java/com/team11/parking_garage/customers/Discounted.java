package com.team11.parking_garage.customers;

import com.team11.parking_garage.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Discounted extends Customer {
    private final String type;

    public Discounted(int nr, String type) {
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
