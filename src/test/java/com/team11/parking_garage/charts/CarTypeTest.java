package com.team11.parking_garage.charts;

import com.team11.parking_garage.Car;
import com.team11.parking_garage.CarIF;
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

class CarTypeTest {
    private static final String STUDENT = "Student";
    private static final String ENTER = "enter";

    List<CarIF> cars = new ArrayList<>();
    CarType carType;
    String layout;
    String data;

    @BeforeEach
    void setUp() {
        String t1 = "1625744456000";
        Car c1 = new Car(new String[]{ENTER,"1",t1,"_","_","78477000d3f4486557edd3245febeec0","#03b428","14", STUDENT,"Kombi","SU-B 26"}, new Standard(1));
        cars.add(c1);

        String t2 = "1625744457000";
        Car c2 = new Car(new String[]{ENTER,"2",t2,"_","_","f55fb9cbe110192e12c21dc734b6511c","#69524f","4", STUDENT,"SUV","SU-G 93"}, new Subscriber(2));
        cars.add(c2);

        String t3 = "1625744458000";
        Car c3 = new Car(new String[]{ENTER,"3",t3,"_","_","c8ed72e96795b1970367a5d058457ed9","#2a3e73","1","Familie","Limousine","SU-A 14"}, new Discounted(3, STUDENT));
        cars.add(c3);

        String t4 = "1625744459000";
        Car c4 = new Car(new String[]{ENTER,"4",t4,"_","_","c354d2014d1c63c9be162400be104894","#d6a3ea","18","Standard","SUV","SU-J 99"}, new Discounted(4,"Senior"));
        cars.add(c4);

        String t5 = "1625744460000";
        Car c5 = new Car(new String[]{ENTER,"5",t5,"_","_","d2227edd8ae39c18375039e14602afb4","#6c5d42","12", STUDENT,"SUV","SU-Q 66"}, new Discounted(5, "Familie"));
        cars.add(c5);

        layout = "{\"title\":" +
                "{\"text\":\"Fahrzeugtypen\"}," +
                "\"font\":{\"color\":\"#2196f3\"}," +
                "\"paper_bgcolor\":\"rgba(0,0,0,0)\"," +
                "\"plot_bgcolor\":\"rgba(0,0,0,0)\"}";

        data = "[{\"labels\":" +
                "[\"SUV\",\"Limousine\",\"Kombi\"]," +
                "\"values\":[3,1,1],\"type\":\"pie\"," +
                "\"name\":\"Typ\"}]";

        carType = new CarType(cars);
    }

    @Test
    void getLayout() {
        assertEquals(
                layout,
                carType.getLayout().toString()
        );
    }

    @Test
    void getData() {
        assertEquals(
                data,
                carType.getData().toString()
        );
    }

    @Test
    void getJson() {
        assertEquals("{\"data\":" +
                        data + "," +
                        "\"layout\":" +
                        layout +
                        "}",
                carType.getJson()
        );
    }

    @RegisterExtension
    LogCapturer logs = LogCapturer.create().captureForLogger("parking_garage.charts.CarType");

    @Test
    void loggerTest() {
        String t6 = "1625744461000";
        Car c6 = new Car(new String[]{ENTER,"6",t6,"_","_","5454bc8efc5a83c0bc5c547e543df689","#613cfb","15", STUDENT,"Test","SU-Q 66"}, new Discounted(6, "Test"));
        cars.add(c6);
        carType = new CarType(cars);
        carType.getJson();

        logs.assertContains("Unrecognised Car Type:");
    }
}