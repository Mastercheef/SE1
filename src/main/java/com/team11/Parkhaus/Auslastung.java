package com.team11.Parkhaus;

import java.util.Arrays;
import java.util.Date;

public class Auslastung {
    int maxCars = 10;

    public int getAuslasung(CarIF[] cars) {
        return (int) (((float)Arrays.stream(cars)
                                .filter(CarIF::isParking)
                                .count()) / (float)maxCars * 100);
    }

    public int[] getAuslastung24H(CarIF[] cars) {
        int[] auslastungArray24H = new int[24];
        for (int i=0; i<24; i++) {
            auslastungArray24H[i] = getMaxAuslastungXUhr(cars, i);
        }
        return auslastungArray24H;
    }

    private int getMaxAuslastungXUhr(CarIF[] cars, int x) {
        Date now = new Date();
        long unixtimestamp = now.getTime()/1000;
        int max = 0;
        long start = unixtimestamp - ((x + 1) * 3600L);
        for (int i = 0; i <= 3600; i++) {
            long timeToCheck = start + i;
            int countNow =  (int)Arrays.stream(cars)
                                .filter(car -> Long.parseLong(car.getArrival())/1000L < timeToCheck)
                                .filter(car -> car.isParking()).count() +
                            (int) Arrays.stream(cars)
                                .filter(car -> Long.parseLong(car.getArrival())/1000L < timeToCheck)
                                .filter(car -> Long.parseLong(car.getDeparture())/1000L > timeToCheck).count();
            if (countNow > max) {
                max = countNow;
            }
        }

        return (int)(max / (float) maxCars * 100);
    }
}
