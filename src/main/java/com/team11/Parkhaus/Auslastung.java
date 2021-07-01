package com.team11.Parkhaus;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.util.Date;
import java.util.List;

public class Auslastung {
    float maxCars;
    int default_max;

    Auslastung(int default_max){
        this.default_max = default_max;
    }

    public int getAuslastung(List<CarIF> cars, ServletContext context) {
        if (context.getAttribute("cfg_max") == null) {
            maxCars = default_max;
        } else {
            maxCars = Integer.parseInt((String)(context.getAttribute("cfg_max")));
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
