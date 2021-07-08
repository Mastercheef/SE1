package com.team11.parking_garage.charts;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.team11.parking_garage.Ticket;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class AveragePriceDuration extends Chart {
    private final List<Ticket> tickets;

    public AveragePriceDuration(List<Ticket> tickets) {
        super(
                "Durschnittlicher Preis & Parkdauer von Nicht-Abonnenten",
                "Zeitpunkt der Messung",
                "Parkdauer in Minuten"
        );
        this.tickets = tickets;
    }

    @Override
    protected JsonArray getData() {
        JsonArray data = super.getData();

        final MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

        JsonObject priceJson = new JsonObject();
        JsonObject durationJson = new JsonObject();

        JsonArray price = new JsonArray();
        JsonArray duration = new JsonArray();
        JsonArray timeJson = new JsonArray();

        BigDecimal sumPrice = BigDecimal.valueOf(0);
        long sumDuration = 0;
        float carCount = 0;

        for (Ticket ticket : tickets.stream()
                .filter(ticket -> (ticket.getPrice().floatValue() > 0))
                .collect(Collectors.toList())) {
            sumPrice = sumPrice.add(ticket.getPrice());
            sumDuration += ticket.getDuration();
            carCount++;
            price.add((sumPrice.divide(BigDecimal.valueOf(carCount), mc)).floatValue());
            duration.add(sumDuration / carCount / 1000 / 60);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss:SS");
            String javaDate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(ticket.getDeparture()),
                    ZoneId.systemDefault())
                    .format(formatter);
            timeJson.add(javaDate);
        }

        priceJson.add("x", timeJson);
        priceJson.add("y", price);
        priceJson.addProperty("type", "line");
        priceJson.addProperty("yaxis", "y2");
        priceJson.addProperty("name", "Preis");

        durationJson.add("x", timeJson);
        durationJson.add("y", duration);
        durationJson.addProperty("type", "line");
        durationJson.addProperty("name", "Parkdauer");

        data.add(priceJson);
        data.add(durationJson);
        return data;
    }

    @Override
    protected JsonObject getLayout() {
         JsonObject layout = super.getLayout();

        JsonObject secondYAxis = new JsonObject();
        secondYAxis.addProperty("title", "Preis in Euro");
        secondYAxis.addProperty("showgrid", false);
        secondYAxis.addProperty("overlaying", "y");
        secondYAxis.addProperty("side", "right");

        layout.add("yaxis2", secondYAxis);

        return layout;
    }
}
