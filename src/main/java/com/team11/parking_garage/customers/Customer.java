package com.team11.parking_garage.customers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author: ecetin2s
 * @author: mhoens2s
 */
public abstract class Customer {
    private final int nr;
    private final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    /**
     * @author: ecetin2s
     * @author: mhoens2s
     */
    public Customer(int nr) {
        this.nr = nr;
    }

    /**
     * @author: ecetin2s
     * @author: mhoens2s
     */
    public BigDecimal calculatePrice(BigDecimal price) {
        return price
                .divide(BigDecimal.valueOf(100), mc)
                .round(mc);
    }

    /**
     * @author: ecetin2s
     */
    public int getNr() {
        return nr;
    }
}
