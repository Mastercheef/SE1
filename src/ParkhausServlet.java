import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
            getContext().setAttribute("cars", cars);
        } else if (postParams[0].equals("leave")){
            float total = Float.parseFloat(postParams[4]);
            total /= 100;
            float sum;
            if (getContext().getAttribute("sum") == null) {
                sum = 0f;
            } else {
                sum = (float) getContext().getAttribute("sum");
            }
            sum += total;
            getContext().setAttribute("sum", sum);


            int carCount;
            if (getContext().getAttribute("carCount") == null) {
                carCount = 0;
            } else {
                carCount = (int) getContext().getAttribute("carCount");
            }
            carCount++;
            getContext().setAttribute("carCount", carCount);

            CarIF[] cars = cars();
            for (int i=0; i<cars.length; i++){
                if(cars[i].getTicketId()==postParams[5]){
                    cars[i].leave(Integer.parseInt(postParams[3]), Integer.parseInt(postParams[4]));
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
        return getContext().getAttribute("sum") == null ? 0f : (float)getContext().getAttribute("sum");
    }

    private float getAvg() {
        if (getContext().getAttribute("sum") == null) {
            return 0f;
        } else {
            float sum = (float)getContext().getAttribute("sum");
            int carCount = (int)getContext().getAttribute("carCount");
            return sum / carCount;
        }
    }

    private int getCarCount() {
        if (getContext().getAttribute("carCount") == null) {
            return 0;
        } else {
            return (int)getContext().getAttribute("carCount");
        }
    }

    private JsonObject getDiagram(){
        JsonObject root = Json.createObjectBuilder()
                .add("data" , Json.createObjectBuilder()
                        .add("x", Car.ticketIdArray(cars()).toString())
                        .add("y", Car.durationArray(cars()).toString())
                        .add("type", "bar")
                        .add("name","Duration")
                    ).build();
        System.out.println(root);
        return root;
    }

    private String getCarTypeDiagram(){
        String htmlout = "";
        htmlout += "<div> FamDiagramm </div>";

        return htmlout;
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
