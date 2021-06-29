package com.team11.Parkhaus;

import com.team11.Parkhaus.Kunden.Abonnent;
import com.team11.Parkhaus.Kunden.Standard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChartsTest {
    private final List<CarIF> cars = new ArrayList<>();
    private final Charts chart = new Charts();
    private final List<Ticket> tickets = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Car c1 = new Car("SU-K 12", "20622e7202ff98f04cce072d21a42387", "#4b96f1", "Kombi", 1, "1623766786071", "7", "_", new Abonnent(1, 0));
        Car c2 = new Car("SU-L 24", "114fe2ed725e9988285bbc4c5c8d6145", "#4b96f1", "SUV", 2, "1623766789000", "6", "_", new Abonnent(2, 0));
        Car c3 = new Car("SU-M 19", "573466f20334252981478621577421e3", "#f5a852", "Limousine", 3, "1623766890000", "5", "_", new Standard(3));
        cars.add(c1);
        cars.add(c2);
        cars.add(c3);
        tickets.add(cars.get(0).leave(tickets,"60000","500"));
        tickets.add(cars.get(2).leave(tickets,"120000","1000"));
    }

    @Test
    void getDiagram() {
        assertEquals(
                "{" +
                        "\"data\":[" +
                        "{" +
                        "\"x\":[\"SU-K 12\",\"SU-M 19\"]," +
                        "\"y\":[1000.0,2000.0]," +
                        "\"type\":\"bar\"," +
                        "\"name\":\"Dauer\"" +
                        "}," +
                        "{" +
                        "\"x\":[\"SU-K 12\",\"SU-M 19\"]," +
                        "\"y\":[0.0,10.0]," +
                        "\"type\":\"bar\"," +
                        "\"name\":\"Preis\"" +
                        "}" +
                        "]" +
                        "}",
                chart.getDiagram(cars));
    }

    @Test
    void getCarTypeDiagram() {
        assertEquals(
                "{" +
                        "\"data\":[" +
                        "{" +
                        "\"labels\":[\"SUV\",\"Limousine\",\"Kombi\"]," +
                        "\"values\":[1,1,1]," +
                        "\"type\":\"pie\"," +
                        "\"name\":\"Typ\"" +
                        "}" +
                        "]" +
                        "}",
                chart.getCarTypeDiagram(cars));
    }

    @Test
    void getCustomerTypeDiagram() {
        assertEquals(
                "{" +
                        "\"data\":[" +
                        "{" +
                        "\"labels\":[\"Abonnent\",\"Standard\"]," +
                        "\"values\":[1,1]," +
                        "\"type\":\"pie\"," +
                        "\"name\":\"Typ\"" +
                        "}" +
                        "]" +
                        "}",
                chart.getCustomerTypeDiagram(tickets)
        );
    }
}