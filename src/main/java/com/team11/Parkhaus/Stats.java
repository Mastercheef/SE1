package com.team11.Parkhaus;

import java.util.List;

public class Stats {
    public float getSum(List<Ticket> tickets) {
        return (float) tickets.stream().mapToDouble(Ticket::getPrice).sum();
    }

    public float getAvg(List<Ticket> tickets) {
        return (float) tickets.stream().filter(ticket -> ticket.getPrice() > 0).mapToDouble(Ticket::getPrice).average().orElse(0.0);
    }

    public int getCarCount(List<Ticket> tickets) {
        return tickets.size();
    }
}
