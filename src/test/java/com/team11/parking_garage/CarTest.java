package com.team11.parking_garage;

import com.team11.parking_garage.customers.Standard;
import com.team11.parking_garage.customers.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: mhoens2s
 */
class CarTest {
    private static final String ENTER = "enter";
    private static final String STANDARD = "Standard";
    private static final String KOMBI = "Kombi";
    private static final String SUV = "SUV";
    private static final String LIMOUSINE = "Limousine";
    private static final String COLOR_1 = "#4b96f1";
    private static final String COLOR_2 = "#f5a852";
    private static final String COLOR_3 = "#a2caec";

    CarIF c1;
    CarIF c2;
    CarIF c3;
    CarIF c4;
    CarIF c5;
    CarIF emptyCar;
    List<CarIF> cars;
    List<CarIF> emptyList;

    @BeforeEach
    public void setUp() {
                            //     nr    arrival              ticketId                           color     space  costomertypt  cartypt  license-plate
        String[] paramsC1 = {ENTER, "1", "1623766786071","","", "20622e7202ff98f04cce072d21a42387", COLOR_1, "7", STANDARD, KOMBI, "SU-K 12"};
        String[] paramsC2 = {ENTER, "2", "1623766789000","","", "114fe2ed725e9988285bbc4c5c8d6145", COLOR_1, "7", STANDARD, SUV, "SU-L 24"};
        String[] paramsC3 = {ENTER, "3", "1623766890000","","", "573466f20334252981478621577421e3", COLOR_2, "7", STANDARD, LIMOUSINE, "SU-M 19"};
        String[] paramsC4 = {ENTER, "4", "1623766786071","","", "a91b9e388b674143294675ceed1fd68a", "#c86e7f", "7", STANDARD, KOMBI, "SU-N 13"};
        String[] paramsC5 = {ENTER, "5", "1623766789000","","", "3409fef14239d5e9eefabd63f7819a7d", COLOR_3, "7", STANDARD, SUV, "SU-O 56"};
        String[] empty = {"","0","0","","","","","0","","","",""};
        c1 = new Car(paramsC1, new Standard(1));
        c2 = new Car(paramsC2, new Subscriber(2));
        c3 = new Car(paramsC3, new Standard(3));
        c4 = new Car(paramsC4, new Standard(4));
        c5 = new Car(paramsC5, new Subscriber(5));
        emptyCar = new Car(empty,new Subscriber(0));


        cars = new ArrayList<>();
        cars.add(c1);
        cars.add(c2);
        cars.add(c3);
        cars.add(c4);
        cars.add(c5);

        cars.get(0).leave("480000","4000");
        cars.get(1).leave("120000","1000");
        cars.get(2).leave("240000","2000");

        emptyList = new ArrayList<>();
    }


    @Test
    public void carTypeArray() {
        String[] carType = {KOMBI, SUV, LIMOUSINE, KOMBI, SUV};
        int pos = 0;
        for (CarIF car : cars) {
            assertEquals(carType[pos++], car.getCarType());
        }
        assertArrayEquals(carType, Car.carTypeArray(cars));
        assertArrayEquals(new String[]{}, Car.carTypeArray(emptyList));
    }

    @Test
    public void getSavedCarsCSV() {
        assertEquals(
                "1/1623766786071/480000/4000/20622e7202ff98f04cce072d21a42387/#4b96f1/7/Standard/SU-K 12," +
                        "2/1623766789000/120000/0/114fe2ed725e9988285bbc4c5c8d6145/#4b96f1/7/Standard/SU-L 24," +
                        "3/1623766890000/240000/2000/573466f20334252981478621577421e3/#f5a852/7/Standard/SU-M 19," +
                        "4/1623766786071/-1/-100/a91b9e388b674143294675ceed1fd68a/#c86e7f/7/Standard/SU-N 13," +
                        "5/1623766789000/-1/-100/3409fef14239d5e9eefabd63f7819a7d/#a2caec/7/Standard/SU-O 56",
                Car.getSavedCarsCSV(cars)
        );
    }

    @Test
    public void leave() {
        Ticket t1 = c4.leave( "60000", "100");
        Ticket t2 = c5.leave( "1200000", "1000");
        assertEquals(60000d, t1.getDuration());
        assertEquals(1200000d, t2.getDuration());
        assertEquals(new BigDecimal(1), t1.getPrice());
        assertEquals(new BigDecimal(0), t2.getPrice());
    }

