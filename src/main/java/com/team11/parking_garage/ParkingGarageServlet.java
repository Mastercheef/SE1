package com.team11.parking_garage;

import com.team11.parking_garage.customers.Subscriber;
import com.team11.parking_garage.customers.Customer;
import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Standard;
import com.team11.parking_garage.management.IncomeStatement;
import com.team11.parking_garage.management.ROICalculator;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ParkingGarageServlet extends HttpServlet {
    /*
     * config:
     *
     * "parkhaus.js" defaults:
     *      defaultMax: 10
     *      defaultOpenFrom: 6
     *      defaultOpenTo: 24
     *      defaultDelay: 100
     *      defaultSimulationSpeed: 10
     */

    private static final int defaultMax = 20;
    private static final int defaultOpenFrom = 0;
    private static final int defaultOpenTo = 24;
    private static final int defaultDelay = 200;
    private static final int defaultSimulationSpeed = 2700;
    private static final String CFG_MAX = "cfgMax";
    private static final String CFG_FROM = "cfgFrom";
    private static final String CFG_TO = "cfgTo";
    public static final String CUSTOMERS = "customers";
    public static final String TICKETS = "tickets";
    public static final String SUBSCRIBER_AVG = "subscriberAvg";
    public static final String UTILIZATION_LIST = "utilizationList";

    Stats stats = new Stats();
    Charts charts = new Charts();
    Utilization utilization = new Utilization(defaultMax);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] postParams = getBody(req).split(",");
        PrintWriter out = resp.getWriter();

        switch (postParams[0]) { // Switch Case is implemented for String since Java 7: https://stackoverflow.com/a/338230
            case "enter":
                enter(postParams);
                break;
            case "leave":
                leave(postParams[5], postParams[3], postParams[4]);
                break;
            case "occupied":
                delete(postParams[1]);
                break;
            case "change_max":
                setConfig(CFG_MAX, postParams[2]);
                break;
            case "change_open_from":
                setConfig(CFG_FROM, postParams[2]);
                break;
            case "change_open_to":
                setConfig(CFG_TO, postParams[2]);
                break;
            case "roi":
                out.println(calcRoi(postParams[1], postParams[2], postParams[3]));
                break;
            case "incomestatement":
                out.println(createIncomeStatement(postParams[1]));
                break;
            default:
                System.out.println("Post Parameter \"" + postParams[0] + "\" detected.");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String cmd = req.getParameter("cmd");
        if (cmd != null) {
            switch(cmd) {
                case "sum":
                    out.println(stats.getSum(getTickets()));
                    break;
                case "averagePrice":
                    out.println(stats.getAvg(getTickets()));
                    break;
                case "ticketCount":
                    out.println(stats.getCarCount(getTickets()));
                    break;
                case "averageDiagram":
                    out.println((charts.getAveragePriceDurationDiagram(getTickets())));
                    break;
                case "carTypeDiagram":
                    out.println((charts.getCarTypeDiagram(getCars())));
                    break;
                case "customerDiagram":
                    out.println(charts.getCustomerTypeDiagram(getTickets()));
                    break;
                case "subDurationDiagram":
                    out.println(charts.getSubscriberDurationsDiagram(getSubscriberAvg()));
                    break;
                case "utilization":
                    out.println((utilization.getUtilization(getCars(), getContext()) + "%"));
                    break;
                case "utilizationDiagram":
                    out.println((charts.getUtilizationDiagram(getUtilizationList())));
                    break;
                case "ticket":
                    out.println(getTicketJsonById(req.getParameter("id")));
                    break;
                case "allTickets":
                    out.println(allTicketsAsJson());
                    break;
                case "config":
                    out.println(getConfig());
                    break;
                case "cars":
                    out.println(Car.getSavedCarsCSV(getCars()));
                    break;
                case "reset":
                    reset();
                    break;
                default:
                    System.out.println("Command \""+ cmd + "\" detected.");
            }
        }
    }



    String getBody( HttpServletRequest request ) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if ( inputStream != null ) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return stringBuilder.toString();
    }

    public ServletContext getContext()  {
        return getServletConfig().getServletContext();
    }

    private void enter(String[] postParams) {
        List<CarIF> cars = getCars();
        List<Customer> customers = getCustomers();

        int parsedNr = Integer.parseInt(postParams[1]);

        Customer enteringCustomer = customers.stream().filter(customer -> customer.getNr() == parsedNr).findFirst().orElse(null);

        // Create new Customer if not existing
        String clientType = postParams[8];
        if (enteringCustomer == null) {
            switch (clientType) {
                case "Abonnent":
                    enteringCustomer = new Subscriber(parsedNr, 0);
                    break;
                case "Standard":
                    enteringCustomer = new Standard(parsedNr);
                    break;
                default:
                    enteringCustomer = new Discounted(parsedNr, clientType);
                    break;
            }
            customers.add(enteringCustomer);
            setCustomers(customers);
        }

        cars.add(new Car(postParams, enteringCustomer));
        setCars(cars);

        setUtilizationList(utilization.getUtilizationNow(getUtilizationList(), getCars(), getContext()));

        System.out.println("ENTER: " + parsedNr);
    }


    private void leave(String ticketId, String duration, String price) {
        List<CarIF> cars = getCars();
        CarIF toLeave = cars.stream().filter(car -> car.getTicketId().equals(ticketId)).findFirst().orElse(null);

        List<Ticket> tickets = getTickets();

        if (toLeave != null) {
            tickets.add(toLeave.leave(tickets, duration, price));
            setTickets(tickets);
            setCars(cars);
            setUtilizationList(utilization.getUtilizationNow(getUtilizationList(), getCars(), getContext()));
            updateSubscriberAvg();
            System.out.println("LEAVE: " + toLeave.getNr());
        }
    }

    private void delete(String nr) {
        deleteLastUtilization();
        List<CarIF> cars = getCars();
        int toRemove = Integer.parseInt(nr.replaceAll("\\D+","")); // Übrige zeichen aus nr entfernen
        setCars(cars.stream().filter(car -> car.getNr() != toRemove).collect(Collectors.toList())); // Alle übrigen Cars an setCars übergeben
        System.out.println("DELETED: " + toRemove);
    }

    private List<Customer> getCustomers() {
        if (getContext().getAttribute(CUSTOMERS) == null) {
            return new ArrayList<>();
        } else {
            return (List<Customer>) getContext().getAttribute(CUSTOMERS);
        }
    }

    private void setCustomers(List<Customer> customers) {
        getContext().setAttribute(CUSTOMERS, customers);
    }

    public String allTicketsAsJson() {
        List<Ticket> tickets = getTickets();
        final String collect = tickets.stream().map(Ticket::getAsJson).collect(Collectors.joining(","));
        return "[" + collect + "]";
    }

    public String getTicketJsonById(String id) {
        Ticket t = getTickets().stream().filter(ticket -> ticket.getId().equals(id)).findFirst().get();
        return t.getAsJson();
    }

    public List<Ticket> getTickets() {
        if (getContext().getAttribute(TICKETS) == null) {
            return new ArrayList<>();
        } else {
            return (List<Ticket>) getContext().getAttribute(TICKETS);
        }
    }

    private void setTickets(List<Ticket> tickets) {
        getContext().setAttribute(TICKETS, tickets);
    }

    private List<CarIF> getCars() {
        if (getContext().getAttribute("cars") == null) {
            return new ArrayList<>();
        } else {
            return (List<CarIF>) getContext().getAttribute("cars");
        }
    }

    private void setCars(List<CarIF> cars) {
        getContext().setAttribute("cars", cars);
    }

    private List<String[]> getSubscriberAvg() {
        if (getContext().getAttribute(SUBSCRIBER_AVG) == null) {
            return new ArrayList<>();
        } else {
            return (List<String[]>) getContext().getAttribute(SUBSCRIBER_AVG);
        }
    }

    private void updateSubscriberAvg() {
        List<String[]> subscriberAvg = getSubscriberAvg();
        double avg = getTickets().stream().filter(ticket -> ticket.getCustomer() instanceof Subscriber).mapToLong(Ticket::getDuration).average().orElse(-1);
        if (avg > -1) {
            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss:SS");
            format.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
            subscriberAvg.add(new String[]{String.valueOf(avg), format.format(new Date())});
            getContext().setAttribute(SUBSCRIBER_AVG, subscriberAvg);
        }
    }

    private List<String[]> getUtilizationList() {
        List<String[]> utilizationList;
        if (getContext().getAttribute(UTILIZATION_LIST) == null) {
            utilizationList = new ArrayList<>();
        } else {
            utilizationList = (List<String[]>) getContext().getAttribute(UTILIZATION_LIST);
        }
        return utilizationList;
    }

    private void setUtilizationList(List<String[]> utilizationList) {
        getContext().setAttribute(UTILIZATION_LIST, utilizationList);
    }

    private void deleteLastUtilization() {
        List<String[]> utilizationList = (List<String[]>)getContext().getAttribute(UTILIZATION_LIST);
        utilizationList.remove(utilizationList.size()-1);
        getContext().setAttribute(UTILIZATION_LIST, utilizationList);
    }

    private String calcRoi(String investment, String share, String costPerCar) {
        String dailyProfit = new IncomeStatement(getTickets(), costPerCar).getProfit();
        return new ROICalculator(investment, dailyProfit, share).getAsJson();
    }

    private String createIncomeStatement(String costPerCar) {
        return new IncomeStatement(getTickets(), costPerCar).getAsJson();
    }

    private void setConfig(String key, String value) {
        getContext().setAttribute(key, value);
    }

    private String getConfig() {
        StringBuilder stringBuilder = new StringBuilder();
        String cfgMax;
        String cfgFrom;
        String cfgTo;
        if (getContext().getAttribute(CFG_MAX) == null) {
            cfgMax = String.valueOf(defaultMax);
        } else {
            cfgMax = (String)(getContext().getAttribute(CFG_MAX));
        }

        if (getContext().getAttribute(CFG_FROM) == null) {
            cfgFrom = String.valueOf(defaultOpenFrom);
        } else {
            cfgFrom = (String)(getContext().getAttribute(CFG_FROM));
        }

        if (getContext().getAttribute(CFG_TO) == null) {
            cfgTo = String.valueOf(defaultOpenTo);
        } else {
            cfgTo = (String)(getContext().getAttribute(CFG_TO));
        }

        stringBuilder
                .append(cfgMax).append(",")
                .append(cfgFrom).append(",")
                .append(cfgTo).append(",")
                .append(defaultDelay).append(",")
                .append(defaultSimulationSpeed);
        return stringBuilder.toString();
    }

    private void reset() {
        Enumeration<String> attributeNames = getContext().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            getContext().removeAttribute(attributeNames.nextElement());
        }
    }
}