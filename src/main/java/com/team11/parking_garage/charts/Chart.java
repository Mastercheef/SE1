package com.team11.parking_garage.charts;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class Chart {
    private final String chartTitle;
    private final String xTitle;
    private final String yTitle;

    public Chart(String type, String name, String chartTitle, String xTitle, String yTitle) {
        this.chartTitle = chartTitle;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
    }

    public final String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", getData());
        jsonObject.add("layout", getLayout());
        return jsonObject.toString();
    }

    protected JsonArray getData() {
        JsonArray data = new JsonArray();
        return data;
    }

    protected JsonObject getLayout() {
        JsonObject layout = new JsonObject();
        JsonObject title = new JsonObject();
        JsonObject xAxis = new JsonObject();
        JsonObject yAxis = new JsonObject();
        JsonObject xAxisTitle = new JsonObject();
        JsonObject yAxisTitle = new JsonObject();
        JsonObject font = new JsonObject();

        font.addProperty("color", "#2196f3");

        String textKey = "text";
        title.addProperty(textKey, this.chartTitle);

        xAxisTitle.addProperty(textKey, this.xTitle);
        yAxisTitle.addProperty(textKey, this.yTitle);

        String titleKey = "title";
        xAxis.add(titleKey, xAxisTitle);
        yAxis.add(titleKey, yAxisTitle);

        layout.add(titleKey, title);
        layout.add("xaxis", xAxis);
        layout.add("yaxis", yAxis);
        layout.add("font", font);

        String transparentBg = "rgba(0,0,0,0)";
        layout.addProperty("paper_bgcolor", transparentBg);
        layout.addProperty("plot_bgcolor", transparentBg);

        return layout;
    }
}
