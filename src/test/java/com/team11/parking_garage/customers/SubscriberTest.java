package com.team11.parking_garage.customers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author: mhoens2s
 */
class SubscriberTest {
    Subscriber c1;
    Subscriber c2;
    Subscriber c3;

    @BeforeEach
    void setUp() {
        c1 = new Subscriber(1);
        c2 = new Subscriber(2);
        c3 = new Subscriber(3);
    }

    @Test
    void calculatePrice() {
        assertEquals(new BigDecimal(0),
                c1.calculatePrice(BigDecimal.valueOf(1200))
        );
    }

    @Test
    void getNr() {
        assertEquals(1, c1.getNr());
        assertEquals(2, c2.getNr());
        assertEquals(3, c3.getNr());
    }
}
