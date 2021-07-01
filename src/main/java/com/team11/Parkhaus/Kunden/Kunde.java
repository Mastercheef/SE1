package com.team11.Parkhaus.Kunden;

import com.team11.Parkhaus.Ticket;
import java.util.List;

public abstract class Kunde {
    private final int nr;

    public Kunde(int nr) {
        this.nr = nr;
    }

    public float calculatePrice(List<Ticket> tickets, float price, long departure) {
        return price;
    }

    public int getNr() {
        return nr;
    }
}
