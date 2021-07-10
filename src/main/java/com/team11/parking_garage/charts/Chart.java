package com.team11.parking_garage.charts;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class Chart {
    private final String chartTitle;
    private String xTitle;
    private String yTitle;

    public Chart(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public Chart(String chartTitle, String xTitle, String yTitle) {
        this.chartTitle = chartTitle;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
    }

    /**
     * @author: mhoens2s
     */
    public final String getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", getData());
        jsonObject.add("layout", getLayout());
        return jsonObject.toString();
    }

    protected JsonArray getData() {
        return new JsonArray();
    }

    /**
     * @author: mhoens2s
     */
    protected JsonObject getLayout() {
        JsonObject layout = new JsonObject();
        JsonObject title = new JsonObject();
        JsonObject font = new JsonObject();

        // local constants to avoid duplicate Strings
        String textKey = "text";
        String titleKey = "title";
        String transparentBg = "rgba(0,0,0,0)";

        font.addProperty("color", "#2196f3");
        title.addProperty(textKey, this.chartTitle);

        if (this.xTitle != null && this.yTitle != null) {
            JsonObject xAxis = new JsonObject();
            JsonObject yAxis = new JsonObject();
            JsonObject xAxisTitle = new JsonObject();
            JsonObject yAxisTitle = new JsonObject();

            xAxisTitle.addProperty(textKey, this.xTitle);
            yAxisTitle.addProperty(textKey, this.yTitle);

            xAxis.add(titleKey, xAxisTitle);
            yAxis.add(titleKey, yAxisTitle);

            layout.add("xaxis", xAxis);
            layout.add("yaxis", yAxis);
        }

        layout.add(titleKey, title);
        layout.add("font", font);

        layout.addProperty("paper_bgcolor", transparentBg);
        layout.addProperty("plot_bgcolor", transparentBg);

        return layout;
    }
}