    @Test
    public void getCarType() {
        assertEquals(KOMBI, c1.getCarType());
        assertEquals(SUV, c2.getCarType());
        assertEquals(LIMOUSINE, c3.getCarType());
        assertEquals(KOMBI, c4.getCarType() );
        assertEquals(SUV, c5.getCarType() );
        assertEquals("", emptyCar.getCarType());
    }

    @Test
    public void getPrice() {
        assertEquals(new BigDecimal(40), c1.getPrice());
        assertEquals(new BigDecimal(0), c2.getPrice());
        assertEquals(new BigDecimal(20), c3.getPrice());
        assertEquals(new BigDecimal(-1),c4.getPrice());
        assertEquals(new BigDecimal(-1),c5.getPrice() );
        assertEquals(new BigDecimal(-1), emptyCar.getPrice());
    }

    @Test
    public void getDuration() {
        assertEquals(480000/60f, cars.get(0).getDuration());
        assertEquals(120000/60f, c2.getDuration());
        assertEquals(240000/60f, c3.getDuration());
        assertEquals(-1.0, c4.getDuration()) ;
        assertEquals(-1.0, c5.getDuration() );
        assertEquals(-1.0, emptyCar.getDuration() );
    }

    @Test
    public void getTicketId() {
        assertEquals( "20622e7202ff98f04cce072d21a42387", c1.getTicketId());
        assertEquals( "114fe2ed725e9988285bbc4c5c8d6145", c2.getTicketId());
        assertEquals( "573466f20334252981478621577421e3", c3.getTicketId());
        assertEquals( "a91b9e388b674143294675ceed1fd68a", c4.getTicketId());
        assertEquals("3409fef14239d5e9eefabd63f7819a7d", c5.getTicketId());
        assertEquals("", emptyCar.getTicketId(), "");
    }

    @Test
    public void getLicencePlate() {
        String[] licensePlateArr= {"SU-K 12", "SU-L 24", "SU-M 19", "SU-N 13", "SU-O 56"};
        int pos=0;
        for(CarIF car:cars){
            assertEquals(licensePlateArr[pos++], car.getLicencePlate());
        }
        assertEquals("", emptyCar.getLicencePlate() );

    }

    @Test
    public void getColor() {
        String[] colorArr= {COLOR_1, COLOR_1, COLOR_2, "#c86e7f", COLOR_3};
        int pos=0;
        for(CarIF car:cars){
            assertEquals(colorArr[pos++], car.getColor());
        }
        assertEquals("", emptyCar.getColor() );
    }

    @Test
    public void isParking() {
        assertFalse(c1.isParking());
        assertFalse(c2.isParking());
        assertFalse(c3.isParking());
        assertTrue(c4.isParking());
        assertTrue(c5.isParking());
        assertTrue(emptyCar.isParking());
    }

    @Test
    public void getNr() {
        Integer[] numberArr = {1,2,3,4,5};
        int pos=0;
        for(CarIF car:cars){
            assertEquals(numberArr[pos++], car.getNr());
        }
        assertEquals(0, emptyCar.getNr());
    }

    @Test
    public void getArrival() {
        long[] arrivalArr= {1623766786071L, 1623766789000L, 1623766890000L, 1623766786071L, 1623766789000L};
        int pos=0;
        for(CarIF car:cars){
            assertEquals(arrivalArr[pos++], car.getArrival());
        }
        assertEquals(0, emptyCar.getArrival());
    }

    @Test
    public void getSpace() {
        for(CarIF car:cars){
            assertEquals(7, car.getSpace());
        }
        assertEquals(0, emptyCar.getArrival() );
    }

    @Test
    public void getClientType() {
        for(CarIF car:cars){
            assertEquals(STANDARD, car.getClientType());
        }
        assertEquals("", emptyCar.getClientType());
    }

    @Test
    public void getDeparture() {
        assertEquals(1623767266071L, c1.getDeparture() );
        assertEquals(1623766909000L, c2.getDeparture() );
        assertEquals(1623767130000L, c3.getDeparture()  );
        assertEquals(1623766786070L, c4.getDeparture() );
        assertEquals(1623766788999L, c5.getDeparture() );
        assertEquals(-1,emptyCar.getDeparture());
    }
}
