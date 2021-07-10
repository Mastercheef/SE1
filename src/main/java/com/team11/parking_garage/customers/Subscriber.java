package com.team11.parking_garage.customers;

import java.math.BigDecimal;

/**
 * @author: mhoens2s
 */
public class Subscriber extends Customer {

    /**
     * @author: mhoens2s
     */
    public Subscriber(int nr) {
        super(nr);
    }

    /**
     * @author: mhoens2s
     */
    @Override
    public BigDecimal calculatePrice(BigDecimal price) {
        return BigDecimal.valueOf(0);
    }
}
