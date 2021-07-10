package com.team11.parking_garage;

import com.team11.parking_garage.customers.Customer;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: mhoens2s
 */
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

    /**
     * @author: mhoens2s
     */
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

    /**
     * @author: mhoens2s
     */
    public static String[] carTypeArray(List<CarIF> cars) {
        return cars.stream().map(CarIF::getCarType).toArray(String[]::new);
    }

    /**
     * @author: mhoens2s
     */
    public static String getSavedCarsCSV(List<CarIF> cars) {
        // Nr/Timer/Duration/Price/Hash/Color/Space/client_category/vehicle_type/license
        StringBuilder csv = new StringBuilder();
        for (CarIF car : cars) {
            int nr = car.getNr();
            long timer = car.getArrival();
            int duration = car.getDuration() != -1 ? (int) (car.getDuration()*60): -1;
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

    /**
     * @author: mhoens2s
     */
    @Override
    public Ticket leave(String duration, String price) {
        this.isParking = false;
        this.duration = Long.parseLong(duration);
        this.price = customer.calculatePrice(new BigDecimal(price));
        return new Ticket(this, this.customer);
    }

    /**
     * @author: mhoens2s
     */
    @Override
    public String getCarType() {
        return this.carType;
    }

    /**
     * @author: mhoens2s
     */
    @Override
    public BigDecimal getPrice() { return this.price; }

    /**
     * @author: mhoens2s
     */
    @Override
    public float getDuration() { return this.duration != -1 ? this.duration/60f : -1; }

    /**
     * @author: mhoens2s
     */
    @Override
    public String getTicketId() {
        return this.ticketId;
    }

    /**
     * @author: mhoens2s
     */
    @Override
    public String getLicencePlate() { return this.licencePlate; }

    /**
     * @author: mhoens2s
     */
    @Override
    public String getColor() { return this.color; }

    /**
     * @author: mhoens2s
     */
    @Override
    public boolean isParking() { return this.isParking; }

    /**
     * @author: mhoens2s
     */
    @Override
    public int getNr() { return this.nr; }

    /**
     * @author: mhoens2s
     */
    @Override
    public long getArrival() { return this.arrival; }

    /**
     * @author: mhoens2s
     */
    @Override
    public int getSpace() { return this.space; }

    /**
     * @author: mhoens2s
     */
    @Override
    public String getClientType() { return this.clientType; }

    /**
     * @author: mhoens2s
     */
    public long getDeparture() {
        return this.arrival + this.duration;
    }
}