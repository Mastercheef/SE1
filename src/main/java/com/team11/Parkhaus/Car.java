package com.team11.Parkhaus;

import java.util.Arrays;

public class Car implements CarIF {
    boolean isParking;
    String licensePlate;
    String ticketId;
    String color;
    String carType;
    float duration;
    float price;


    Car(String licensePlate, String ticketId, String color, String carType){
        this.isParking = true;
        this.licensePlate = licensePlate;
        this.ticketId = ticketId;
        this.color = color;
        this.carType = carType;
        this.price =  -1;
        this.duration = -1;
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


    @Override
    public void leave(int duration, int price) {
        this.isParking = false;
        this.duration = duration;
        this.price = price;
    }


    @Override
    public String getCarType() {
        return this.carType;
    }


    @Override
    public float getPrice() { return this.price/100; }


    @Override
    public float getDuration() { return this.duration/60; }


    @Override
    public String getTicketId() {
        return this.ticketId;
    }


    @Override
    public String getLicencePlate() { return this.licensePlate; }


    @Override
    public String getColor() { return this.color; }

    @Override
    public boolean isParking() { return this.isParking; }
}