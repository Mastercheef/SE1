package com.team11.parking_garage;

import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Standard;
import com.team11.parking_garage.customers.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.ServletContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilizationTest {
    ServletContext context;
    List<CarIF> cars = new ArrayList<>();
    Utilization utilization;
    List<String[]> utilizationTestList;
    List<String[]> utilizationList;
    String unixtimestamp;

    @BeforeEach
    public void setUp() {
        context = new MockServletContext();
        context.setAttribute("cfgMax", "10");

        String t1 = "1625744456000";
        String t2 = "1625744457000";
        String t3 = "1625744458000";
        String t4 = "1625744459000";
        String t5 = "1625744460000";

        Car c1 = new Car(new String[]{"enter","1",t1,"_","_","78477000d3f4486557edd3245febeec0","#03b428","14","Student","Kombi","SU-B 26"}, new Standard(1));
        Car c2 = new Car(new String[]{"enter","2",t2,"_","_","f55fb9cbe110192e12c21dc734b6511c","#69524f","4","Student","SUV","SU-G 93"}, new Subscriber(2));
        Car c3 = new Car(new String[]{"enter","3",t3,"_","_","c8ed72e96795b1970367a5d058457ed9","#2a3e73","1","Familie","Limousine","SU-A 14"}, new Discounted(3, "Student"));
        Car c4 = new Car(new String[]{"enter","4",t4,"_","_","c354d2014d1c63c9be162400be104894","#d6a3ea","18","Standard","SUV","SU-J 99"}, new Discounted(4,"Senior"));
        Car c5 = new Car(new String[]{"enter","5",t5,"_","_","d2227edd8ae39c18375039e14602afb4","#6c5d42","12","Student","SUV","SU-Q 66"}, new Discounted(5, "Familie"));

        utilizationTestList = new ArrayList<>();
        utilizationList = new ArrayList<>();
        utilization = Utilization.getInstance();

        cars.add(c1);
        unixtimestamp = String.valueOf(Instant.now().toEpochMilli());
        utilizationList = utilization.getUtilizationNow(utilizationList, cars, context);
        utilizationTestList.add(new String[]{unixtimestamp, "10"});
        cars.add(c2);
        unixtimestamp = String.valueOf(Instant.now().toEpochMilli());
        utilizationList = utilization.getUtilizationNow(utilizationList, cars, context);
        utilizationTestList.add(new String[]{unixtimestamp, "20"});
        cars.add(c3);
        unixtimestamp = String.valueOf(Instant.now().toEpochMilli());
        utilizationList = utilization.getUtilizationNow(utilizationList, cars, context);
        utilizationTestList.add(new String[]{unixtimestamp, "30"});
        cars.add(c4);
        unixtimestamp = String.valueOf(Instant.now().toEpochMilli());
        utilizationList = utilization.getUtilizationNow(utilizationList, cars, context);
        utilizationTestList.add(new String[]{unixtimestamp, "40"});
        cars.add(c5);
        unixtimestamp = String.valueOf(Instant.now().toEpochMilli());
        utilizationList = utilization.getUtilizationNow(utilizationList, cars, context);
        utilizationTestList.add(new String[]{unixtimestamp, "50"});


    }

    @Test
    public void getUtilization() {
        assertEquals(50, utilization.getUtilization(cars,context));
    }

    @Test
    public void getUtilizationContextIsNull() {
        this.context.setAttribute("cfgMax", null);
        assertEquals(25, utilization.getUtilization(cars,context));
    }

    @Test
    public void getUtilizationNow() {
        for (int i=0; i<utilizationTestList.size(); i++) {
            assertEquals(Long.parseLong(utilizationTestList.get(i)[0]), Long.parseLong(utilizationList.get(i)[0]), 10);
            assertEquals(utilizationTestList.get(i)[1], utilizationList.get(i)[1]);
        }
    }
}