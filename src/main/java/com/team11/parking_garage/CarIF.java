package com.team11.parking_garage;

import java.math.BigDecimal;
import java.util.List;

public interface CarIF {
    Ticket leave(List<Ticket> tickets, String duration, String price);
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
