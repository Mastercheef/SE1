package com.team11.Parkhaus;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.team11.Parkhaus.Kunden.Abonnent;
import com.team11.Parkhaus.Kunden.Kunde;
import com.team11.Parkhaus.Kunden.Standard;

import java.text.SimpleDateFormat;
import java.util.*;

public class Charts {
    public String getDiagram(List<CarIF> cars) {
        JsonObject json = new JsonObject();
        JsonObject dataDurations = new JsonObject();
        JsonObject dataPrices = new JsonObject();
        JsonArray jArray = new JsonArray();

        JsonArray licencePlates = new JsonArray();
        JsonArray durations = new JsonArray();
        JsonArray prices = new JsonArray();

        for (String s : Car.licencePlateArray(cars)) {
            licencePlates.add(s);
        }
        for (double d : Car.durationArray(cars)) {
            durations.add(d);
        }
        for (double p : Car.priceArray(cars)) {
            prices.add(p);
        }

        dataDurations.add("x", licencePlates);
        dataDurations.add("y", durations);
        dataDurations.addProperty("type", "bar");
        dataDurations.addProperty("name", "Dauer");

        dataPrices.add("x", licencePlates);
        dataPrices.add("y", prices);
        dataPrices.addProperty("type", "bar");
        dataPrices.addProperty("name", "Preis");

        jArray.add(dataDurations);
        jArray.add(dataPrices);

        json.add("data", jArray);
        return json.toString();
    }

    public String getCarTypeDiagram(List<CarIF> cars) {
        int suv = 0, limousine = 0, kombi = 0;
        String[] carTypeArray = Car.carTypeArray(cars);
        for (String c : carTypeArray) {
            if (c.equals("SUV")) suv++;
            if (c.equals("Limousine")) limousine++;
            if (c.equals("Kombi")) kombi++;
        }

        JsonObject json = new JsonObject();
        JsonArray data = new JsonArray();
        JsonObject dataE = new JsonObject();
        JsonArray labels = new JsonArray();
        JsonArray values = new JsonArray();

        labels.add("SUV");
        labels.add("Limousine");
        labels.add("Kombi");

        values.add(suv);
        values.add(limousine);
        values.add(kombi);

        dataE.add("labels", labels);
        dataE.add("values", values);
        dataE.addProperty("type", "pie");
        dataE.addProperty("name", "Typ");
        data.add(dataE);

        json.add("data", data);
        return json.toString();
    }

    public String getAuslastungDiagramm(List<String[]> auslastungsListe) {
        JsonObject json = new JsonObject();
        JsonObject auslastungJson = new JsonObject();
        JsonArray jArray = new JsonArray();

        JsonArray prozent = new JsonArray();
        JsonArray zeit = new JsonArray();

        for (String[] p : auslastungsListe) {
            prozent.add(Integer.parseInt(p[1]));

            SimpleDateFormat date = new SimpleDateFormat("MM-dd HH:mm:ss:SS");
            date.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            Date time = new Date((Long.parseLong(p[0])));
            String java_date = date.format(time);
            zeit.add(java_date);
        }

        auslastungJson.add("x", zeit);
        auslastungJson.add("y", prozent);
        auslastungJson.addProperty("type", "line");
        auslastungJson.addProperty("name", "Maximale Auslastung");

        jArray.add(auslastungJson);
        json.add("data", jArray);
        return json.toString();
    }

    public String getCustomerTypeDiagram(List<Ticket> tickets) {
        int abonnent = 0, standard = 0;
        for (Ticket ticket : tickets) {
            Kunde customer = ticket.getCustomer();
            if (customer instanceof Abonnent) abonnent++;
            if (customer instanceof Standard) standard++;
        }

        JsonObject json = new JsonObject();
        JsonArray data = new JsonArray();
        JsonObject dataE = new JsonObject();
        JsonArray labels = new JsonArray();
        JsonArray values = new JsonArray();

        labels.add("Abonnent");
        labels.add("Standard");

        values.add(abonnent);
        values.add(standard);

        dataE.add("labels", labels);
        dataE.add("values", values);
        dataE.addProperty("type", "pie");
        dataE.addProperty("name", "Typ");
        data.add(dataE);

        json.add("data", data);
        return json.toString();
    }
}
