package com.team11.parking_garage.charts;

import com.team11.parking_garage.Car;
import com.team11.parking_garage.Ticket;
import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Standard;
import com.team11.parking_garage.customers.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AveragePriceDurationTest {
    private static final String studentString = "Student";
    private static final String enterString = "enter";
    private static final String commaString =  "\",\"";
    private static final String jsonY = "\"y\":";
    private static final String typeLine = "\"type\":\"line\",";

    final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
    AveragePriceDuration averagePriceDuration;
    String times;
    String price;
    String durations;
    String layout;

    @BeforeEach
    void setUp() {
        String t1 = "1625744456000";
        String t2 = "1625744457000";
        String t3 = "1625744458000";
        String t4 = "1625744459000";
        String t5 = "1625744460000";


        Car c1 = new Car(new String[]{enterString,"1",t1,"_","_","78477000d3f4486557edd3245febeec0","#03b428","14", studentString,"Kombi","SU-B 26"}, new Standard(1));
        Car c2 = new Car(new String[]{enterString,"2",t2,"_","_","f55fb9cbe110192e12c21dc734b6511c","#69524f","4", studentString,"SUV","SU-G 93"}, new Subscriber(2));
        Car c3 = new Car(new String[]{enterString,"3",t3,"_","_","c8ed72e96795b1970367a5d058457ed9","#2a3e73","1","Familie","Limousine","SU-A 14"}, new Discounted(3, studentString));
        Car c4 = new Car(new String[]{enterString,"4",t4,"_","_","c354d2014d1c63c9be162400be104894","#d6a3ea","18","Standard","SUV","SU-J 99"}, new Discounted(4,"Senior"));
        Car c5 = new Car(new String[]{enterString,"5",t5,"_","_","d2227edd8ae39c18375039e14602afb4","#6c5d42","12", studentString,"SUV","SU-Q 66"}, new Discounted(5, "Familie"));


        List<Ticket> ticketList = new ArrayList<>();
        String d1 = "120000";
        String d2 = "240000";
        String d3 = "480000";
        String d4 = "960000";
        String d5 = "1920000";

        ticketList.add(c1.leave(d1,"1000"));
        ticketList.add(c2.leave(d2,"2000"));
        ticketList.add(c3.leave(d3,"4000"));
        ticketList.add(c4.leave(d4, "8000"));
        ticketList.add(c5.leave(d5, "16000"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss:SS");

        times = "[\"" +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t1)+Long.parseLong(d1)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t3)+Long.parseLong(d3)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t4)+Long.parseLong(d4)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t5)+Long.parseLong(d5)), ZoneId.systemDefault()).format(formatter) +
                "\"],";

        price = "[" +
                (ticketList.get(0).getPrice()).floatValue()+
                "," +
                (ticketList.get(2).getPrice()
                        .add(ticketList.get(0).getPrice()))
                        .divide(BigDecimal.valueOf(2f), mc) +
                "," +
                (ticketList.get(3).getPrice()
                        .add(ticketList.get(0).getPrice())
                        .add(ticketList.get(2).getPrice()))
                        .divide(BigDecimal.valueOf(3f), mc) +
                "," +
                (ticketList.get(4).getPrice()
                        .add(ticketList.get(0).getPrice())
                        .add(ticketList.get(2).getPrice())
                        .add(ticketList.get(3).getPrice()))
                        .divide(BigDecimal.valueOf(4f), mc) +
                "],";

        durations = "[" +
                (ticketList.get(0).getDuration()) / 60000f +
                "," +
                (ticketList.get(2).getDuration()
                        + ticketList.get(0).getDuration()) / 2 / 60000f +
                "," +
                (ticketList.get(3).getDuration()
                        + ticketList.get(2).getDuration()
                        + ticketList.get(0).getDuration()) / 3 / 60000f +
                "," +
                (ticketList.get(4).getDuration()
                        + ticketList.get(3).getDuration()
                        + ticketList.get(2).getDuration()
                        + ticketList.get(0).getDuration()) / 4 / 60000f +
                "],";

        layout =  "{\"xaxis\":" +
                "{\"title\":{\"text\":\"Zeitpunkt der Messung\"}}," +
                "\"yaxis\":" +
                "{\"title\":{\"text\":\"Parkdauer in Minuten\"}}," +
                "\"title\":{\"text\":\"Durschnittlicher Preis & Parkdauer von Nicht-Abonnenten\"}," +
                "\"font\":{\"color\":\"#2196f3\"}," +
                "\"paper_bgcolor\":\"rgba(0,0,0,0)\"," +
                "\"plot_bgcolor\":\"rgba(0,0,0,0)\"," +
                "\"yaxis2\":" +
                "{\"title\":\"Preis in Euro\"," +
                "\"showgrid\":false," +
                "\"overlaying\":\"y\"," +
                "\"side\":\"right\"}}";

        averagePriceDuration = new AveragePriceDuration(ticketList);
    }

    @Test
    void getData() {
        assertEquals("[{\"x\":" +
                        times +
                        jsonY +
                        price +
                        typeLine +
                        "\"yaxis\":\"y2\"," +
                        "\"name\":\"Preis\"}," +
                        "{\"x\":" +
                        times +
                        jsonY +
                        durations +
                        typeLine +
                        "\"name\":\"Parkdauer\"}]",
                averagePriceDuration.getData().toString());
    }

    @Test
    void getLayout() {
        assertEquals(layout,
                averagePriceDuration.getLayout().toString());
    }

    @Test
    void getJson() {
        assertEquals("{\"data\":" +
                "[{\"x\":" +
                times +
                jsonY +
                price +
                typeLine +
                "\"yaxis\":\"y2\"," +
                "\"name\":\"Preis\"}," +
                "{\"x\":" +
                times +
                jsonY +
                durations +
                typeLine +
                "\"name\":\"Parkdauer\"}]," +
                "\"layout\":" + layout + "}",
                averagePriceDuration.getJson().toString());
    }
}