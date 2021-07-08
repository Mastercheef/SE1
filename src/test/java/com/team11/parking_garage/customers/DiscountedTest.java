package com.team11.parking_garage.customers;

import com.team11.parking_garage.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscountedTest {
    Discounted c1;
    Discounted c2;
    Discounted c3;
    List<Ticket> tickets = new ArrayList<>();
    private final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    @BeforeEach
    void setUp() {
        c1 = new Discounted(1, "Student");
        c2 = new Discounted(1, "Familie");
        c3 = new Discounted(1, "Senior");
    }

    @Test
    void calculatePrice() {
        assertEquals(new BigDecimal(1200)
                .divide(BigDecimal.valueOf(100), mc)
                .multiply(BigDecimal.valueOf(0.85))
                .setScale(2, RoundingMode.HALF_UP),
                c1.calculatePrice(tickets, BigDecimal.valueOf(1200))
        );
    }

    @Test
    void getType() {
        assertEquals("Student", c1.getType());
        assertEquals("Familie", c2.getType());
        assertEquals("Senior", c3.getType());
    }
}