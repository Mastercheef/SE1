package com.team11.parking_garage.customers;

import java.math.BigDecimal;

/**
 * @author: ecetin2s (based on commit a1fadbab)
 * @author: mhoens2s
 */
public class Subscriber extends Customer {

    /**
     * @author: ecetin2s
     * @author: mhoens2s
     */
    public Subscriber(int nr) {
        super(nr);
    }

    /**
     * @author: ecetin2s
     * @author: mhoens2s
     */
    @Override
    public BigDecimal calculatePrice(BigDecimal price) {
        return BigDecimal.valueOf(0);
    }
}
