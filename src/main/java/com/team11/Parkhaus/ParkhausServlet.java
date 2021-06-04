package com.team11.Parkhaus;

import java.io.*;
import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ParkhausServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] postParams = getBody(req).split(",");
        if (postParams[0].equals("enter")){
            CarIF[] cars = cars();
            CarIF[] tmpCars = new Car[cars.length+1];
            for (int i=0; i<cars.length; i++){
                tmpCars[i] = cars[i];
            }
            cars = tmpCars;
            String licensePlate = postParams[1];
            String ticketId = postParams[5];
            String color = postParams[6];
            String carType = postParams[8];
            cars[cars.length-1] = new Car(licensePlate,ticketId,color,carType);
            System.out.println("enter:" + licensePlate);
            getContext().setAttribute("cars", cars);
        } else if (postParams[0].equals("leave")){
            CarIF[] cars = cars();
            for (int i=0; i<cars.length; i++){
                if(cars[i].getTicketId().equals(postParams[5])){
                    cars[i].leave(Integer.parseInt(postParams[3]), Integer.parseInt(postParams[4]));
                    System.out.println("leave:" + cars()[i].getLicencePlate());
                }
            }
            getContext().setAttribute("cars", cars);
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
                    out.println(getSum());
                    break;
                case "Durchschnitt":
                    out.println(getAvg());
                    break;
                case "habenVerlassen":
                    out.println(getCarCount());
                    break;
                case "Diagramm":
                    out.println((getDiagram()));
                    break;
                case "FahrzeugtypenDiagramm":
                    out.println((getCarTypeDiagram()));
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

    private ServletContext getContext() {
        return getServletConfig().getServletContext();
    }

    private float getSum() {
        return (float)Arrays.stream(cars())
                .filter(car -> !car.isParking())
                .mapToDouble(car -> car.getPrice())
                .sum()/100;
    }

    private float getAvg() {
        return (float) Arrays.stream(cars())
                        .filter(car -> !car.isParking())
                        .mapToDouble(car -> car.getPrice())
                        .average().orElse(0.0)/100;
    }

    private int getCarCount() {
        return (int) Arrays.stream(cars())
                        .filter(car -> !car.isParking())
                        .count();
    }

    private String getDiagram(){
        String licencePlates = "";
        String durations = "";
        String prices = "";

        for (int i=0; i<cars().length; i++){
            licencePlates += "\"" + Car.licencePlateArray(cars())[i] + "\",";
            durations += "\"" + Float.parseFloat(Car.durationArray(cars())[i])/100 + "\",";
            prices += "\"" + Float.parseFloat(Car.priceArray(cars())[i])/100 + "\",";
        }
        licencePlates = "[" + licencePlates.substring(0,licencePlates.length()-1) + "]";
        durations = "[" + durations.substring(0,durations.length()-1) + "]";
        prices = "[" + prices.substring(0,prices.length()-1) + "]";
        String json = "{\"data\":[{\"x\":" +
                licencePlates +
                ", \"y\":" +
                durations +
                ", \"type\": \"bar\"" +
                ", \"name\": \"Dauer\"" +
                "},{" +
                "\"x\":" +
                licencePlates +
                ", \"y\":" +
                prices +
                ", \"type\": \"bar\"" +
                ", \"name\": \"Preis\"" +
                "}" +
                "]}";

        return json;
    }

    private String getCarTypeDiagram(){
        int suv = 0, limousine = 0, kombi = 0;
        for (int i=0; i<cars().length; i++){
            if (Car.carTypeArray(cars())[i].equals("SUV")){
                suv++;
            }
            if (Car.carTypeArray(cars())[i].equals("Limousine")){
                limousine++;
            }
            if (Car.carTypeArray(cars())[i].equals("Kombi")){
                kombi++;
            }
        }

        String json = "{\"data\":[{\"labels\":" +
                "[\"SUV\", \"Limousine\", \"Kombi\"]" +
                ", \"values\":" +
                "[\"" + suv + "\"," +
                "\"" + limousine + "\"," +
                "\"" + kombi + "\"]" +
                ", \"type\": \"pie\"" +
                ", \"name\": \"Typ\"" +
                "}" +
                "]}";

        return json;
    }

    private CarIF[] cars(){
        CarIF[] cars;
        if(getContext().getAttribute("cars") == null){
            cars = new Car[0];
        } else{
            cars = (CarIF[]) getContext().getAttribute("cars");
        }
        return cars;
    }
}
