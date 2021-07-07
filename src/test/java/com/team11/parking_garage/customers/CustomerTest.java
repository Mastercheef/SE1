package com.team11.parking_garage.customers;

import com.team11.parking_garage.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private List<Ticket> tickets;
    private Subscriber a1, a2, a3;
    private Standard s1, s2;

    @BeforeEach
    public void setUp() {
        tickets = new ArrayList<>();
        a1 = new Subscriber(1, 0);
        a2 = new Subscriber(2, 3);
        a3 = new Subscriber(3, 5);
        s1 = new Standard(4);
        s2 = new Standard(5);
    }

    @Test
    public void calculatePrice() {
        /*tickets.add(new Ticket("_", 1, 1625000000001L, 1625000000010L, 0f, a1));
        tickets.add(new Ticket("_", 2, 1625000000001L, 1625000000020L, 0f, a2));
        tickets.add(new Ticket("_", 2, 1625000000001L, 1625000000030L, 0f, a2));
        tickets.add(new Ticket("_", 2, 1625000000001L, 1625000000040L, 0f, a2));
        tickets.add(new Ticket("_", 3, 1625000000001L, 1625000000050L, 0f, a3));
        tickets.add(new Ticket("_", 3, 1625000000001L, 1625000000060L, 0f, a3));
        tickets.add(new Ticket("_", 3, 1625000000001L, 1625000000070L, 0f, a3));
        tickets.add(new Ticket("_", 3, 1625000000001L, 1625000000080L, 0f, a3));
        tickets.add(new Ticket("_", 3, 1625000000001L, 1625000000090L, 10f, a3));

        assertEquals(0f, a1.calculatePrice(tickets, 10f, 1625000000099L));
        assertEquals(11f, a2.calculatePrice(tickets, 11f, 1625000000099L));
        assertEquals(0f, a3.calculatePrice(tickets, 10f, 1625000000099L));
        assertEquals(10f, s1.calculatePrice(tickets, 10f, 1625000000099L));
        assertEquals(10f, s2.calculatePrice(tickets, 10f, 1625000000099L));*/
    }

    @Test
    public void getNr() {
        assertEquals(1, a1.getNr());
        assertEquals(2, a2.getNr());
        assertEquals(3, a3.getNr());
        assertEquals(4, s1.getNr());
        assertEquals(5, s2.getNr());
    }
}