package com.team11.Parkhaus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChartsTest {
    private CarIF[] cars = new Car[3];
    private Charts chart = new Charts();

    @BeforeEach
    void setUp() {
        Car c1 = new Car("SU-K 12", "20622e7202ff98f04cce072d21a42387", "#4b96f1", "Kombi", "1", "1623766786071", "7", "_");
        Car c2 = new Car("SU-L 24", "114fe2ed725e9988285bbc4c5c8d6145", "#4b96f1", "SUV", "2", "1623766789000", "6", "_");
        Car c3 = new Car("SU-M 19", "573466f20334252981478621577421e3", "#f5a852", "Limousine", "3", "1623766890000", "5", "_");
        cars[0] = c1;
        cars[1] = c2;
        cars[2] = c3;
        cars[0].leave("60000", "500");
        cars[2].leave("120000", "1000");
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
                        "\"y\":[5.0,10.0]," +
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
}