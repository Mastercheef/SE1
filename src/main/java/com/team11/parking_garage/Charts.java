package com.team11.parking_garage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.team11.parking_garage.customers.Subscriber;
import com.team11.parking_garage.customers.Customer;
import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Standard;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

public class Charts {
    private static final String TITLE = "title";
    private static final String FONT = "font";
    private static final String VALUES = "values";
    private static final String ALPHA_BG = "rgba(0,0,0,0)";
    private static final String LAYOUT = "layout";
    private static final String DATA = "data";
    private final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

    private String getPieChart(int[] ints, JsonArray labels, String chartTitle) {
        JsonArray data = new JsonArray();
        JsonArray values = new JsonArray();
        JsonObject dataObject = new JsonObject();
        JsonObject layout = new JsonObject();
        JsonObject font = new JsonObject();
        JsonObject title = new JsonObject();
        JsonObject json = new JsonObject();

        title.addProperty("text", chartTitle);
        font.addProperty("color", "#2196f3");
        layout.add(TITLE, title);
        layout.add(FONT, font);
        layout.addProperty("paper_bgcolor", ALPHA_BG);
        layout.addProperty("plot_bgcolor", ALPHA_BG);

        for (int i : ints) {
            values.add(i);
        }

        dataObject.add("labels", labels);
        dataObject.add(VALUES, values);
        dataObject.addProperty("type", "pie");
        dataObject.addProperty("name", "Typ");
        data.add(dataObject);

        json.add(DATA, data);
        json.add(LAYOUT, layout);
        return json.toString();
    }

    private JsonObject getLayout(String chartTitle, String xTitle, String yTitle) {
        JsonObject layout = new JsonObject();
        JsonObject title = new JsonObject();
        JsonObject xAxis = new JsonObject();
        JsonObject yAxis = new JsonObject();
        JsonObject xAxisTitle = new JsonObject();
        JsonObject yAxisTitle = new JsonObject();
        JsonObject font = new JsonObject();

        font.addProperty("color", "#2196f3");
        title.addProperty("text", chartTitle);

        xAxisTitle.addProperty("text", xTitle);
        yAxisTitle.addProperty("text", yTitle);
        xAxis.add(TITLE, xAxisTitle);
        yAxis.add(TITLE, yAxisTitle);

        layout.add(TITLE, title);
        layout.add("xaxis", xAxis);
        layout.add("yaxis", yAxis);
        layout.add(FONT, font);
        layout.addProperty("paper_bgcolor", ALPHA_BG);
        layout.addProperty("plot_bgcolor", ALPHA_BG);
        return layout;
    }

    public String getAveragePriceDuration(List<Ticket> tickets) {
        JsonObject json = new JsonObject();
        JsonObject priceJson = new JsonObject();
        JsonObject durationJson = new JsonObject();
        JsonArray data = new JsonArray();

        JsonArray price = new JsonArray();
        JsonArray duration = new JsonArray();
        JsonArray timeJson = new JsonArray();
        BigDecimal sumPrice = new BigDecimal(0);
        long sumDuration = 0;
        float carCount = 0;

        for (Ticket ticket : tickets) {
            sumPrice = sumPrice.add(ticket.getPrice());
            sumDuration += ticket.getDuration();
            carCount++;
            price.add((sumPrice.divide(new BigDecimal(carCount), mc)).floatValue());
            duration.add(sumDuration/carCount/1000/60   );
            SimpleDateFormat date = new SimpleDateFormat("MM-dd HH:mm:ss:SS");
            date.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            Date time = new Date((ticket.getDeparture()));
            String javaDate = date.format(time);
            timeJson.add(javaDate);
        }

        priceJson.add("x", timeJson);
        priceJson.add("y", price);
        priceJson.addProperty("type", "line");
        priceJson.addProperty("name", "Preis");

        durationJson.add("x", timeJson);
        durationJson.add("y", duration);
        durationJson.addProperty("type", "line");
        durationJson.addProperty("name", "Parkdauer");

        data.add(priceJson);
        data.add(durationJson);
        json.add(DATA, data);
        json.add(LAYOUT, getLayout(
                "Durschnittlicher Preis und Parkdauer",
                "Zeitpunkt der Messung",
                "Preis / Parkdauer"));
        return json.toString();
    }

