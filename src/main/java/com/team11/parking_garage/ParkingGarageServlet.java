package com.team11.parking_garage;

import com.team11.parking_garage.charts.CarType;
import com.team11.parking_garage.charts.CustomerType;
import com.team11.parking_garage.charts.SubscriberDuration;
import com.team11.parking_garage.charts.UtilizationChart;
import com.team11.parking_garage.customers.Customer;
import com.team11.parking_garage.customers.Discounted;
import com.team11.parking_garage.customers.Standard;
import com.team11.parking_garage.customers.Subscriber;
import com.team11.parking_garage.management.IncomeStatement;
import com.team11.parking_garage.management.ROICalculator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


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

    static final int DEFAULT_MAX = 20;
    private static final int DEFAULT_OPEN_FROM = 0;
    private static final int DEFAULT_OPEN_TO = 24;
    private static final int DEFAULT_DELAY = 200;
    private static final int DEFAULT_SIMULATION_SPEED = 2700;
    private static final String CFG_MAX = "cfgMax";
    private static final String CFG_FROM = "cfgFrom";
    private static final String CFG_TO = "cfgTo";
    private static final String CUSTOMERS = "customers";
    private static final String TICKETS = "tickets";
    private static final String SUBSCRIBER_AVG = "subscriberAvg";
    private static final String UTILIZATION_LIST = "utilizationList";

    private final Stats stats = Stats.getInstance();
    private final Charts charts = Charts.getInstance();
    private final Utilization utilization = Utilization.getInstance();
    private static Logger logger = Logger.getLogger("parking_garage.ParkingGarageServlet");

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
                logger.log(Level.INFO, "Unknown POST: \"" + postParams[0] + "\" detected.");
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
                    out.println(new CarType(getCars()).getJson());
                    break;
                case "customerDiagram":
                    out.println(new CustomerType(getTickets()).getJson());
                    break;
                case "subDurationDiagram":
                    out.println(new SubscriberDuration(getSubscriberAvg()).getJson());
                    break;
                case "utilization":
                    out.println((utilization.getUtilization(getCars(), getContext()) + "%"));
                    break;
                case "utilizationDiagram":
                    out.println(new UtilizationChart(getUtilizationList()).getJson());
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
                    logger.log(Level.INFO, "Unknown GET: \""+ cmd + "\" detected.");
            }
        }
    }



    String getBody( HttpServletRequest request ) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = null;
            InputStream inputStream = request.getInputStream();
            if ( inputStream != null ) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
        } catch (IOException e) {
            logger.log(Level.INFO,"Invalid POST Body");
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

        logger.log(Level.INFO,"New Car with Nr: " + parsedNr + " entered");
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
            logger.log(Level.INFO,"Car with Nr: " + toLeave.getNr() + " left");
        }
    }

    private void delete(String nr) {
        deleteLastUtilization();
        List<CarIF> cars = getCars();
        int toRemove = Integer.parseInt(nr.replaceAll("\\D+","")); // Übrige zeichen aus nr entfernen
        setCars(cars.stream().filter(car -> car.getNr() != toRemove).collect(Collectors.toList())); // Alle übrigen Cars an setCars übergeben
        logger.log(Level.INFO,"Car with Nr: " + toRemove + " deleted");
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss:SS");
            subscriberAvg.add(new String[]{String.valueOf(avg), LocalDateTime.now().format(formatter)});
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
            cfgMax = String.valueOf(DEFAULT_MAX);
        } else {
            cfgMax = (String)(getContext().getAttribute(CFG_MAX));
        }

        if (getContext().getAttribute(CFG_FROM) == null) {
            cfgFrom = String.valueOf(DEFAULT_OPEN_FROM);
        } else {
            cfgFrom = (String)(getContext().getAttribute(CFG_FROM));
        }

        if (getContext().getAttribute(CFG_TO) == null) {
            cfgTo = String.valueOf(DEFAULT_OPEN_TO);
        } else {
            cfgTo = (String)(getContext().getAttribute(CFG_TO));
        }

        stringBuilder
                .append(cfgMax).append(",")
                .append(cfgFrom).append(",")
                .append(cfgTo).append(",")
                .append(DEFAULT_DELAY).append(",")
                .append(DEFAULT_SIMULATION_SPEED);
        return stringBuilder.toString();
    }

    private void reset() {
        Enumeration<String> attributeNames = getContext().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            getContext().removeAttribute(attributeNames.nextElement());
        }
    }
}