package com.team11.parking_garage.charts;

import com.team11.parking_garage.Car;
import com.team11.parking_garage.Ticket;
import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberDurationTest {
    private static final String STUDENT = "Student";
    private static final String ENTER = "enter";
    private static final String COMMA = "\",\"";

    final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
    SubscriberDuration subscriberDuration;
    String times;
    String data;
    String values;
    String layout;

    @BeforeEach
    void setUp() {
        List<Ticket> ticketList = new ArrayList<>();
        String d1 = "120000";
        String d2 = "240000";
        String d3 = "480000";
        String d4 = "960000";
        String d5 = "1920000";

        String t1 = "1625743356000";
        String t2 = "1625743357000";
        String t3 = "1625743358000";
        String t4 = "1625743359000";
        String t5 = "1625743360000";

        Car c1 = new Car(new String[]{ENTER,"1",t1,"_","_","78477000d3f4486557edd3245febeec0","#03b428","14", STUDENT,"Kombi","SU-B 26"}, new Subscriber(1));
        Car c2 = new Car(new String[]{ENTER,"2",t2,"_","_","f55fb9cbe110192e12c21dc734b6511c","#69524f","4", STUDENT,"SUV","SU-G 93"}, new Subscriber(2));
        Car c3 = new Car(new String[]{ENTER,"3",t3,"_","_","c8ed72e96795b1970367a5d058457ed9","#2a3e73","1","Familie","Limousine","SU-A 14"}, new Discounted(3, STUDENT));
        Car c4 = new Car(new String[]{ENTER,"4",t4,"_","_","c354d2014d1c63c9be162400be104894","#d6a3ea","18","Standard","SUV","SU-J 99"}, new Subscriber(4));
        Car c5 = new Car(new String[]{ENTER,"5",t5,"_","_","d2227edd8ae39c18375039e14602afb4","#6c5d42","12", STUDENT,"SUV","SU-Q 66"}, new Subscriber(5));

        List<String[]> subscriberAvg = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss:SS");
        ticketList.add(c1.leave(d1,"1000"));
        double avg = ticketList.stream().filter(ticket -> ticket.getCustomer() instanceof Subscriber).mapToLong(Ticket::getDuration).average().orElse(-1);
        if (avg > -1) {
            subscriberAvg.add(new String[]{String.valueOf(avg), LocalDateTime.now().format(formatter)});
        }
        ticketList.add(c2.leave(d2,"2000"));
        avg = ticketList.stream().filter(ticket -> ticket.getCustomer() instanceof Subscriber).mapToLong(Ticket::getDuration).average().orElse(-1);
        if (avg > -1) {
            subscriberAvg.add(new String[]{String.valueOf(avg), LocalDateTime.now().format(formatter)});
        }
        ticketList.add(c3.leave(d3,"4000"));
        avg = ticketList.stream().filter(ticket -> ticket.getCustomer() instanceof Subscriber).mapToLong(Ticket::getDuration).average().orElse(-1);
        if (avg > -1) {
            subscriberAvg.add(new String[]{String.valueOf(avg), LocalDateTime.now().format(formatter)});
        }
        ticketList.add(c4.leave(d4, "8000"));
        avg = ticketList.stream().filter(ticket -> ticket.getCustomer() instanceof Subscriber).mapToLong(Ticket::getDuration).average().orElse(-1);
        if (avg > -1) {
            subscriberAvg.add(new String[]{String.valueOf(avg), LocalDateTime.now().format(formatter)});
        }
        ticketList.add(c5.leave(d5, "16000"));
        avg = ticketList.stream().filter(ticket -> ticket.getCustomer() instanceof Subscriber).mapToLong(Ticket::getDuration).average().orElse(-1);
        if (avg > -1) {
            subscriberAvg.add(new String[]{String.valueOf(avg), LocalDateTime.now().format(formatter)});
        }

        layout =  "{\"xaxis\":" +
                "{\"title\":{\"text\":\"Zeitpunkt der Messung\"}}," +
                "\"yaxis\":{\"title\":{\"text\":\"Parkdauer in Minuten\"}}," +
                "\"title\":{\"text\":\"Durschnittliche Parkdauer der Abonnenten\"}," +
                "\"font\":{\"color\":\"#2196f3\"}," +
                "\"paper_bgcolor\":\"rgba(0,0,0,0)\"," +
                "\"plot_bgcolor\":\"rgba(0,0,0,0)\"}";

        times = "[\"" +
                subscriberAvg.get(0)[1] +
                COMMA +
                subscriberAvg.get(1)[1] +
                COMMA +
                subscriberAvg.get(2)[1] +
                COMMA +
                subscriberAvg.get(3)[1] +
                COMMA +
                subscriberAvg.get(4)[1] +
                "\"]";

        float averageForTwoCars = (Float.parseFloat(d2)
                + Float.parseFloat(d1)) / 60000f / 2;
        values = "[" +
                (Float.parseFloat(d1)) / 60000f / 1 +
                "," +
                averageForTwoCars +
                "," +
                averageForTwoCars +
                "," +
                (Float.parseFloat(d4)
                        + Float.parseFloat(d2)
                        + Float.parseFloat(d1))/ 60000f / 3 +
                "," +
                (Float.parseFloat(d5)
                        + Float.parseFloat(d4)
                        + Float.parseFloat(d2)
                        + Float.parseFloat(d1))/ 60000f / 4 +
                "]";

        data = "[{\"x\":" + times + ",\"y\":" + values + ",\"type\":\"line\",\"name\":\"Durchschnittl. Parkdauer der Abonnenten\"}]";
        subscriberDuration = new SubscriberDuration(subscriberAvg);
    }

    @Test
    void getLayout() {
        assertEquals(layout, subscriberDuration.getLayout().toString());
    }

    @Test
    void getData() {
        assertEquals(data, subscriberDuration.getData().toString());
    }

    @Test
    void getJson() {
        assertEquals("{\"data\":" +
                data + "," +
                "\"layout\":" +
                layout +
                "}", subscriberDuration.getJson());
    }
}