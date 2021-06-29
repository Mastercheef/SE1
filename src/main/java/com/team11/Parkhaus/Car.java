package com.team11.Parkhaus;

import java.util.Arrays;
import java.util.List;

public class Car implements CarIF {
    private boolean isParking;
    private int nr;
    private String arrival;
    private String licencePlate;
    private String ticketId;
    private String color;
    private String carType;
    private float duration;
    private float price;
    private int space;
    private String clientType;


    Car(String licensePlate, String ticketId, String color, String carType, String nr, String arrival, String space, String clientType) {
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


    public static double[] durationArray(List<CarIF> cars) {
        return cars.stream().filter(car -> !car.isParking()).mapToDouble(CarIF::getDuration).toArray();
    }


    public static double[] priceArray(List<CarIF> cars) {
        return cars.stream().filter(car -> !car.isParking()).mapToDouble(CarIF::getPrice).toArray();
    }


    public static String[] carTypeArray(List<CarIF> cars) {
        return cars.stream().map(CarIF::getCarType).toArray(String[]::new);
    }


    public static String[] ticketIdArray(CarIF[] cars) {
        String[] ticketIdArray = new String[cars.length];
        for (int i=0; i<cars.length; i++) {
            ticketIdArray[i] = cars[i].getTicketId();
        }
        return ticketIdArray;
    }

    public static String[] licencePlateArray(List<CarIF> cars) {
        return cars.stream().filter(car -> !car.isParking()).map(CarIF::getLicencePlate).toArray(String[]::new);
    }

    public static String getSavedCarsCSV(List<CarIF> cars) {
        // Nr/Timer/Duration/Price/Hash/Color/Space/client_category/vehicle_type/license
        StringBuilder csv = new StringBuilder();
        for (CarIF car : cars) {
            int nr = car.getNr();
            String timer = car.getArrival();
            int duration = (int) (car.getDuration()*60);
            int price = (int) (car.getPrice()*100);
            String ticketId = car.getTicketId();
            String color = car.getColor();
            int space = car.getSpace();
            String client_category = car.getClientType();
            String license = car.getLicencePlate();

            csv.append(",")
                .append(nr)
                .append("/")
                .append(timer)
                .append("/")
                .append(duration)
                .append("/")
                .append(price)
                .append("/")
                .append(ticketId)
                .append("/")
                .append(color)
                .append("/")
                .append(space)
                .append("/")
                .append(client_category)
                .append("/")
                .append(license);
        }
        return csv.length() > 0 ? csv.substring(1) : csv.toString();
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
    public float getDuration() { return this.duration/60; }


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
}