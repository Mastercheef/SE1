package com.team11.Parkhaus;

import com.team11.Parkhaus.Kunden.Abonnent;
import com.team11.Parkhaus.Kunden.Standard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {
    Stats stats;
    List<Ticket> ticketList;

    @BeforeEach
    void setUp() {
        stats = new Stats();
        ticketList = new ArrayList<Ticket>();
        CarIF c1, c2, c3;
        c1 = new Car("SU-K 12", "20622e7202ff98f04cce072d21a42387", "#4b96f1", "Kombi", 1, "1623766786071", "7", "_", new Standard(1));
        c2 = new Car("SU-L 24", "114fe2ed725e9988285bbc4c5c8d6145", "#4b96f1", "SUV", 2, "1623766789000", "6", "_", new Abonnent(2, 0));
        c3 = new Car("SU-M 19", "573466f20334252981478621577421e3", "#f5a852", "Limousine", 3, "1623766890000", "5", "_", new Standard(3));

        ticketList.add(c1.leave(ticketList,"480000","4000"));
        ticketList.add(c2.leave(ticketList,"120000","1000"));
        ticketList.add(c3.leave(ticketList,"240000","2000"));
    }

    @Test
    void getSum() {
        assertEquals(60f, stats.getSum(ticketList));
    }

    @Test
    void getAvg() {
        assertEquals(20f,stats.getAvg(ticketList));
    }

    @Test
    void getCarCount() {
        assertEquals(3,stats.getCarCount(ticketList));
    }
}