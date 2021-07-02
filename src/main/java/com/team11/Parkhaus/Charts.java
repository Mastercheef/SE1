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

        JsonArray ticketIds = new JsonArray();
        JsonArray durations = new JsonArray();
        JsonArray prices = new JsonArray();

        for (String s : Car.ticketIdArray(cars)) {
            ticketIds.add(s);
        }
        for (double d : Car.durationArray(cars)) {
            durations.add(d);
        }
        for (double p : Car.priceArray(cars)) {
            prices.add(p);
        }

        dataDurations.add("x", ticketIds);
        dataDurations.add("y", durations);
        dataDurations.addProperty("type", "bar");
        dataDurations.addProperty("name", "Dauer");

        dataPrices.add("x", ticketIds);
        dataPrices.add("y", prices);
        dataPrices.addProperty("type", "bar");
        dataPrices.addProperty("name", "Preis");

        JsonObject layout = new JsonObject();
        JsonObject title = new JsonObject();
        JsonObject xAxis = new JsonObject();
        JsonObject yAxis = new JsonObject();
        JsonObject xAxisTitle = new JsonObject();
        JsonObject yAxisTitle = new JsonObject();

        title.addProperty("text", "Ueberischt ueber Parkzeit und Preis pro Ticket");
        xAxisTitle.addProperty("text", "Ticket ID");
        yAxisTitle.addProperty("text", "Preis / Dauer");

        xAxis.add("title", xAxisTitle);
        yAxis.add("title", yAxisTitle);

        layout.add("title", title);
        layout.add("xaxis", xAxis);
        layout.add("yaxis", yAxis);


        jArray.add(dataDurations);
        jArray.add(dataPrices);

        json.add("data", jArray);
        json.add("layout", layout);
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
            String javaDate = date.format(time);
            zeit.add(javaDate);
        }

        auslastungJson.add("x", zeit);
        auslastungJson.add("y", prozent);
        auslastungJson.addProperty("type", "line");
        auslastungJson.addProperty("name", "Maximale Auslastung");

        JsonObject layout = new JsonObject();
        JsonObject title = new JsonObject();
        JsonObject xAxis = new JsonObject();
        JsonObject yAxis = new JsonObject();
        JsonObject xAxisTitle = new JsonObject();
        JsonObject yAxisTitle = new JsonObject();

        title.addProperty("text", "Momentane Auslastung des Parkhauses");
        xAxisTitle.addProperty("text", "Zeitpunkt der Messung");
        yAxisTitle.addProperty("text", "Auslastung (in %)");

        xAxis.add("title", xAxisTitle);
        yAxis.add("title", yAxisTitle);

        layout.add("title", title);
        layout.add("xaxis", xAxis);
        layout.add("yaxis", yAxis);

        jArray.add(auslastungJson);
        json.add("data", jArray);
        json.add("layout", layout);
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

    public String getSubscriberDurationsDiagram(List<String[]> subscriberAvg) {
        JsonObject json = new JsonObject();
        JsonObject avgDurationJson = new JsonObject();
        JsonArray jArray = new JsonArray();

        JsonArray dauer = new JsonArray();
        JsonArray zeit = new JsonArray();

        for (String[] a : subscriberAvg) {
            dauer.add(a[0]);
            zeit.add(a[1]);
        }

        avgDurationJson.add("x", zeit);
        avgDurationJson.add("y", dauer);
        avgDurationJson.addProperty("type", "line");
        avgDurationJson.addProperty("name", "Durchschnittl. Parkdauer der Abonnenten");

        jArray.add(avgDurationJson);

        JsonObject layout = new JsonObject();
        JsonObject title = new JsonObject();
        JsonObject xAxis = new JsonObject();
        JsonObject yAxis = new JsonObject();
        JsonObject xAxisTitle = new JsonObject();
        JsonObject yAxisTitle = new JsonObject();

        title.addProperty("text", "Durschnittliche Parkdauer der Abonnenten");
        xAxisTitle.addProperty("text", "Zeitpunkt der Messung");
        yAxisTitle.addProperty("text", "Parkdauer (in ms)");

        xAxis.add("title", xAxisTitle);
        yAxis.add("title", yAxisTitle);

        layout.add("title", title);
        layout.add("xaxis", xAxis);
        layout.add("yaxis", yAxis);


        json.add("data", jArray);
        json.add("layout", layout);
        return json.toString();
    }
}
