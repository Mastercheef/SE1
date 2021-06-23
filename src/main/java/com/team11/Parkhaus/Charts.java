package com.team11.Parkhaus;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Charts {
    public String getDiagram(CarIF[] cars){
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

    public String getCarTypeDiagram(CarIF[] cars){
        int suv = 0, limousine = 0, kombi = 0;
        for (int i = 0; i< cars.length; i++){
            if (Car.carTypeArray(cars)[i].equals("SUV")){
                suv++;
            }
            if (Car.carTypeArray(cars)[i].equals("Limousine")){
                limousine++;
            }
            if (Car.carTypeArray(cars)[i].equals("Kombi")){
                kombi++;
            }
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

    public String getAuslasungDiagramm(CarIF[] cars) {
        Auslastung auslastung = new Auslastung();
        int[] auslastungArray = auslastung.getAuslastung24H(cars);

        JsonObject json = new JsonObject();
        JsonObject auslastungJson = new JsonObject();
        JsonArray jArray = new JsonArray();

        JsonArray prozent = new JsonArray();
        JsonArray hours = new JsonArray();

        for (int p : auslastungArray) {
            prozent.add(p);
        }
        Date date = new Date();
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);

        for (int i=0; i<24; i++) {
            if (hour < 0) {hour += 24;}
            String hourString = Integer.toString(hour);
            String minSting = Integer.toString(min);
            int hourEnd = hour - 1;
            if (hourEnd < 0) {hourEnd += 24;}
            String hourStringEnd = Integer.toString(hourEnd);
            int minEnd = min - 1;
            if (minEnd < 0) {minEnd += 60;}
            String minStingEnd = Integer.toString(minEnd);


            if (minSting.length() == 1) {
                minSting = "0" + minSting;
            }
            if (minStingEnd.length() == 1) {
                minStingEnd = "0" + minStingEnd;
            }
            if (hourStringEnd.length() == 1) {
                hourStringEnd = "0" + hourStringEnd;
            }

            if (hourString.length() == 1) {
                hourString = "0" + hourString + ":" + minSting + " - " + hourStringEnd + ":" + minStingEnd;
            } else {
                hourString = hourString + ":" + minSting + "-" + hourStringEnd + ":" + minStingEnd;
            }
            hours.add(hourString);
            hour--;
        }

        auslastungJson.add("x", hours);
        auslastungJson.add("y", prozent);
        auslastungJson.addProperty("type", "bar");
        auslastungJson.addProperty("name", "Maximale Auslastung in Prozent");

        jArray.add(auslastungJson);
        json.add("data", jArray);
        return json.toString();
    }
}
