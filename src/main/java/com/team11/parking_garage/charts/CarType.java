package com.team11.parking_garage.charts;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.team11.parking_garage.Car;
import com.team11.parking_garage.CarIF;
import com.team11.parking_garage.customers.Discounted;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarType extends Chart {
    private final List<CarIF> cars;
    private static final Logger logger = Logger.getLogger("parking_garage.charts.CarType");

    public CarType(List<CarIF> cars) {
        super("Fahrzeugtypen");
        this.cars = cars;
    }

    @Override
    protected JsonArray getData() {
        JsonArray data = super.getData();
        JsonObject jsonObject = new JsonObject();

        int[] counts = new int[3];
        String[] carTypeArray = Car.carTypeArray(cars);
        for (String c : carTypeArray) {
            switch (c) {
                case "SUV":
                    counts[0]++;
                    break;
                case "Limousine":
                    counts[1]++;
                    break;
                case "Kombi":
                    counts[2]++;
                    break;
                default:
                    logger.log(Level.INFO, "Unrecognised Car Type: " + c);
                    break;
            }
        }

        JsonArray labels = new JsonArray();
        JsonArray values = new JsonArray();

        labels.add("SUV");
        labels.add("Limousine");
        labels.add("Kombi");

        for (int count : counts) {
            values.add(count);
        }

        jsonObject.add("labels", labels);
        jsonObject.add("values", values);
        jsonObject.addProperty("type", "pie");
        jsonObject.addProperty("name", "Typ");

        data.add(jsonObject);
        return data;
    }
}
