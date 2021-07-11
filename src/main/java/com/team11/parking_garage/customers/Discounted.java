package com.team11.parking_garage.customers;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: ecetin2s (based on commit f2ab4491)
 * @author: mhoens2s
 */
public class Discounted extends Customer {
    private final String type;

    /**
     * @author: ecetin2s
     * @author: mhoens2s
     */
    public Discounted(int nr, String type) {
        super(nr);
        this.type = type;
    }

    /**
     * @author: ecetin2s
     * @author: mhoens2s
     */
    @Override
    public BigDecimal calculatePrice(BigDecimal price) {
        return super.calculatePrice(price)
                .multiply(new BigDecimal("0.85"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public String getType() {
        return type;
    }
}
