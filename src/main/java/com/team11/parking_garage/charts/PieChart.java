package com.team11.parking_garage.charts;

import com.google.gson.JsonArray;

public abstract class PieChart extends Chart {
    public PieChart(String chartTitle) {
        super(chartTitle);
    }

    protected JsonArray createValuesArray(int[] array) {
        JsonArray values = new JsonArray();
        for (int i : array) {
            values.add(i);
        }
        return values;
    }

    protected JsonArray createLabelArray(String[] array) {
        JsonArray labels = new JsonArray();
        for (String label : array) {
            labels.add(label);
        }
        return labels;
    }
}
