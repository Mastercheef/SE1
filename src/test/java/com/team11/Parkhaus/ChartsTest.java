package com.team11.Parkhaus;

import com.team11.Parkhaus.Kunden.Abonnent;
import com.team11.Parkhaus.Kunden.Standard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class ChartsTest {
    private final List<CarIF> cars = new ArrayList<>();
    private final Charts chart = new Charts();
    private final List<Ticket> tickets = new ArrayList<>();
    private final List<String[]> subscriberAvg = new ArrayList<>();
    private List<String[]> auslastungsListe = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        Car c1 = new Car("SU-K 12", "20622e7202ff98f04cce072d21a42387", "#4b96f1", "Kombi", 1, "1623766786071", "7", "_", new Abonnent(1, 0));
        Car c2 = new Car("SU-L 24", "114fe2ed725e9988285bbc4c5c8d6145", "#4b96f1", "SUV", 2, "1623766789000", "6", "_", new Abonnent(2, 0));
        Car c3 = new Car("SU-M 19", "573466f20334252981478621577421e3", "#f5a852", "Limousine", 3, "1623766890000", "5", "_", new Standard(3));
        cars.add(c1);
        cars.add(c2);
        cars.add(c3);
        tickets.add(cars.get(0).leave(tickets,"60000","500"));
        tickets.add(cars.get(2).leave(tickets,"120000","1000"));

        double avg = tickets.stream().filter(ticket -> ticket.getCustomer() instanceof Abonnent).mapToLong(Ticket::getDuration).average().orElse(-1);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss:SS");
        format.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        subscriberAvg.add(new String[]{String.valueOf(avg), format.format(new Date(1623766990000L))});


        auslastungsListe.add(new String[]{"1625185693464", "10"});
        auslastungsListe.add(new String[]{"1625185719937", "20"});
        auslastungsListe.add(new String[]{"1625185720177", "30"});
    }

    @Test
    public void getDiagram() {
        assertEquals(
                "{" +
                        "\"data\":[" +
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
                chart.getDiagram(cars));
    }

    @Test
    public void getCarTypeDiagram() {
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
    public void getAuslastungDiagramm() {
        assertEquals(
                "{\"data\":[" +
                        "{\"x\":[\"07-02 01:28:13:464\",\"07-02 01:28:39:937\",\"07-02 01:28:40:177\"]," +
                        "\"y\":[10,20,30],\"type\":\"line\",\"name\":\"Maximale Auslastung\"}]," +
                        "\"layout\":{\"title\":{\"text\":\"Momentane Auslastung des Parkhauses\"}," +
                        "\"xaxis\":{\"title\":{\"text\":\"Zeitpunkt der Messung\"}}," +
                        "\"yaxis\":{\"title\":{\"text\":\"Auslastung (in %)\"}}}" +
                        "}",
                chart.getAuslastungDiagramm(auslastungsListe)
        );
    }

    @Test
    public void getCustomerTypeDiagram() {
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

    @Test
    public void getSubscriberDurationsDiagram() {
       assertEquals("{" +
                       "\"data\":[" +
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