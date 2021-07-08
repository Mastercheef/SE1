package com.team11.parking_garage;

import com.team11.parking_garage.customers.Subscriber;
import com.team11.parking_garage.customers.Standard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {
    Stats stats;
    List<Ticket> ticketList;

    @BeforeEach
    public void setUp() {
        stats = Stats.getInstance();
        ticketList = new ArrayList<>();
        Car c1 = new Car(new String[]{"","1", "1625669537797","_","_","b308440e557abf7523364dded9692e24","#44c4f1","4","Student","Kombi","SU-F 94"}, new Standard(1));
        Car c2 = new Car(new String[]{"","2","1625669536598","_","_","62f0972e64e5f32484e8d129ebbdb1d9","#f492a5","17","Student","Limousine","SU-Q 57"}, new Subscriber(2));
        Car c3 = new Car(new String[]{"","3","1625669536198","_","_","5a560ae743a686979250b117c95463fb","#9ce696","7","Abonnent","Limousine","SU-U 78"}, new Standard(3));

        ticketList.add(c1.leave("480000","4000"));
        ticketList.add(c2.leave("120000","1000"));
        ticketList.add(c3.leave("240000","2000"));
    }

    @Test
    public void getSum() {
        assertEquals(60f, stats.getSum(ticketList));
    }

    @Test
    public void getAvg() {
        assertEquals(30f,stats.getAvg(ticketList));
    }

    @Test
    public void getCarCount() {
        assertEquals(3,stats.getCarCount(ticketList));
    }
}