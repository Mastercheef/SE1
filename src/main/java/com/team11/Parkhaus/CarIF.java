package com.team11.Parkhaus;

public interface CarIF {
    void leave(int duration, int price);
    String getCarType();
    int getPrice();
    int getDuration();
    String getTicketId();
    String getLicencePlate();
    String getColor();
}
