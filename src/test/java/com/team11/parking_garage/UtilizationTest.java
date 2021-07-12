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

/**
 * @author: mhoens2s
 */
class UtilizationTest {
    private static final String ENTER = "enter";
    private static final String STUDENT = "Student";

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

        String t = "16257444";
        String t1 = t + "56000";
        String t2 = t + "57000";
        String t3 = t + "58000";
        String t4 = t + "59000";
        String t5 = t + "60000";

        utilizationTestList = new ArrayList<>();
        utilizationList = new ArrayList<>();
        utilization = Utilization.getInstance();

        cars.add(new Car(new String[]{ENTER,"1",t1,"_","_","36fb95b76d5a5315cb501ca5abadaa53","#8d9171","13", STUDENT,"SUV","SU-U 75"}, new Standard(1)));
        unixtimestamp = String.valueOf(Instant.now().toEpochMilli());
        utilizationList = utilization.getUtilizationNow(utilizationList, cars, context);
        utilizationTestList.add(new String[]{unixtimestamp, "10"});
        cars.add(new Car(new String[]{ENTER,"2",t2,"_","_","0678fd574b14db79f35a5b8dff7f24ed","#bda477","5", STUDENT,"Kombi","SU-Y 4"}, new Subscriber(2)));
        unixtimestamp = String.valueOf(Instant.now().toEpochMilli());
        utilizationList = utilization.getUtilizationNow(utilizationList, cars, context);
        utilizationTestList.add(new String[]{unixtimestamp, "20"});
        cars.add(new Car(new String[]{ENTER,"3",t3,"_","_","517df966c5e5b280f18585efa411601e","#8aaa2f","2","Familie","SUV","SU-B 74"}, new Discounted(3, STUDENT)));
        unixtimestamp = String.valueOf(Instant.now().toEpochMilli());
        utilizationList = utilization.getUtilizationNow(utilizationList, cars, context);
        utilizationTestList.add(new String[]{unixtimestamp, "30"});
        cars.add(new Car(new String[]{ENTER,"4",t4,"_","_","81607fe4e3b6fede95ab8d8ec99b511e","#c4d291","19","Standard","SUV","SU-N 38"}, new Discounted(4,"Familie")));
        unixtimestamp = String.valueOf(Instant.now().toEpochMilli());
        utilizationList = utilization.getUtilizationNow(utilizationList, cars, context);
        utilizationTestList.add(new String[]{unixtimestamp, "40"});
        cars.add(new Car(new String[]{ENTER,"5",t5,"_","_","5ef7f4d31c1b37808dbfe800d29b76af","#f39ca9","20", STUDENT,"Limousine","SU-Y 76"}, new Discounted(5, "Senior")));
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