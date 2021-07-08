package com.team11.parking_garage.customers;

import com.team11.parking_garage.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StandardTest {
    Standard s1;
    Standard s2;
    Standard s3;
    List<Ticket> tickets = new ArrayList<>();

    @BeforeEach
    void setUp() {
        s1 = new Standard(1);
        s2 = new Standard(2);
        s3 = new Standard(3);
    }

    @Test
    void calculatePrice() {
        assertEquals(BigDecimal.valueOf(12), s1.calculatePrice(tickets, BigDecimal.valueOf(1200)));
        assertEquals(BigDecimal.valueOf(349), s2.calculatePrice(tickets, BigDecimal.valueOf(34890)));
        assertEquals(BigDecimal.valueOf(123), s3.calculatePrice(tickets, BigDecimal.valueOf(12345)));
    }

    @Test
    void getNr() {
        assertEquals(1, s1.getNr());
        assertEquals(2, s2.getNr());
        assertEquals(3, s3.getNr());
    }
}