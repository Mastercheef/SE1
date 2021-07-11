package com.team11.parking_garage.charts;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author: ecetin2s
 * @author: mhoens2s
 */
public class UtilizationChart extends Chart {
    private final List<String[]> utilizationList;

    public UtilizationChart(List<String[]> utilizationList) {
        super("Momentane Auslastung des Parkhauses", "Zeitpunkt der Messung", "Auslastung (in %)");
        this.utilizationList = utilizationList;
    }

    /**
     * @author: ecetin2s
     * @author: mhoens2s
     */
    @Override
    protected JsonArray getData() {
        JsonArray data = super.getData();
        JsonObject jsonObject = new JsonObject();

        JsonArray percent = new JsonArray();
        JsonArray timeJson = new JsonArray();

        for (String[] s : utilizationList) {
            percent.add(Integer.parseInt(s[1]));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss:SS");
            String javaDate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(Long.parseLong(s[0])),
                    ZoneId.systemDefault())
                    .format(formatter);
            timeJson.add(javaDate);
        }

        jsonObject.add("x", timeJson);
        jsonObject.add("y", percent);
        jsonObject.addProperty("type", "line");
        jsonObject.addProperty("name", "Maximale Auslastung");

        data.add(jsonObject);
        return data;
    }
}
