/**
 * @author: ecetin2s
 */

package com.team11.parking_garage.management;

import com.team11.parking_garage.Car;
import com.team11.parking_garage.Ticket;
import com.team11.parking_garage.customers.Standard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author: ecetin2s
 * @author: eauten2s
 */
class IncomeStatementTest {
    private static final String STANDARD = "Standard";
    private static final String ENTER = "enter";

    private IncomeStatement i1;
    private IncomeStatement i2;

    @BeforeEach
    void setUp() {
        String time = String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)).toEpochMilli());

        Car c1 = new Car(new String[]{ENTER,"1",time,"_","_","78477000d3f4486557edd3245febeec0","#03b428","14", STANDARD,"Kombi","SU-B 26"}, new Standard(1));
        Car c2 = new Car(new String[]{ENTER,"2",time,"_","_","f55fb9cbe110192e12c21dc734b6511c","#69524f","4", STANDARD,"SUV","SU-G 93"}, new Standard(2));
        Car c3 = new Car(new String[]{ENTER,"3",time,"_","_","c8ed72e96795b1970367a5d058457ed9","#2a3e73","1", STANDARD,"Limousine","SU-A 14"}, new Standard(3));
        Car c4 = new Car(new String[]{ENTER,"4",time,"_","_","c354d2014d1c63c9be162400be104894","#d6a3ea","18", STANDARD,"SUV","SU-J 99"}, new Standard(4));
        Car c5 = new Car(new String[]{ENTER,"5",time,"_","_","d2227edd8ae39c18375039e14602afb4","#6c5d42","12", STANDARD,"SUV","SU-Q 66"}, new Standard(5));

        List<Ticket> t1 = new ArrayList<>();
        List<Ticket> t2 = new ArrayList<>();

        t1.add(c1.leave("120000", "1000"));
        t1.add(c2.leave("240000", "2000"));
        t1.add(c3.leave("480000", "4000"));
        t2.add(c4.leave("960000", "8000"));
        t2.add(c5.leave("1920000", "16000"));

        i1 = new IncomeStatement(t1, "0.5");
        i2 = new IncomeStatement(t2, "0.75");
    }

    @Test
    void getProfit() {
        assertEquals("55.20", i1.getProfit());
        assertEquals("192.90", i2.getProfit());
    }

    @Test
    void getAsJson() {
        assertEquals(
                "{" +
                        "\"turnover\": 70.00," +
                        "\"taxes\": 13.30," +
                        "\"turnoverAfterTax\": 56.70," +
                        "\"cost\": 1.50," +
                        "\"profit\": 55.20" +
                        "}",
                i1.getAsJson()
        );
        assertEquals(
                "{" +
                        "\"turnover\": 240.00," +
                        "\"taxes\": 45.60," +
                        "\"turnoverAfterTax\": 194.40," +
                        "\"cost\": 1.50," +
                        "\"profit\": 192.90" +
                        "}",
                i2.getAsJson()
        );
    }
}