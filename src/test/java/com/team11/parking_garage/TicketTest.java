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
    private static final String enterString = "enter";
    private static final String studentString = "Student";
    private static final String arrivalJson = "\"arrival\": ";
    private static final String departureJson = "\"departure\": ";
    private static final String durationJson = "\"duration\": ";
    private static final String vehicleTypeSuv = "\"vehicleType\": \"SUV\",";

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
        Car c1 = new Car(new String[]{enterString,"1",t1,"_","_","78477000d3f4486557edd3245febeec0","#03b428","14", studentString,"Kombi","SU-B 26"}, new Standard(1));
        Car c2 = new Car(new String[]{enterString,"2",t2,"_","_","f55fb9cbe110192e12c21dc734b6511c","#69524f","4", studentString,"SUV","SU-G 93"}, new Subscriber(2));
        Car c3 = new Car(new String[]{enterString,"3",t3,"_","_","c8ed72e96795b1970367a5d058457ed9","#2a3e73","1","Familie","Limousine","SU-A 14"}, new Discounted(3, studentString));
        Car c4 = new Car(new String[]{enterString,"4",t4,"_","_","c354d2014d1c63c9be162400be104894","#d6a3ea","18","Standard","SUV","SU-J 99"}, new Discounted(4,"Senior"));
        Car c5 = new Car(new String[]{enterString,"5",t5,"_","_","d2227edd8ae39c18375039e14602afb4","#6c5d42","12", studentString,"SUV","SU-Q 66"}, new Discounted(5, "Familie"));

        ticketList = new ArrayList<>();
        ticketList.add(c1.leave(d1,"1000"));
        ticketList.add(c2.leave(d2,"2000"));
        ticketList.add(c3.leave(d3,"4000"));
        ticketList.add(c4.leave(d4, "8000"));
        ticketList.add(c5.leave(d5, "16000"));
    }

    @Test
    public void getId() {
        assertEquals("78477000d3f4486557edd3245febeec0", ticketList.get(0).getId());
        assertEquals("f55fb9cbe110192e12c21dc734b6511c", ticketList.get(1).getId());
        assertEquals("c8ed72e96795b1970367a5d058457ed9", ticketList.get(2).getId());
        assertEquals("c354d2014d1c63c9be162400be104894", ticketList.get(3).getId());
        assertEquals("d2227edd8ae39c18375039e14602afb4", ticketList.get(4).getId());
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
                        arrivalJson + Long.parseLong(t1) + "," +
                        departureJson + (Long.parseLong(t1)+Long.parseLong(d1)) + "," +
                        durationJson + Long.parseLong(d1) + "," +
                        "\"licensePlate\": \"SU-B 26\"," +
                        "\"vehicleType\": \"Kombi\"," +
                        "\"customerType\": \"Standard\"," +
                        "\"price\": 10," +
                        "\"ticketId\": \"78477000d3f4486557edd3245febeec0\"}",
                ticketList.get(0).getAsJson()
        );

        assertEquals(
                "{\"nr\": 2," +
                        arrivalJson + Long.parseLong(t2) + "," +
                        departureJson + (Long.parseLong(t2)+Long.parseLong(d2)) + "," +
                        durationJson + Long.parseLong(d2) + "," +
                        "\"licensePlate\": \"SU-G 93\"," +
                        vehicleTypeSuv +
                        "\"customerType\": \"Abonnent\"," +
                        "\"price\": 0," +
                        "\"ticketId\": \"f55fb9cbe110192e12c21dc734b6511c\"}",
                ticketList.get(1).getAsJson()
        );

        assertEquals(
                "{\"nr\": 3," +
                        arrivalJson + Long.parseLong(t3) + "," +
                        departureJson + (Long.parseLong(t3)+Long.parseLong(d3)) + "," +
                        durationJson + Long.parseLong(d3) + "," +
                        "\"licensePlate\": \"SU-A 14\"," +
                        "\"vehicleType\": \"Limousine\"," +
                        "\"customerType\": \"Student\"," +
                        "\"price\": 34.00," +
                        "\"ticketId\": \"c8ed72e96795b1970367a5d058457ed9\"}",
                ticketList.get(2).getAsJson()
        );

        assertEquals(
                "{\"nr\": 4," +
                        arrivalJson + Long.parseLong(t4) + "," +
                        departureJson + (Long.parseLong(t4)+Long.parseLong(d4)) + "," +
                        durationJson + Long.parseLong(d4) + "," +
                        "\"licensePlate\": \"SU-J 99\"," +
                        vehicleTypeSuv +
                        "\"customerType\": \"Senior\"," +
                        "\"price\": 68.00," +
                        "\"ticketId\": \"c354d2014d1c63c9be162400be104894\"}",
                ticketList.get(3).getAsJson()
        );

        assertEquals(
                "{\"nr\": 5," +
                        arrivalJson + Long.parseLong(t5) + "," +
                        departureJson + (Long.parseLong(t5)+Long.parseLong(d5)) + "," +
                        durationJson + Long.parseLong(d5) + "," +
                        "\"licensePlate\": \"SU-Q 66\"," +
                        vehicleTypeSuv +
                        "\"customerType\": \"Familie\"," +
                        "\"price\": 136.00," +
                        "\"ticketId\": \"d2227edd8ae39c18375039e14602afb4\"}",
                ticketList.get(4).getAsJson()
        );
    }
}