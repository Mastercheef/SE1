package com.team11.parking_garage.customers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountedTest {
    Discounted c1;
    Discounted c2;
    Discounted c3;
    private final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    @BeforeEach
    void setUp() {
        c1 = new Discounted(1, "Student");
        c2 = new Discounted(2, "Familie");
        c3 = new Discounted(3, "Senior");
    }

    @Test
    void calculatePrice() {
        assertEquals(new BigDecimal(1200)
                .divide(BigDecimal.valueOf(100), mc)
                .multiply(BigDecimal.valueOf(0.85))
                .setScale(2, RoundingMode.HALF_UP),
                c1.calculatePrice(BigDecimal.valueOf(1200))
        );
    }

    @Test
    void getType() {
        assertEquals("Student", c1.getType());
        assertEquals("Familie", c2.getType());
        assertEquals("Senior", c3.getType());
    }

    void getNr() {
        assertEquals(1, c1.getNr());
        assertEquals(2, c2.getNr());
        assertEquals(3, c3.getNr());
    }
}