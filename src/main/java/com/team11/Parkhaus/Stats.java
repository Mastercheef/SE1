package com.team11.Parkhaus;

import java.util.Arrays;

public class Stats {
    public float getSum(CarIF[] cars) {
        return (float) Arrays.stream(cars)
                .filter(car -> !car.isParking())
                .mapToDouble(car -> car.getPrice())
                .sum()/100;
    }

    public float getAvg(CarIF[] cars) {
        return (float) Arrays.stream(cars)
                .filter(car -> !car.isParking())
                .mapToDouble(car -> car.getPrice())
                .average().orElse(0.0)/100;
    }

    public int getCarCount(CarIF[] cars) {
        return (int) Arrays.stream(cars)
                .filter(car -> !car.isParking())
                .count();
    }
}
