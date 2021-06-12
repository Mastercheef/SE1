package com.team11.Parkhaus;

public interface CarIF {
    void leave(int duration, int price);
    String getCarType();
    float getPrice();
    float getDuration();
    String getTicketId();
    String getLicencePlate();
    String getColor();
    boolean isParking();
}
