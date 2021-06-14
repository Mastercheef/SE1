package com.team11.Parkhaus;

import java.io.*;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ParkhausServlet extends HttpServlet {
    Stats stats = new Stats();
    Charts charts = new Charts();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] postParams = getBody(req).split(",");
        // enter
        if (postParams[0].equals("enter")){
            enter(postParams[1], postParams[5], postParams[6], postParams[9]);
        // leave
        } else if (postParams[0].equals("leave")){
            leave(postParams[5], Integer.parseInt(postParams[3]), Integer.parseInt(postParams[4]));

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

    private void enter(String licensePlate, String ticketId, String color, String carType) {
        CarIF[] cars = getCars();

        // extend array
        CarIF[] tmpCars = new Car[cars.length+1];
        for (int i=0; i<cars.length; i++){
            tmpCars[i] = cars[i];
        }
        cars = tmpCars;

        // add new car
        cars[cars.length-1] = new Car(licensePlate,ticketId,color,carType);
        System.out.println("enter:" + licensePlate);

        setCars(cars);
    }

    private void leave(String ticketId, int duration, int price){
        CarIF[] cars = getCars();
        for (int i=0; i<cars.length; i++){
            if(cars[i].getTicketId().equals(ticketId)){
                cars[i].leave(duration, price);
                System.out.println("leave:" + getCars()[i].getLicencePlate());
            }
        }
        setCars(cars);
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