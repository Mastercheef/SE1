package com.team11.Parkhaus;

import java.util.Arrays;

public class Car implements CarIF {
    boolean isParking;
    int nr;
    String arrival;
    String licencePlate;
    String ticketId;
    String color;
    String carType;
    float duration;
    float price;
    int space;
    String clientType;


    public Car(String licensePlate, String ticketId, String color, String carType, String nr, String arrival, String space, String clientType){
        this.isParking = true;
        this.nr = Integer.parseInt(nr);
        this.licencePlate = licensePlate;
        this.ticketId = ticketId;
        this.color = color;
        this.carType = carType;
        this.price =  -1;
        this.duration = -1;
        this.arrival = arrival;
        this.space = Integer.parseInt(space);
        this.clientType = clientType;
    }


    public static double[] durationArray(CarIF[] cars){
        return Arrays.stream(cars).filter(car -> !car.isParking()).mapToDouble(CarIF::getDuration).toArray();
    }


    public static double[] priceArray(CarIF[] cars){
        return Arrays.stream(cars).filter(car -> !car.isParking()).mapToDouble(CarIF::getPrice).toArray();
    }


    public static String[] carTypeArray(CarIF[] cars){
        String[] carTypeArray = new String[cars.length];
        for (int i=0; i<cars.length; i++){
            carTypeArray[i] = cars[i].getCarType();
        }
        return carTypeArray;
    }


    public static String[] ticketIdArray(CarIF[] cars){
        String[] ticketIdArray = new String[cars.length];
        for (int i=0; i<cars.length; i++){
            ticketIdArray[i] = cars[i].getTicketId();
        }
        return ticketIdArray;
    }

    public static String[] licencePlateArray(CarIF[] cars){
        return Arrays.stream(cars).filter(car -> !car.isParking()).map(CarIF::getLicencePlate).toArray(String[]::new);
    }

    public static String getSavedCarsCSV(CarIF[] cars){
        // Nr/Timer/Duration/Price/Hash/Color/Space/client_category/vehicle_type/license
        String csv = "";
        for (int i=0; i<cars.length; i++){
            int nr = cars[i].getNr();
            String timer = cars[i].getArrival();
            int duration = (int) (cars[i].getDuration());
            int price = (int) (cars[i].getPrice());
            String ticketId = cars[i].getTicketId();
            String color = cars[i].getColor();
            int space = cars[i].getSpace();
            String client_category = cars[i].getClientType();
            String license = cars[i].getLicencePlate();

            String csv_car = nr+"/"+timer+"/"+duration+"/"+price+"/"+ticketId+"/"
                    +color+"/"+space+"/"+client_category+"/"+license;
            csv += "," + csv_car;

            System.out.println(price);
            System.out.println(cars[i].getPrice());
        }
        return csv.substring(1);
    }


    @Override
    public void leave(String duration, String price) {
        this.isParking = false;
        this.duration = Integer.parseInt(duration);
        this.price = Integer.parseInt(price);
    }


    @Override
    public String getCarType() {
        return this.carType;
    }


    @Override
    public float getPrice() { return this.price/100; }


    @Override
    public float getDuration() { return this.duration/1000; }


    @Override
    public String getTicketId() {
        return this.ticketId;
    }


    @Override
    public String getLicencePlate() { return this.licencePlate; }


    @Override
    public String getColor() { return this.color; }

    @Override
    public boolean isParking() { return this.isParking; }

    @Override
    public int getNr() { return this.nr; }

    @Override
    public String getArrival() { return  this.arrival; }

    @Override
    public int getSpace() { return this.space; }

    @Override
    public String getClientType() { return this.clientType; }

    public String getDeparture() {
        return String.valueOf(Long.parseLong(this.getArrival()) + (int) this.duration);
    }

    @Override
    public String toString() {
        return "LP: " + getLicencePlate()  + " Dur: " + getDuration() + " [Min.]" +  " Price: " + getPrice() + " [EUR] ";
    }
}