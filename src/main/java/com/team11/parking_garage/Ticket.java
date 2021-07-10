package com.team11.parking_garage;

import com.team11.parking_garage.customers.Subscriber;
import com.team11.parking_garage.customers.Customer;
import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Standard;

import java.math.BigDecimal;

/**
 * @author: mhoens2s
 */
public class Ticket {
    private final String id;
    private final int nr;
    private final long arrival;
    private final long departure;
    private final String licensePlate;
    private final String vehicleType;
    private final BigDecimal price;
    private final Customer customer;

    /**
     * @author: mhoens2s
     */
    public Ticket(CarIF car, Customer customer) {
        this.id = car.getTicketId();
        this.nr = car.getNr();
        this.arrival = car.getArrival();
        this.departure = car.getDeparture();
        this.licensePlate = car.getLicencePlate();
        this.vehicleType = car.getCarType();
        this.price = car.getPrice();
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public long getArrival() {
        return arrival;
    }

    public long getDeparture() {
        return departure;
    }

    public long getDuration() {
        return departure - arrival;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getNr() {
        return nr;
    }

    /**
     * @author: mhoens2s
     */
    public Customer getCustomer() {
        return customer;
    }

    public String getAsJson() {
        StringBuilder stringBuilder = new StringBuilder();
        String customerType = "";
        if (this.customer instanceof Standard) {
            customerType = "Standard";
        } else if (this.customer instanceof Subscriber) {
            customerType = "Abonnent";
        } else if (this.customer instanceof Discounted) {
            customerType = ((Discounted) this.customer).getType();
        }

        stringBuilder
                .append("{")
                .append("\"nr\": ").append(this.getNr()).append(",")
                .append("\"arrival\": ").append(this.getArrival()).append(",")
                .append("\"departure\": ").append(this.getDeparture()).append(",")
                .append("\"duration\": ").append(this.getDuration()).append(",")
                .append("\"licensePlate\": \"").append(this.licensePlate).append("\",")
                .append("\"vehicleType\": \"").append(this.vehicleType).append("\",")
                .append("\"customerType\": \"").append(customerType).append("\",")
                .append("\"price\": ").append(this.getPrice()).append(",")
                .append("\"ticketId\": \"").append(this.getId()).append("\"")
                .append("}");
        return stringBuilder.toString();
    }
}
