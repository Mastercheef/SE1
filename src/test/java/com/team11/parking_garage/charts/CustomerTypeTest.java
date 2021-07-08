package com.team11.parking_garage.charts;

import com.team11.parking_garage.Car;
import com.team11.parking_garage.Ticket;
import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Standard;
import com.team11.parking_garage.customers.Subscriber;
import io.github.netmikey.logunit.api.LogCapturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTypeTest {
    private static final String studentString = "Student";
    private static final String enterString = "enter";

    List<Ticket> ticketList;
    CustomerType customerType;
    String layout;
    String data;

    @BeforeEach
    void setUp() {
    String t1 = "1625744456000";
    String t2 = "1625744457000";
    String t3 = "1625744458000";
    String t4 = "1625744459000";
    String t5 = "1625744460000";

    Car c1 = new Car(new String[]{enterString,"1",t1,"_","_","78477000d3f4486557edd3245febeec0","#03b428","14","Standard","Kombi","SU-B 26"}, new Standard(1));
    Car c2 = new Car(new String[]{enterString,"2",t2,"_","_","f55fb9cbe110192e12c21dc734b6511c","#69524f","4","Abonnent","SUV","SU-G 93"}, new Subscriber(2));
    Car c3 = new Car(new String[]{enterString,"3",t3,"_","_","c8ed72e96795b1970367a5d058457ed9","#2a3e73","1",studentString,"Limousine","SU-A 14"}, new Discounted(3, studentString));
    Car c4 = new Car(new String[]{enterString,"4",t4,"_","_","c354d2014d1c63c9be162400be104894","#d6a3ea","18","Senoir","SUV","SU-J 99"}, new Discounted(4,"Senior"));
    Car c5 = new Car(new String[]{enterString,"5",t5,"_","_","d2227edd8ae39c18375039e14602afb4","#6c5d42","12","Familie","SUV","SU-Q 66"}, new Discounted(5, "Familie"));

    ticketList = new ArrayList<>();
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

    layout = "{\"title\":" +
            "{\"text\":\"Kundentypen\"}," +
            "\"font\":{\"color\":\"#2196f3\"}," +
            "\"paper_bgcolor\":\"rgba(0,0,0,0)\"," +
            "\"plot_bgcolor\":\"rgba(0,0,0,0)\"}";

    data = "[{\"labels\":" +
            "[\"Abonnent\",\"Standard\",\"Senior\",\"Student\",\"Familie\"]," +
            "\"values\":[1,1,1,1,1],\"type\":\"pie\",\"name\":\"Typ\"}]";

    customerType = new CustomerType(ticketList);
}

    @Test
    void getLayout() {
        assertEquals(
                layout,
                customerType.getLayout().toString()
        );
    }

    @Test
    void getData() {
        assertEquals(
                data,
                customerType.getData().toString()
        );
    }

    @Test
    void getJson() {
        assertEquals("{\"data\":" +
                        data + "," +
                        "\"layout\":" +
                        layout +
                        "}",
                customerType.getJson().toString()
        );
    }

    @RegisterExtension
    LogCapturer logs = LogCapturer.create().captureForLogger("parking_garage.charts.CustomerType");

    @Test
    void loggerTest() {
        String t6 = "1625744461000";
        String d6 = "1000";
        Car c6 = new Car(new String[]{enterString,"6",t6,"_","_","5454bc8efc5a83c0bc5c547e543df689","#613cfb","15",studentString,"Test","SU-Q 66"}, new Discounted(6, "Test"));
        ticketList.add(c6.leave(d6,"100"));
        customerType = new CustomerType(ticketList);
        customerType.getJson();

        logs.assertContains("Unrecognised Customer Type:");
    }
}