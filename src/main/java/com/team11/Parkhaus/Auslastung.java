package com.team11.Parkhaus;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.List;

public class Auslastung {
    float maxCars;
    int defaultMax;

    Auslastung(int defaultMax){
        this.defaultMax = defaultMax;
    }

    public int getAuslastung(List<CarIF> cars, ServletContext context) {
        if (context.getAttribute("cfgMax") == null) {
            maxCars = defaultMax;
        } else {
            maxCars = Integer.parseInt((String)(context.getAttribute("cfgMax")));
        }
        float currentCars = cars.stream().filter(CarIF::isParking).count();
        return (int) (currentCars / maxCars * 100);
    }

    public List<String[]> setAuslastungNow(List<String[]> auslastungsListe, List<CarIF> cars, ServletContext context) {
        Date now = new Date();
        long unixtimestamp = now.getTime();
        String[] stat = new String[] {String.valueOf(unixtimestamp), String.valueOf(this.getAuslastung(cars, context))};
        auslastungsListe.add(stat);
        return auslastungsListe;
    }
}
