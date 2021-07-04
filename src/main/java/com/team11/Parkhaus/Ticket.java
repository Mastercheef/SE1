package com.team11.Parkhaus;

import com.team11.Parkhaus.Kunden.Abonnent;
import com.team11.Parkhaus.Kunden.Kunde;
import com.team11.Parkhaus.Kunden.Standard;

public class Ticket {
    private final String id;
    private final int nr;
    private final long arrival;
    private final long departure;
    private final String licensePlate;
    private final String vehicleType;
    private final float price;
    private final Kunde customer;

    public Ticket(CarIF car, Kunde customer) {
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

    public float getPrice() {
        return price;
    }

    public int getNr() {
        return nr;
    }

    public Kunde getCustomer() {
        return customer;
    }

    public String getAsJson() {
        StringBuilder stringBuilder = new StringBuilder();
        String customerType = "";
        if (this.customer instanceof Standard) {
            customerType = "Standard";
        } else if (this.customer instanceof Abonnent) {
            customerType = "Abonnent";
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
