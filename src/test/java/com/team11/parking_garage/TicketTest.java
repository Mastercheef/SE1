package com.team11.parking_garage;

import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Standard;
import com.team11.parking_garage.customers.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketTest {
    private static final String STUDENT = "Student";
    private static final String ENTER = "enter";
    private static final String JSON_ARRIVAL = "\"arrival\": ";
    private static final String JSON_DEPARTURE = "\"departure\": ";
    private static final String JSON_DURATION = "\"duration\": ";

    List<Ticket> ticketList;
    String t1 = "1625744456000";
    String t2 = "1625744457000";
    String t3 = "1625744458000";
    String t4 = "1625744459000";
    String t5 = "1625744460000";

    String d1 = "120000";
    String d2 = "240000";
    String d3 = "480000";
    String d4 = "960000";
    String d5 = "1920000";
    final MathContext mc = new MathContext(4, RoundingMode.HALF_UP);

    @BeforeEach
    public void setUp() {
        Car c1 = new Car(new String[]{ENTER,"1",t1,"_","_","73b782ca92d44d2766a8b25a92cb41c0","#453d38","12",STUDENT,"Kombi","SU-R 65"}, new Standard(1));
        Car c2 = new Car(new String[]{ENTER,"2",t2,"_","_","f5be3194fa5e1717074dfa76914dd464","#b60dc1","3",STUDENT,"SUV","SU-A 43"}, new Subscriber(2));
        Car c3 = new Car(new String[]{ENTER,"3",t3,"_","_","5f8833de9729cf35075f3fe18a96670f","#57bbb3","2",STUDENT,"SUV","SU-V 21"}, new Discounted(3, STUDENT));
        Car c4 = new Car(new String[]{ENTER,"4",t4,"_","_","cfc413b566aa1cbc2ab77b2e5c1339b9","#75dfda","5","Standard","Limousine","SU-M 66"}, new Discounted(4,"Senior"));
        Car c5 = new Car(new String[]{ENTER,"5",t5,"_","_","56d3f6307c60f1f3ae172585f7f95dea","#bf88b9","6","Familie","Limousine","SU-Z 34"}, new Discounted(5, "Familie"));

        ticketList = new ArrayList<>();
        ticketList.add(c1.leave(d1,"1000"));
        ticketList.add(c2.leave(d2,"2000"));
        ticketList.add(c3.leave(d3,"4000"));
        ticketList.add(c4.leave(d4, "8000"));
        ticketList.add(c5.leave(d5, "16000"));
    }

    @Test
    public void getId() {
        assertEquals("73b782ca92d44d2766a8b25a92cb41c0", ticketList.get(0).getId());
        assertEquals("f5be3194fa5e1717074dfa76914dd464", ticketList.get(1).getId());
        assertEquals("5f8833de9729cf35075f3fe18a96670f", ticketList.get(2).getId());
        assertEquals("cfc413b566aa1cbc2ab77b2e5c1339b9", ticketList.get(3).getId());
        assertEquals("56d3f6307c60f1f3ae172585f7f95dea", ticketList.get(4).getId());
    }

    @Test
    public void getArrival() {
        assertEquals(Long.parseLong(t1), ticketList.get(0).getArrival());
        assertEquals(Long.parseLong(t2), ticketList.get(1).getArrival());
        assertEquals(Long.parseLong(t3), ticketList.get(2).getArrival());
        assertEquals(Long.parseLong(t4), ticketList.get(3).getArrival());
        assertEquals(Long.parseLong(t5), ticketList.get(4).getArrival());
    }

    @Test
    public void getDeparture() {
        assertEquals(Long.parseLong(t1)+Long.parseLong(d1), ticketList.get(0).getDeparture());
        assertEquals(Long.parseLong(t2)+Long.parseLong(d2), ticketList.get(1).getDeparture());
        assertEquals(Long.parseLong(t3)+Long.parseLong(d3), ticketList.get(2).getDeparture());
        assertEquals(Long.parseLong(t4)+Long.parseLong(d4), ticketList.get(3).getDeparture());
        assertEquals(Long.parseLong(t5)+Long.parseLong(d5), ticketList.get(4).getDeparture());
    }

    @Test
    public void getDuration() {
        assertEquals(Long.parseLong(d1), ticketList.get(0).getDuration());
        assertEquals(Long.parseLong(d2), ticketList.get(1).getDuration());
        assertEquals(Long.parseLong(d3), ticketList.get(2).getDuration());
        assertEquals(Long.parseLong(d4), ticketList.get(3).getDuration());
        assertEquals(Long.parseLong(d5), ticketList.get(4).getDuration());
    }

    @Test
    public void getPrice() {
        assertEquals(BigDecimal.valueOf(10), ticketList.get(0).getPrice());
        assertEquals(BigDecimal.valueOf(0), ticketList.get(1).getPrice());
        assertEquals(BigDecimal.valueOf(40).multiply(BigDecimal.valueOf(0.85)).setScale(2), ticketList.get(2).getPrice());
        assertEquals(BigDecimal.valueOf(80).multiply(BigDecimal.valueOf(0.85)).setScale(2), ticketList.get(3).getPrice());
        assertEquals(BigDecimal.valueOf(160).multiply(BigDecimal.valueOf(0.85)).setScale(2), ticketList.get(4).getPrice());
    }

    @Test
    public void getNr() {
        assertEquals(1, ticketList.get(0).getNr());
        assertEquals(2, ticketList.get(1).getNr());
        assertEquals(3, ticketList.get(2).getNr());
        assertEquals(4, ticketList.get(3).getNr());
        assertEquals(5, ticketList.get(4).getNr());
    }

    @Test
    public void getCustomer() {
        assertEquals(1, ticketList.get(0).getCustomer().getNr());
        assertEquals(2, ticketList.get(1).getCustomer().getNr());
        assertEquals(3, ticketList.get(2).getCustomer().getNr());
        assertEquals(4, ticketList.get(3).getCustomer().getNr());
        assertEquals(5, ticketList.get(4).getCustomer().getNr());
    }

    @Test
    public void getAsJson() {
        assertEquals(
                "{\"nr\": 1," +
                        JSON_ARRIVAL + Long.parseLong(t1) + "," +
                        JSON_DEPARTURE + (Long.parseLong(t1)+Long.parseLong(d1)) + "," +
                        JSON_DURATION + Long.parseLong(d1) + "," +
                        "\"licensePlate\": \"SU-R 65\"," +
                        "\"vehicleType\": \"Kombi\"," +
                        "\"customerType\": \"Standard\"," +
                        "\"price\": 10," +
                        "\"ticketId\": \"73b782ca92d44d2766a8b25a92cb41c0\"}",
                ticketList.get(0).getAsJson()
        );

        assertEquals(
                "{\"nr\": 2," +
                        JSON_ARRIVAL + Long.parseLong(t2) + "," +
                        JSON_DEPARTURE + (Long.parseLong(t2)+Long.parseLong(d2)) + "," +
                        JSON_DURATION + Long.parseLong(d2) + "," +
                        "\"licensePlate\": \"SU-A 43\"," +
                        "\"vehicleType\": \"SUV\"," +
                        "\"customerType\": \"Abonnent\"," +
                        "\"price\": 0," +
                        "\"ticketId\": \"f5be3194fa5e1717074dfa76914dd464\"}",
                ticketList.get(1).getAsJson()
        );

        assertEquals(
                "{\"nr\": 3," +
                        JSON_ARRIVAL + Long.parseLong(t3) + "," +
                        JSON_DEPARTURE + (Long.parseLong(t3)+Long.parseLong(d3)) + "," +
                        JSON_DURATION + Long.parseLong(d3) + "," +
                        "\"licensePlate\": \"SU-V 21\"," +
                        "\"vehicleType\": \"SUV\"," +
                        "\"customerType\": \"Student\"," +
                        "\"price\": 34.00," +
                        "\"ticketId\": \"5f8833de9729cf35075f3fe18a96670f\"}",
                ticketList.get(2).getAsJson()
        );

        assertEquals(
                "{\"nr\": 4," +
                        JSON_ARRIVAL + Long.parseLong(t4) + "," +
                        JSON_DEPARTURE + (Long.parseLong(t4)+Long.parseLong(d4)) + "," +
                        JSON_DURATION + Long.parseLong(d4) + "," +
                        "\"licensePlate\": \"SU-M 66\"," +
                        "\"vehicleType\": \"Limousine\"," +
                        "\"customerType\": \"Senior\"," +
                        "\"price\": 68.00," +
                        "\"ticketId\": \"cfc413b566aa1cbc2ab77b2e5c1339b9\"}",
                ticketList.get(3).getAsJson()
        );

        assertEquals(
                "{\"nr\": 5," +
                        JSON_ARRIVAL + Long.parseLong(t5) + "," +
                        JSON_DEPARTURE + (Long.parseLong(t5)+Long.parseLong(d5)) + "," +
                        JSON_DURATION + Long.parseLong(d5) + "," +
                        "\"licensePlate\": \"SU-Z 34\"," +
                        "\"vehicleType\": \"Limousine\"," +
                        "\"customerType\": \"Familie\"," +
                        "\"price\": 136.00," +
                        "\"ticketId\": \"56d3f6307c60f1f3ae172585f7f95dea\"}",
                ticketList.get(4).getAsJson()
        );
    }
}