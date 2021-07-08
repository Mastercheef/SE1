package com.team11.parking_garage;

import java.math.BigDecimal;

public interface CarIF {
    Ticket leave(String duration, String price);
    String getCarType();
    BigDecimal getPrice();
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