    public String getCarTypeDiagram(List<CarIF> cars) {
        int suv = 0;
        int limousine = 0;
        int kombi = 0;
        String[] carTypeArray = Car.carTypeArray(cars);
        for (String c : carTypeArray) {
            if (c.equals("SUV")) suv++;
            if (c.equals("Limousine")) limousine++;
            if (c.equals("Kombi")) kombi++;
        }

        JsonArray labels = new JsonArray();
        labels.add("SUV");
        labels.add("Limousine");
        labels.add("Kombi");
        return getPieChart(new int[]{suv, limousine, kombi}, labels, "Fahrzeugtypen");
    }

    public String getUtilizationPlot(List<String[]> utilizationList) {
        JsonObject json = new JsonObject();
        JsonObject utilization = new JsonObject();
        JsonArray data = new JsonArray();

        JsonArray percent = new JsonArray();
        JsonArray timeJson = new JsonArray();

        for (String[] p : utilizationList) {
            percent.add(Integer.parseInt(p[1]));
            SimpleDateFormat date = new SimpleDateFormat("MM-dd HH:mm:ss:SS");
            date.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            Date time = new Date((Long.parseLong(p[0])));
            String javaDate = date.format(time);
            timeJson.add(javaDate);
        }

        utilization.add("x", timeJson);
        utilization.add("y", percent);
        utilization.addProperty("type", "line");
        utilization.addProperty("name", "Maximale Auslastung");

        data.add(utilization);
        json.add(DATA, data);
        json.add(LAYOUT, getLayout(
                "Momentane Auslastung des Parkhauses",
                "Zeitpunkt der Messung",
                "Auslastung (in %)"));
        return json.toString();
    }

    public String getCustomerTypeDiagram(List<Ticket> tickets) {
        int subscriber = 0;
        int standard = 0;
        int senior = 0;
        int student = 0;
        int family = 0;
        for (Ticket ticket : tickets) {
            Customer customer = ticket.getCustomer();
            if (customer instanceof Subscriber) subscriber++;
            if (customer instanceof Standard) standard++;
            if (customer instanceof Discounted) {
                switch(((Discounted) customer).getType()) {
                    case "Senior":
                        senior++;
                        break;
                    case "Student":
                        student++;
                        break;
                    case "Familie":
                        family++;
                        break;
                    default:
                        System.out.println("Unrecognised Client Type: " + ((Discounted) customer).getType());
                        break;
                }
            }
        }

        JsonArray labels = new JsonArray();
        labels.add("Abonnent");
        labels.add("Standard");
        labels.add("Senior");
        labels.add("Student");
        labels.add("Familie");

        return getPieChart(new int[]{subscriber, standard, senior, student, family}, labels, "Kundentypen");
    }

    public String getSubscriberDurationsDiagram(List<String[]> subscriberAvg) {
        JsonObject json = new JsonObject();
        JsonObject avgDurationJson = new JsonObject();
        JsonArray data = new JsonArray();

        JsonArray duration = new JsonArray();
        JsonArray time = new JsonArray();

        for (String[] a : subscriberAvg) {
            duration.add(a[0]);
            time.add(a[1]);
        }

        avgDurationJson.add("x", time);
        avgDurationJson.add("y", duration);
        avgDurationJson.addProperty("type", "line");
        avgDurationJson.addProperty("name", "Durchschnittl. Parkdauer der Abonnenten");

        data.add(avgDurationJson);

        json.add(DATA, data);
        json.add(LAYOUT, getLayout(
                "Durschnittliche Parkdauer der Abonnenten",
                "Zeitpunkt der Messung",
                "Parkdauer (in ms)"));
        return json.toString();
    }
}