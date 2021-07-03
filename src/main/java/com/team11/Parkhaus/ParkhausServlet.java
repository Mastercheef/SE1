package com.team11.Parkhaus;

import com.team11.Parkhaus.Kunden.Abonnent;
import com.team11.Parkhaus.Kunden.Kunde;
import com.team11.Parkhaus.Kunden.Standard;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ParkhausServlet extends HttpServlet {
    /*
     * config:
     *
     * webcomponent defaults:
     *      default_max = 10;
     *      default_open_from = 6;
     *      default_open_to = 24;
     *      default_delay = 100;
     *      default_price_factor = 10;
     */

    private int defaultMax = 20;
    private int defaultOpenFrom = 0;
    private int defaultOpenTo = 24;
    private int defaultDelay = 100;
    private int defaultPriceFactor = 10;



    Stats stats = new Stats();
    Charts charts = new Charts();
    Auslastung auslastung = new Auslastung(defaultMax);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] postParams = getBody(req).split(",");

        switch (postParams[0]) { // Switch Case ist für String implementiert seit Java 7: https://stackoverflow.com/a/338230
            case "enter":
                enter(postParams[10], postParams[5], postParams[6], postParams[9], postParams[1], postParams[2], postParams[7], postParams[8]);
                break;
            case "leave":
                leave(postParams[5], postParams[3], postParams[4]);
                break;
            case "occupied":
                delete(postParams[1]);
                break;
            case "change_max":
                setConfig("cfgMax", postParams[2]);
                break;
            case "change_open_from":
                setConfig("cfgFrom", postParams[2]);
                break;
            case "change_open_to":
                setConfig("cfgTo", postParams[2]);
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
                case "Summe":
                    out.println(stats.getSum(getTickets()));
                    break;
                case "Durchschnitt":
                    out.println(stats.getAvg(getTickets()));
                    break;
                case "habenVerlassen":
                    out.println(stats.getCarCount(getTickets()));
                    break;
                case "Diagramm":
                    out.println((charts.getDiagram(getCars())));
                    break;
                case "FahrzeugtypenDiagramm":
                    out.println((charts.getCarTypeDiagram(getCars())));
                    break;
                case "KundentypenDiagramm":
                    out.println(charts.getCustomerTypeDiagram(getTickets()));
                    break;
                case "AboParkdauerDiagramm":
                    out.println(charts.getSubscriberDurationsDiagram(getSubscriberAvg()));
                    break;
                case "reset":
                    out.println(reset());
                    break;
                case "cars":
                    out.println(Car.getSavedCarsCSV(getCars()));
                    break;
                case "Auslastung":
                    out.println((auslastung.getAuslastung(getCars(), getContext()) + "%"));
                    break;
                case "AuslastungDiagramm":
                    out.println((charts.getAuslastungDiagramm(getAuslastungsListe())));
                    break;
                case "ticket":
                    out.println(getTicketJsonById(req.getParameter("id")));
                    break;
                case "config":
                    out.println(getConfig());
                    break;
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

    private void enter(String licensePlate, String ticketId, String color, String carType, String nr, String arrival, String space, String clientType) {
        List<CarIF> cars = getCars();
        List<Kunde> customers = getCustomers();

        int parsedNr = Integer.parseInt(nr);

        Kunde enteringCustomer = customers.stream().filter(customer -> customer.getNr() == parsedNr).findFirst().orElse(null);

        // Neuen Kunden erstellen, falls nicht vorhanden
        if (enteringCustomer == null) {
            switch (clientType) {
                case "Abo-1":
                    enteringCustomer = new Abonnent(parsedNr, 0);
                    break;
                case "Abo-2":
                    enteringCustomer = new Abonnent(parsedNr, 3);
                    break;
                default:
                    enteringCustomer = new Standard(parsedNr);
                    break;
            }
            customers.add(enteringCustomer);
            setCustomers(customers);
        }

        cars.add(new Car(licensePlate,ticketId,color,carType, parsedNr, arrival, space, clientType, enteringCustomer));
        setCars(cars);

        setAuslastung(auslastung.setAuslastungNow(getAuslastungsListe(), getCars(), getContext()));

        System.out.println("ENTER: " + nr);
    }


    private void leave(String ticketId, String duration, String price) {
        List<CarIF> cars = getCars();
        CarIF toLeave = cars.stream().filter(car -> car.getTicketId().equals(ticketId)).findFirst().orElse(null);

        List<Ticket> tickets = getTickets();

        if (toLeave != null) {
            tickets.add(toLeave.leave(tickets, duration, price));
            setTickets(tickets);
            setCars(cars);
            setAuslastung(auslastung.setAuslastungNow(getAuslastungsListe(), getCars(), getContext()));
            updateSubscriberAvg();
            System.out.println("LEAVE: " + toLeave.getNr());
        }
    }

    private void delete(String nr) {
        deleteAuslastung();
        List<CarIF> cars = getCars();
        int toRemove = Integer.parseInt(nr.replaceAll("\\D+","")); // Übrige zeichen aus nr entfernen
        setCars(cars.stream().filter(car -> car.getNr() != toRemove).collect(Collectors.toList())); // Alle übrigen Cars an setCars übergeben
        System.out.println("DELETED: " + toRemove);
    }

    private List<Kunde> getCustomers() {
        if (getContext().getAttribute("customers") == null) {
            return new ArrayList<>();
        } else {
            return (List<Kunde>) getContext().getAttribute("customers");
        }
    }

    private void setCustomers(List<Kunde> customers) {
        getContext().setAttribute("customers", customers);
    }

    public String getTicketJsonById(String id) {
        Ticket t = getTickets().stream().filter(ticket -> ticket.getId().equals(id)).findFirst().get();
        return t.getAsJson();
    }

    public List<Ticket> getTickets() {
        if (getContext().getAttribute("tickets") == null) {
            return new ArrayList<>();
        } else {
            return (List<Ticket>) getContext().getAttribute("tickets");
        }
    }

    private void setTickets(List<Ticket> tickets) {
        getContext().setAttribute("tickets", tickets);
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
        if (getContext().getAttribute("subscriberAvg") == null) {
            return new ArrayList<>();
        } else {
            return (List<String[]>) getContext().getAttribute("subscriberAvg");
        }
    }

    private void updateSubscriberAvg() {
        List<String[]> subscriberAvg = getSubscriberAvg();
        double avg = getTickets().stream().filter(ticket -> ticket.getCustomer() instanceof Abonnent).mapToLong(Ticket::getDuration).average().orElse(-1);
        if (avg > -1) {
            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss:SS");
            format.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
            subscriberAvg.add(new String[]{String.valueOf(avg), format.format(new Date())});
            getContext().setAttribute("subscriberAvg", subscriberAvg);
        }
    }

    private List<String[]> getAuslastungsListe() {
        List<String[]> auslastungsListe;
        if (getContext().getAttribute("auslastungsListe") == null) {
            auslastungsListe = new ArrayList<>();
        } else {
            auslastungsListe = (List<String[]>) getContext().getAttribute("auslastungsListe");
        }
        return auslastungsListe;
    }

    private void setAuslastung(List<String[]> auslastungsListe) {
        getContext().setAttribute("auslastungsListe", auslastungsListe);
    }

    private void deleteAuslastung() {
        List<String[]> auslastungsListe = (List<String[]>)getContext().getAttribute("auslastungsListe");
        auslastungsListe.remove(auslastungsListe.size()-1);
        getContext().setAttribute("auslastungsListe", auslastungsListe);
    }

    private void setConfig(String key, String value) {
        getContext().setAttribute(key, value);
    }

    private String getConfig() {
        StringBuilder stringBuilder = new StringBuilder();
        String cfgMax, cfgFrom, cfgTo;
        if (getContext().getAttribute("cfgMax") == null) {
            cfgMax = String.valueOf(defaultMax);
        } else {
            cfgMax = (String)(getContext().getAttribute("cfgMax"));
        }

        if (getContext().getAttribute("cfgFrom") == null) {
            cfgFrom = String.valueOf(defaultOpenFrom);
        } else {
            cfgFrom = (String)(getContext().getAttribute("cfgFrom"));
        }

        if (getContext().getAttribute("cfgTo") == null) {
            cfgTo = String.valueOf(defaultOpenTo);
        } else {
            cfgTo = (String)(getContext().getAttribute("cfgTo"));
        }

        stringBuilder.append(cfgMax);
        stringBuilder.append(",");
        stringBuilder.append(cfgFrom);
        stringBuilder.append(",");
        stringBuilder.append(cfgTo);
        stringBuilder.append(",");
        stringBuilder.append(String.valueOf(defaultDelay)); // Verzögerung in ms
        stringBuilder.append(",");
        stringBuilder.append(String.valueOf(defaultPriceFactor)); // Preisfaktor
        return stringBuilder.toString();
    }

    private String reset() {
        Enumeration<String> names = getContext().getAttributeNames();
        while (names.hasMoreElements()) {
            getContext().removeAttribute(names.nextElement());
        }
        return "<meta http-equiv=\"refresh\" content=\"0; url=../\" />";
    }
}