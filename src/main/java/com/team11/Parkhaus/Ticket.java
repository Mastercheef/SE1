package com.team11.Parkhaus;

import com.team11.Parkhaus.Kunden.Kunde;

public class Ticket {
    private final String id;
    private final int nr;
    private final long arrival;
    private final long departure;
    private final float price;
    private final Kunde customer;

    public Ticket(String id, int nr, long arrival, long departure, float price, Kunde customer) {
        this.id = id;
        this.nr = nr;
        this.arrival = arrival;
        this.departure = departure;
        this.price = price;
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
}
