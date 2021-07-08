package com.team11.parking_garage.charts;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class SubscriberDuration extends Chart {
    private final List<String[]> subscriberAvg;

    public SubscriberDuration(List<String[]> subscriberAvg) {
        super(
                "Durschnittliche Parkdauer der Abonnenten",
                "Zeitpunkt der Messung",
                "Parkdauer in Minuten"
        );
        this.subscriberAvg = subscriberAvg;
    }

    @Override
    protected JsonArray getData() {
        JsonArray data = super.getData();

        JsonObject jsonObject = new JsonObject();
        JsonArray duration = new JsonArray();
        JsonArray time = new JsonArray();

        for (String[] s : subscriberAvg) {
            duration.add(Float.parseFloat(s[0])/1000/60);
            time.add(s[1]);
        }

        jsonObject.add("x", time);
        jsonObject.add("y", duration);
        jsonObject.addProperty("type", "line");
        jsonObject.addProperty("name", "Durchschnittl. Parkdauer der Abonnenten");

        data.add(jsonObject);
        return data;
    }
}
