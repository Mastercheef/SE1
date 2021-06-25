package com.team11.Parkhaus;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ParkhausServlet extends HttpServlet {
    Stats stats = new Stats();
    Charts charts = new Charts();
    Auslastung auslastung = new Auslastung();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] postParams = getBody(req).split(",");
        // enter
        if (postParams[0].equals("enter")){
            //( licensePlate,    ticketId,          color,        carType,      nr,            arrival,      space,            clientType)
            enter(postParams[10], postParams[5], postParams[6], postParams[9], postParams[1], postParams[2],postParams[7], postParams[8]);
        // leave
        } else if (postParams[0].equals("leave")){
            leave(postParams[5], postParams[3], postParams[4]);
        }
        // occupied
        else if (postParams[0].equals("occupied")){
            delete(postParams[1]);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                    out.println((auslastung.getAuslasung(getCars()) + "%"));
                    break;
                case "AuslastungDiagramm":
                    out.println((charts.getAuslasungDiagramm(getAuslastungsListe())));
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
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
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
        CarIF[] cars = getCars();

        // extend array
        CarIF[] tmpCars = new Car[cars.length+1];
        for (int i=0; i<cars.length; i++){
            tmpCars[i] = cars[i];
        }
        cars = tmpCars;

        // add new car
        cars[cars.length-1] = new Car(licensePlate,ticketId,color,carType, nr, arrival, space, clientType);
        System.out.println("enter:" + licensePlate);

        setCars(cars);
        setAuslastung(auslastung.setAuslastungNow(getAuslastungsListe(), getCars()));
    }


    private void leave(String ticketId, String duration, String price){
        CarIF[] cars = getCars();
        for (int i=0; i<cars.length; i++){
            if(cars[i].getTicketId().equals(ticketId)){
                cars[i].leave(duration, price);
                System.out.println("leave:" + getCars()[i].toString());
            }
        }
        setCars(cars);
        setAuslastung(auslastung.setAuslastungNow(getAuslastungsListe(), getCars()));
    }

    private void delete(String nr) {
        String carNr = nr.replaceAll("\\D+","");
        CarIF[] cars = getCars();
        setCars((CarIF[]) Arrays.stream(cars).filter(car -> car.getNr() != Integer.parseInt(carNr)).toArray(i -> new CarIF[i]));
        System.out.println("delete:" + carNr);
    }

    private CarIF[] getCars(){
        CarIF[] cars;
        if(getContext().getAttribute("cars") == null){
            cars = new Car[0];
        } else{
            cars = (CarIF[]) getContext().getAttribute("cars");
        }
        return cars;
    }

    private List<String[]> getAuslastungsListe() {
        List<String[]> auslastungsListe;
        if (getContext().getAttribute("auslastungsListe") == null) {
            auslastungsListe = new ArrayList<String[]>();
        } else {
            auslastungsListe = (List<String[]>) getContext().getAttribute("auslastungsListe");
        }
        return auslastungsListe;
    }

    private void setAuslastung(List<String[]> auslastungsListe) {
        getContext().setAttribute("auslastungsListe", auslastungsListe);
    }

    private void setCars(CarIF[] cars) {
        getContext().setAttribute("cars", cars);
    }

    private String reset() {
        Enumeration names = getContext().getAttributeNames();
        while (names.hasMoreElements()){
            getContext().removeAttribute(names.nextElement().toString());
        }
        return "<meta http-equiv=\"refresh\" content=\"0; url=../\" />";
    }
}