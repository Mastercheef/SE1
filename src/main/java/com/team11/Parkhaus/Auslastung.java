package com.team11.Parkhaus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Auslastung {
    int maxCars = 10;
    public int getAuslasung(CarIF[] cars) {
        return (int) (((float)Arrays.stream(cars)
                                .filter(CarIF::isParking)
                                .count()) / (float)maxCars * 100);
    }

    public List<String[]> setAuslastungNow(List<String[]> auslastungsListe, CarIF[] cars) {
        Date now = new Date();
        long unixtimestamp = now.getTime();
        String[] stat = new String[] {String.valueOf(unixtimestamp), String.valueOf(this.getAuslasung(cars))};
        auslastungsListe.add(stat);
        return auslastungsListe;
    }
}
