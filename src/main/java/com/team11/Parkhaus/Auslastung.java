package com.team11.Parkhaus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Auslastung {
    float maxCars = 10;

    public int getAuslastung(List<CarIF> cars) {
        float currentCars = cars.stream().filter(CarIF::isParking).count();
        return (int) (currentCars / maxCars * 100);
    }

    public List<String[]> setAuslastungNow(List<String[]> auslastungsListe, List<CarIF> cars) {
        Date now = new Date();
        long unixtimestamp = now.getTime();
        String[] stat = new String[] {String.valueOf(unixtimestamp), String.valueOf(this.getAuslastung(cars))};
        auslastungsListe.add(stat);
        return auslastungsListe;
    }
}
