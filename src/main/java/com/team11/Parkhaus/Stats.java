package com.team11.Parkhaus;

import java.util.List;

public class Stats {
    public float getSum(List<CarIF> cars) {
        return (float) cars.stream()
                .filter(car -> !car.isParking())
                .mapToDouble(CarIF::getPrice)
                .sum();
    }

    public float getAvg(List<CarIF> cars) {
        return (float) cars.stream()
                .filter(car -> !car.isParking())
                .mapToDouble(CarIF::getPrice)
                .average().orElse(0.0);
    }

    public int getCarCount(List<CarIF> cars) {
        return (int) cars.stream()
                .filter(car -> !car.isParking())
                .count();
    }
}
