package com.team11.parking_garage;
import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Standard;
import com.team11.parking_garage.customers.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CarTest {
    CarIF c1, c2 ,c3 ,c4, c5,c6, c7,c8, emptyCar;
    List<CarIF> cars, emptyList;
    List<Ticket> ticketList;


    @BeforeEach
    public void setUp() {
                            //     nr    arrival              ticketId                           color     space  costomertypt  cartypt  license-plate
        String[] paramsC1 = {"enter", "1", "1623766786071","","", "20622e7202ff98f04cce072d21a42387", "#4b96f1", "7", "Standard", "Kombi", "SU-K 12"};
        String[] paramsC2 = {"enter", "2", "1623766789000","","", "114fe2ed725e9988285bbc4c5c8d6145", "#4b96f1", "7", "Standard", "SUV", "SU-L 24"};
        String[] paramsC3 = {"enter", "3", "1623766890000","","", "573466f20334252981478621577421e3", "#f5a852", "7", "Standard", "Limousine", "SU-M 19"};
        String[] paramsC4 = {"enter", "4", "1623766786071","","", "a91b9e388b674143294675ceed1fd68a", "#c86e7f", "7", "Standard", "Kombi", "SU-N 13"};
        String[] paramsC5 = {"enter", "5", "1623766789000","","", "3409fef14239d5e9eefabd63f7819a7d", "#a2caec", "7", "Standard", "SUV", "SU-O 56"};
        String[] empty = {"","0","0","","","","","0","","","",""};
        c1 = new Car(paramsC1, new Standard(1));
        c2 = new Car(paramsC2, new Subscriber(2));
        c3 = new Car(paramsC3, new Standard(3));
        c4 = new Car(paramsC4, new Standard(4));
        c5 = new Car(paramsC5, new Subscriber(5));
        emptyCar = new Car(empty,new Subscriber(0));

        Car c6 = new Car(new String[]{"enter","3","1625744458000","_","_","c8ed72e96795b1970367a5d058457ed9","#2a3e73","1","Familie","Limousine","SU-A 14"}, new Discounted(3, "Student"));
        Car c7 = new Car(new String[]{"enter","4","1625744459000","_","_","c354d2014d1c63c9be162400be104894","#d6a3ea","18","Standard","SUV","SU-J 99"}, new Discounted(4,"Senior"));
        Car c8= new Car(new String[]{"enter","5","1625744460000","_","_","d2227edd8ae39c18375039e14602afb4","#6c5d42","12","Student","SUV","SU-Q 66"}, new Discounted(5, "Familie"));

        c1.leave("480000","4000");
        c2.leave("120000","1000");
        c3.leave("240000","2000");

        emptyList = new ArrayList<CarIF>();
        cars = new ArrayList<CarIF>();
        cars.add(c1);
        cars.add(c2);
        cars.add(c3);
        cars.add(c4);
        cars.add(c5);
    }

    @Test
    public void durationArray() {
        Car car1 = (Car) cars.get(0);
        Car car2 = (Car) cars.get(1);
        Car car5 = (Car) cars.get(4);
        assertFalse(car1.isParking());
        assertFalse(car2.isParking());
        assertTrue(car5.isParking());
        assertEquals(8000d, c1.getDuration());
        assertEquals(2000d, c2.getDuration());
        assertEquals(4000d, c3.getDuration());
        assertArrayEquals(new double[]{8000d, 2000d, 4000d}, Car.durationArray(cars));
        assertArrayEquals(new double[]{},Car.durationArray(emptyList));
    }

    @Test
    public void carTypeArray() {
        String[] carType= {"Kombi", "SUV",  "Limousine", "Kombi" , "SUV"};
        int pos=0;
        for(CarIF car:cars){
            assertEquals(carType[pos++],car.getCarType());
        }
        assertArrayEquals(carType, Car.carTypeArray(cars));
        assertArrayEquals(new String[]{},Car.carTypeArray(emptyList));
    }

    @Test
    public void ticketIdArray() {
        assertArrayEquals(new String[]{
                        "20622e7202ff98f04cce072d21a42387",
                        "114fe2ed725e9988285bbc4c5c8d6145",
                        "573466f20334252981478621577421e3",
                }, Car.ticketIdArray(cars)
        );

        assertArrayEquals(new String[]{},Car.ticketIdArray(emptyList));
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
        assertEquals("Kombi", c1.getCarType());
        assertEquals("SUV", c2.getCarType());
        assertEquals("Limousine", c3.getCarType());
        assertEquals("Kombi", c4.getCarType() );
        assertEquals("SUV", c5.getCarType() );
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
        assertEquals(480000/60f, c1.getDuration());
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
        String[] colorArr= {"#4b96f1", "#4b96f1", "#f5a852", "#c86e7f", "#a2caec"};
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
        int i = 7;
        for(CarIF car:cars){
            assertEquals(7, car.getSpace());
        }
        assertEquals(0, emptyCar.getArrival() );
    }

    @Test
    public void getClientType() {
        for(CarIF car:cars){
            assertEquals("Standard", car.getClientType());
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
