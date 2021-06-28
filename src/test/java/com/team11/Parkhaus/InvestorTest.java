package com.team11.Parkhaus;

import com.team11.Parkhaus.Investor.Investor;
import com.team11.Parkhaus.Investor.ROIRechner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvestorTest {
    Investor investor, investor2;
    double invest = 10000.0;
    private CarIF[] cars = new Car[5];
    private CarIF[] cars2 = new Car[101];
    private Stats stats = new Stats();


    @BeforeEach
    void setUp() {
        cars[0] =  new Car("SU-K 1", "0", "#4b96f1", "Kombi", "0", "1624711610503", "1", "_");
        cars[1] =  new Car("SU-K 2", "1", "#4b96f1", "Kombi", "1", "1624711610503", "2", "_");
        cars[2] =  new Car("SU-K 3", "2", "#4b96f1", "Kombi", "2", "1624711610503", "3", "_");
        cars[3] =  new Car("SU-K 4", "3", "#4b96f1", "Kombi", "3", "1624711610503", "4", "_");
        cars[4] =  new Car("SU-K 5", "4", "#4b96f1", "Kombi", "4", "1624711610503", "5", "_");

        for(CarIF car:cars){ car.leave("0010","1000"); } // Preis wäre hier 10.00 EUR / Car

        investor = new Investor(cars);
        investor.generateRechner(invest);

        for(int i =0;i<=100;i++){
            cars2[i] = new Car("", "0", "#", "Kombi", "0", "0", "1", "_");
        }

        for(int i =0;i<=100;i++){
            String preis="";
            preis = String.valueOf(i);
            preis+="00";
            cars2[i].leave(preis,preis);
        }
        investor2 = new Investor(cars2);
        investor2.generateRechner(invest);

    }

    @Test
    void getRechner() {
        ROIRechner roi = new ROIRechner(0.0,0.0);
        assertNotEquals(investor.getRechner(),roi);
    }
    @Test
    void getGewinnTag() {
        assertEquals(investor.getGewinnTag(),50.0);
        int c=0;
        for(int i =0;i<=100;i++){c+=i;}
        assertEquals(investor2.getGewinnTag(),c);
    }

    @Test
    void returnInvest(){
        double roiInvestor = (((investor.getGewinnTag()*365.0)/investor.getInvest() ) *100.0);
        assertEquals(investor.getRechner().returnInvest(),roiInvestor,0.3);

        double roiInvestor2 = (((investor2.getGewinnTag()*365.0)/investor2.getInvest() ) *100.0);
        assertEquals(investor2.getRechner().returnInvest(),roiInvestor2,0.1);
    }

    @Test
    void amortisationMonat(){
        double ammoMonat= (10000.0/((50.0*365.0)/12.0));
        assertEquals(investor.getRechner().amortisationMonat(), ammoMonat, 0.1);

        double ammoMonat2= (10000.0/((investor2.getGewinnTag()*365.0)/12.0));
        assertEquals(investor2.getRechner().amortisationMonat(), ammoMonat2, 0.1);
    }

    @Test
    void amortisationJahr(){
        assertEquals(investor.getRechner().amortisationJahr(), investor.getRechner().amortisationMonat()/12);
        assertEquals(investor2.getRechner().amortisationJahr(), investor2.getRechner().amortisationMonat()/12);


    }





}