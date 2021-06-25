package com.team11.Parkhaus;

public interface CarIF {
    void leave(String duration, String price);
    String getCarType();
    float getPrice();
    float getDuration();
    String getTicketId();
    String getLicencePlate();
    String getColor();
    boolean isParking();
    int getNr();
    String getArrival();
    int getSpace();
    String getClientType();
    String getDeparture();
    String toString();
}
