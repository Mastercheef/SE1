package com.team11.parking_garage;

class ManagerTest {
    private CarIF[] cars = new Car[2];
    double kF1 = 0.01 ;
    double kF2 = 0.8;

    /*
    @BeforeEach
    void setUp() {
        cars[0] =  new Car("", "0", "#", "", "0", "", "1", "_");
        cars[1] =  new Car("", "1", "#", "", "1", "", "2", "_");
        for(CarIF car:cars){ car.leave("0010","10000");} // 100EUR / CAr
        manager.setkostenFaktor(kF1);
        manager.createKGRechner(cars);
        k1 = manager.getKGRechner();

        manager2.setkostenFaktor(kF2);
        manager2.createKGRechner(cars);
        k2 = manager2.getKGRechner();
    }

    @Test
    void setkostenFaktor() {
        assertEquals(manager.getKostenFaktor(), 0.01);
    }

    @Test
    void getKGRechner() {
        KostenGewinnRechner k1 = new KostenGewinnRechner(cars,0.01 );
        assertNotEquals(manager.getKGRechner(), k1);
    }

    @Test
    void getUmsatzTag() {
        assertEquals(k1.getUmsatzTag(), 200);
         }

    @Test
    void getKostenFaktor() {
        assertEquals(k1.getKostenFaktor(), 0.01);
        assertEquals(k2.getKostenFaktor(),0.8);
        }
    @Test
    void getGewinnTag() {
        assertEquals(k1.getGewinnTag(), 160.38);
        assertEquals(k2.getGewinnTag(), 32.40,0.00001);
    }
    @Test
    void getSteuerTag() {
        assertEquals(k1.getSteuerTag(), 38.0);
        assertEquals(k2.getSteuerTag(), 38.0);}
    @Test
    void getKostenTag() {
        assertEquals(k1.getKostenTag(), 160.38*kF1, 0.2);
        assertEquals(k2.getKostenTag(), 129.6);

    }
    */
}