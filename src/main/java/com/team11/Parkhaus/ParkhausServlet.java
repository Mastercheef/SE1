package com.team11.Parkhaus;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ParkhausServlet extends HttpServlet {
    Stats stats = new Stats();
    Charts charts = new Charts();
    Auslastung auslastung = new Auslastung();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] postParams = getBody(req).split(",");
        // enter
        if (postParams[0].equals("enter")) {
            enter(postParams[10], postParams[5], postParams[6], postParams[9], postParams[1], postParams[2],postParams[7], postParams[8]);
        // leave
        } else if (postParams[0].equals("leave")) {
            leave(postParams[5], postParams[3], postParams[4]);
        }
        // occupied
        else if (postParams[0].equals("occupied")) {
            delete(postParams[1]);
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
                    out.println(stats.getSum(getCars()));
                    break;
                case "Durchschnitt":
                    out.println(stats.getAvg(getCars()));
                    break;
                case "habenVerlassen":
                    out.println(stats.getCarCount(getCars()));
                    break;
                case "Diagramm":
                    out.println((charts.getDiagram(getCars())));
                    break;
                case "FahrzeugtypenDiagramm":
                    out.println((charts.getCarTypeDiagram(getCars())));
                    break;
                case "reset":
                    out.println(reset());
                    break;
                case "cars":
                    out.println(Car.getSavedCarsCSV(getCars()));
                    break;
                case "Auslastung":
                    out.println((auslastung.getAuslastung(getCars()) + "%"));
                    break;
                case "AuslastungDiagramm":
                    out.println((charts.getAuslastungDiagramm(getAuslastungsListe())));
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
        cars.add(new Car(licensePlate,ticketId,color,carType, nr, arrival, space, clientType));
        setCars(cars);

        setAuslastung(auslastung.setAuslastungNow(getAuslastungsListe(), getCars()));

        System.out.println("ENTER: " + licensePlate);
    }


    private void leave(String ticketId, String duration, String price) {
        List<CarIF> cars = getCars();
        CarIF toLeave = cars.stream().filter(car -> car.getTicketId().equals(ticketId)).findFirst().orElse(null);

        if (toLeave != null) {
            toLeave.leave(duration, price);
            setCars(cars);
            setAuslastung(auslastung.setAuslastungNow(getAuslastungsListe(), getCars()));
            System.out.println("LEAVE: " + toLeave.getLicencePlate());
        }
    }

    private void delete(String nr) {
        List<CarIF> cars = getCars();
        int toRemove = Integer.parseInt(nr.replaceAll("\\D+","")); // Übrige zeichen aus nr entfernen
        setCars(cars.stream().filter(car -> car.getNr() != toRemove).collect(Collectors.toList())); // Alle übrigen Cars an setCars übergeben
        System.out.println("DELETED: " + toRemove);
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

    private String reset() {
        Enumeration<String> names = getContext().getAttributeNames();
        while (names.hasMoreElements()) {
            getContext().removeAttribute(names.nextElement());
        }
        return "<meta http-equiv=\"refresh\" content=\"0; url=../\" />";
    }
}