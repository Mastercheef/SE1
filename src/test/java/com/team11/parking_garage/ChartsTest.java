package com.team11.parking_garage;

import com.team11.parking_garage.customers.Subscriber;
import com.team11.parking_garage.customers.Standard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class ChartsTest {
    public static final String DATA = "\"data\":[";
    private final List<CarIF> cars = new ArrayList<>();
    private final Charts chart = new Charts();
    private final List<Ticket> tickets = new ArrayList<>();
    private final List<String[]> subscriberAvg = new ArrayList<>();
    private final List<String[]> utilizationList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        Car c1 = new Car(new String[]{"1", "1625669537797","_","_","b308440e557abf7523364dded9692e24","#44c4f1","4","Student","Kombi","SU-F 94"}, new Standard(1));
        Car c2 = new Car(new String[]{"2","1625669536598","_","_","62f0972e64e5f32484e8d129ebbdb1d9","#f492a5","17","Student","Limousine","SU-Q 57"}, new Subscriber(2, 0));
        Car c3 = new Car(new String[]{"3","1625669536198","_","_","5a560ae743a686979250b117c95463fb","#9ce696","7","Abonnent","Limousine","SU-U 78"}, new Standard(3));
        cars.add(c1);
        cars.add(c2);
        cars.add(c3);
        tickets.add(cars.get(0).leave(tickets,"60000","500"));
        tickets.add(cars.get(2).leave(tickets,"120000","1000"));

        double avg = tickets.stream().filter(ticket -> ticket.getCustomer() instanceof Subscriber).mapToLong(Ticket::getDuration).average().orElse(-1);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss:SS");
        format.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        subscriberAvg.add(new String[]{String.valueOf(avg), format.format(new Date(1623766990000L))});


        utilizationList.add(new String[]{"1625185693464", "10"});
        utilizationList.add(new String[]{"1625185719937", "20"});
        utilizationList.add(new String[]{"1625185720177", "30"});
    }

    @Test
    public void getDiagram() {
        assertEquals(
                "{" +
                        DATA +
                        "{" +
                        "\"x\":[\"20622e7202ff98f04cce072d21a42387\",\"573466f20334252981478621577421e3\"]," +
                        "\"y\":[1000.0,2000.0]," +
                        "\"type\":\"bar\"," +
                        "\"name\":\"Dauer\"" +
                        "}," +
                        "{" +
                        "\"x\":[\"20622e7202ff98f04cce072d21a42387\",\"573466f20334252981478621577421e3\"]," +
                        "\"y\":[0.0,10.0]," +
                        "\"type\":\"bar\"," +
                        "\"name\":\"Preis\"" +
                        "}" +
                        "]," +
                        "\"layout\":{\"title\":{\"text\":\"Ueberischt ueber Parkzeit und Preis pro Ticket\"}," +
                        "\"xaxis\":{\"title\":{\"text\":\"Ticket ID\"}}," +
                        "\"yaxis\":{\"title\":{\"text\":\"Preis / Dauer\"}}}" +
                        "}",
                chart.getAveragePriceDuration(tickets));
    }

    @Test
    public void getCarTypeDiagram() {
        assertEquals(
                "{" +
                        DATA +
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
    public void getAuslastungDiagramm() {
        assertEquals(
                "{" +
                        DATA +
                        "{\"x\":[\"07-02 01:28:13:464\",\"07-02 01:28:39:937\",\"07-02 01:28:40:177\"]," +
                        "\"y\":[10,20,30],\"type\":\"line\",\"name\":\"Maximale Auslastung\"}]," +
                        "\"layout\":{\"title\":{\"text\":\"Momentane Auslastung des Parkhauses\"}," +
                        "\"xaxis\":{\"title\":{\"text\":\"Zeitpunkt der Messung\"}}," +
                        "\"yaxis\":{\"title\":{\"text\":\"Auslastung (in %)\"}}}" +
                        "}",
                chart.getUtilizationPlot(utilizationList)
        );
    }

    @Test
    public void getCustomerTypeDiagram() {
        assertEquals(
                "{" +
                        DATA +
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

    @Test
    public void getSubscriberDurationsDiagram() {
       assertEquals("{" +
                       DATA +
                       "{" +
                       "\"x\":[\"06-15 16:23:10:00\"]," +
                       "\"y\":[\"60000.0\"]," +
                       "\"type\":\"line\"," +
                       "\"name\":\"Durchschnittl. Parkdauer der Abonnenten\"" +
                       "}" +
                       "]," +
                       "\"layout\":" +
                       "{" +
                       "\"title\":{\"text\":\"Durschnittliche Parkdauer der Abonnenten\"}," +
                       "\"xaxis\":{\"title\":{\"text\":\"Zeitpunkt der Messung\"}}," +
                       "\"yaxis\":{\"title\":{\"text\":\"Parkdauer (in ms)\"}}}}",
               chart.getSubscriberDurationsDiagram(subscriberAvg));
    }
}