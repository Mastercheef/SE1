package com.team11.Parkhaus;

import com.team11.Parkhaus.Kunden.Abonnent;
import com.team11.Parkhaus.Kunden.Standard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    CarIF c1, c2 ,c3 ,c4, c5;
    List<CarIF> cars;
    List<Ticket> ticketList;

    @BeforeEach
    void setUp() {
        c1 = new Car("SU-K 12", "20622e7202ff98f04cce072d21a42387", "#4b96f1", "Kombi", 1, "1623766786071", "7", "_", new Standard(1));
        c2 = new Car("SU-L 24", "114fe2ed725e9988285bbc4c5c8d6145", "#4b96f1", "SUV", 2, "1623766789000", "6", "_", new Abonnent(2, 0));
        c3 = new Car("SU-M 19", "573466f20334252981478621577421e3", "#f5a852", "Limousine", 3, "1623766890000", "5", "_", new Standard(3));
        c4 = new Car("SU-N 13", "a91b9e388b674143294675ceed1fd68a", "#c86e7f", "Kombi", 4, "1623766786071", "4", "_", new Standard(4));
        c5 = new Car("SU-O 56", "3409fef14239d5e9eefabd63f7819a7d", "#a2caec", "SUV", 5, "1623766789000", "3", "_", new Abonnent(5, 0));


        ticketList = new ArrayList<Ticket>();
        c1.leave(ticketList,"480000","4000");
        c2.leave(ticketList,"120000","1000");
        c3.leave(ticketList,"240000","2000");

        cars = new ArrayList<CarIF>();
        cars.add(c1);
        cars.add(c2);
        cars.add(c3);
        cars.add(c4);
        cars.add(c5);
    }

    @Test
    void durationArray() {
        assertArrayEquals(
                new double[]{
                        8000d, 2000d, 4000d
                    },
                Car.durationArray(cars)
        );
    }

    @Test
    void priceArray() {
        assertArrayEquals(
                new double[]{
                        40d, 0d, 20d
                },
                Car.priceArray(cars)
        );
    }

    @Test
    void carTypeArray() {
        assertArrayEquals(
                new String[]{
                        "Kombi", "SUV",  "Limousine", "Kombi" , "SUV"
                },
                Car.carTypeArray(cars)
        );
    }

    @Test
    void ticketIdArray() {
        assertArrayEquals(
                new String[]{
                        "20622e7202ff98f04cce072d21a42387",
                        "114fe2ed725e9988285bbc4c5c8d6145",
                        "573466f20334252981478621577421e3",
                },
                Car.ticketIdArray(cars)
        );
    }

    @Test
    void getSavedCarsCSV() {
        assertEquals(
                "1/1623766786071/480000/4000/20622e7202ff98f04cce072d21a42387/#4b96f1/7/_/SU-K 12," +
                        "2/1623766789000/120000/0/114fe2ed725e9988285bbc4c5c8d6145/#4b96f1/6/_/SU-L 24," +
                        "3/1623766890000/240000/2000/573466f20334252981478621577421e3/#f5a852/5/_/SU-M 19," +
                        "4/1623766786071/-1/-100/a91b9e388b674143294675ceed1fd68a/#c86e7f/4/_/SU-N 13," +
                        "5/1623766789000/-1/-100/3409fef14239d5e9eefabd63f7819a7d/#a2caec/3/_/SU-O 56",
                Car.getSavedCarsCSV(cars)
        );
    }

    @Test
    void leave() {
        Ticket t1 = c4.leave(ticketList, "60000", "100");
        Ticket t2 = c5.leave(ticketList, "1200000", "1000");
        assertEquals(
                60000d,
                t1.getDuration()
        );
        assertEquals(
                1200000d,
                t2.getDuration()
        );

        assertEquals(
                1d,
                t1.getPrice()
        );

        assertEquals(
                0d,
                t2.getPrice()
        );

    }

    @Test
    void getCarType() {
        /* TODO */
    }

    @Test
    void getPrice() {
        /* TODO */
    }

    @Test
    void getDuration() {
        /* TODO */
    }

    @Test
    void getTicketId() {
        /* TODO */
    }

    @Test
    void getLicencePlate() {
        /* TODO */
    }

    @Test
    void getColor() {
        /* TODO */
    }

    @Test
    void isParking() {
        /* TODO */
    }

    @Test
    void getNr() {
        /* TODO */
    }

    @Test
    void getArrival() {
        /* TODO */
    }

    @Test
    void getSpace() {
        /* TODO */
    }

    @Test
    void getClientType() {
        /* TODO */
    }

    @Test
    void getDeparture() {
        /* TODO */
    }
}