package com.team11.Parkhaus;

import java.util.List;

public interface CarIF {
    Ticket leave(List<Ticket> tickets, String duration, String price);
    String getCarType();
    float getPrice();
    float getDuration();
    String getTicketId();
    String getLicencePlate();
    String getColor();
    boolean isParking();
    int getNr();
    long getArrival();
    int getSpace();
    String getClientType();
    long getDeparture();
}
