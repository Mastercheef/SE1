package com.team11.parking_garage;

import com.team11.parking_garage.customers.Customer;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Car implements CarIF {
    private boolean isParking;
    private final int nr;
    private final long arrival;
    private final String licencePlate;
    private final String ticketId;
    private final String color;
    private final String carType;
    private long duration;
    private BigDecimal price;
    private final int space;
    private final String clientType;
    private final Customer customer;


    public Car(String[] postParams, Customer customer) {
        this.isParking = true;
        this.nr = Integer.parseInt(postParams[1]);
        this.licencePlate = postParams[10];
        this.ticketId = postParams[5];
        this.color = postParams[6];
        this.carType = postParams[9];
        this.price = new BigDecimal(-1);
        this.duration = -1;
        this.arrival = Long.parseLong(postParams[2]);
        this.space = Integer.parseInt(postParams[7]);
        this.clientType = postParams[8];
        this.customer = customer;
    }

    public static double[] durationArray(List<CarIF> cars) {
        return cars.stream().filter(car -> !car.isParking()).mapToDouble(CarIF::getDuration).toArray();
    }

    public static String[] carTypeArray(List<CarIF> cars) {
        return cars.stream().map(CarIF::getCarType).toArray(String[]::new);
    }


    public static String[] ticketIdArray(List<CarIF> cars) {
        int count = (int)cars.stream().filter(car -> !car.isParking()).count();
        String[] ticketIdArray = new String[count];
        for (int i=0; i<count; i++) {
            ticketIdArray[i] = cars.stream().filter(car -> !car.isParking())
                    .collect(Collectors
                    .toList())
                    .get(i)
                    .getTicketId();
        }
        return ticketIdArray;
    }

    public static String getSavedCarsCSV(List<CarIF> cars) {
        // Nr/Timer/Duration/Price/Hash/Color/Space/client_category/vehicle_type/license
        StringBuilder csv = new StringBuilder();
        for (CarIF car : cars) {
            int nr = car.getNr();
            long timer = car.getArrival();
            int duration = (int) (car.getDuration()*60);
            int price = car.getPrice().multiply(BigDecimal.valueOf(100)).intValue();
            String ticketId = car.getTicketId();
            String color = car.getColor();
            int space = car.getSpace();
            String clientCategory = car.getClientType();
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
                .append(clientCategory)
                .append("/")
                .append(license);
        }
        return csv.length() > 0 ? csv.substring(1) : csv.toString();
    }


    @Override
    public Ticket leave(List<Ticket> tickets, String duration, String price) {
        this.isParking = false;
        this.duration = Long.parseLong(duration);
        this.price = customer.calculatePrice(tickets, new BigDecimal(price));
        return new Ticket(this, this.customer);
    }


    @Override
    public String getCarType() {
        return this.carType;
    }


    @Override
    public BigDecimal getPrice() { return this.price; }


    @Override
    public float getDuration() { return this.duration/60f; }


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
    public long getArrival() { return this.arrival; }

    @Override
    public int getSpace() { return this.space; }

    @Override
    public String getClientType() { return this.clientType; }

    public long getDeparture() {
        return this.arrival + this.duration;
    }
}