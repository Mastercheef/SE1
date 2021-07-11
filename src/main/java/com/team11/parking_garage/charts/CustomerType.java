package com.team11.parking_garage.charts;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.team11.parking_garage.Ticket;
import com.team11.parking_garage.customers.Customer;
import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Standard;
import com.team11.parking_garage.customers.Subscriber;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerType extends PieChart {
    private final List<Ticket> tickets;
    private static final Logger logger = Logger.getLogger("parking_garage.charts.CustomerType");

    /**
     * @author: ecetin2s
     */
    public CustomerType(List<Ticket> tickets) {
        super("Kundentypen");
        this.tickets = tickets;
    }

    /**
     * @author: ecetin2s
     * @author: mhoens2s
     */
    @Override
    protected JsonArray getData() {
        JsonArray data = super.getData();
        JsonObject jsonObject = new JsonObject();

        int[] counts = new int[5]; // Initializes with value of 0 due to Java language spec: https://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.12.5

        for (Ticket ticket : tickets) {
            Customer customer = ticket.getCustomer();
            if (customer instanceof Subscriber) counts[0]++;
            if (customer instanceof Standard) counts[1]++;
            if (customer instanceof Discounted) {
                switch(((Discounted) customer).getType()) {
                    case "Senior":
                        counts[2]++;
                        break;
                    case "Student":
                        counts[3]++;
                        break;
                    case "Familie":
                        counts[4]++;
                        break;
                    default:
                        logger.log(Level.INFO,"Unrecognised Customer Type: " + ((Discounted) customer).getType());
                        break;
                }
            }
        }

        String[] labels = new String[]{"Abonnent", "Standard", "Senior", "Student", "Familie"};

        jsonObject.add("labels", createLabelArray(labels));
        jsonObject.add("values", createValuesArray(counts));
        jsonObject.addProperty("type", "pie");
        jsonObject.addProperty("name", "Typ");

        data.add(jsonObject);
        return data;
    }
}
