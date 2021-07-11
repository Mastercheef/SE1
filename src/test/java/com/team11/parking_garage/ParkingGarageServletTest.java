package com.team11.parking_garage;

import com.team11.parking_garage.charts.*;
import io.github.netmikey.logunit.api.LogCapturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingGarageServletTest {
    private static final long NOW_LONG = LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)).toEpochMilli();
    private static final String NOW_STRING = String.valueOf(NOW_LONG);
    private static final String TICKET_ID_1 = "135e6d8aaa2ea8b8e68e013b910e8e6e";
    private static final String ARRIVAL_JSON = "\"arrival\": " + NOW_STRING + ",";
    private static final String DEPARTURE_JSON = "\"departure\": " + (NOW_LONG + 51513300L) + ",";
    private static final String DURATION_JSON = "\"duration\": 51513300,";

    private final ParkingGarageServlet servlet = new ParkingGarageServlet();
    private final ServletConfig postConfig = new MockServletConfig();
    private final ServletConfig servletConfig = new MockServletConfig();

    @RegisterExtension
    LogCapturer logs = LogCapturer.create().captureForLogger("parking_garage.ParkingGarageServlet");

    @BeforeEach
    void setUp() throws ServletException, IOException {
        servlet.init(servletConfig);
        // Filling garage with data
        String[] csvs = new String[] {
                "enter,42,1625756316229,_,_,135e6d8aaa2ea8b8e68e013b910e8e6e,#b4209b,1,Senior,SUV,SU-X 3",
                "enter,35,1625756316229,_,_,a531b24e184b1e8b5fac3d649a00f913,#67e789,2,Abonnent,Kombi,SU-J 69",
                "enter,8,1625756329618,_,_,068428844fb0db8d9ae4a241943e718b,#e4796c,1,Senior,Limousine,SU-K 18",
                "occupied,Car(8)",
                "leave,42,1625756316229,51513300,2862,135e6d8aaa2ea8b8e68e013b910e8e6e,#b4209b,1,Senior,SUV,SU-X 3",
                "leave,35,1625756316229,51513300,2862,a531b24e184b1e8b5fac3d649a00f913,#67e789,2,Abonnent,Kombi,SU-J 69",
        };

        MockHttpServletResponse setupResponse = new MockHttpServletResponse();

        for (String csv : csvs) {
            MockHttpServletRequest setupRequest = new MockHttpServletRequest();
            setupRequest.setContent(csv.getBytes(StandardCharsets.UTF_8));
            servlet.doPost(setupRequest, setupResponse);
        }
    }

    @Test
    void doPost() throws IOException, ServletException {
        servlet.init(postConfig);

        // CSV-Strings for entering, leaving and occupied
        String enterCSV = "enter,42," + NOW_STRING + ",_,_,135e6d8aaa2ea8b8e68e013b910e8e6e,#b4209b,1,Senior,SUV,SU-X 3";
        String enter2CSV = "enter,8," + NOW_STRING + ",_,_,068428844fb0db8d9ae4a241943e718b,#e4796c,1,Senior,Limousine,SU-K 18";
        String occupiedCSV = "occupied,Car(8)";
        String leaveCSV = "leave,42," + NOW_STRING + ",51513300,2862,135e6d8aaa2ea8b8e68e013b910e8e6e,#b4209b,1,Senior,SUV,SU-X 3";

        // enterString FIRST CAR
        // Setting up enterCSV POST request
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setContent(enterCSV.getBytes(StandardCharsets.UTF_8));

        // Send POST request
        servlet.doPost(request, response);

        // Test log
        logs.assertContains("New Car with Nr: 42 entered");

        // Test CarList
        List<CarIF> enteredCarList = (List<CarIF>) servlet.getContext().getAttribute("cars");
        assertEquals(1, enteredCarList.size());
        CarIF enteredCar = enteredCarList.get(0);
        assertEquals(NOW_LONG, enteredCar.getArrival());
        assertEquals("SUV", enteredCar.getCarType());
        assertEquals("#b4209b", enteredCar.getColor());
        assertEquals("SU-X 3", enteredCar.getLicencePlate());
        assertEquals(42, enteredCar.getNr());
        assertEquals(1, enteredCar.getSpace());
        assertEquals(TICKET_ID_1, enteredCar.getTicketId());


        // enterString SECOND CAR
        // Setting up enter2CSV POST request
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setContent(enter2CSV.getBytes(StandardCharsets.UTF_8));

        // Send POST request
        servlet.doPost(request, response);

        // Test log
        logs.assertContains("New Car with Nr: 8 entered");

        // Test CarList
        List<CarIF> enteredTwoCarList = (List<CarIF>) servlet.getContext().getAttribute("cars");
        assertEquals(2, enteredTwoCarList.size());
        CarIF enteredSecondCar = enteredCarList.get(1);
        assertEquals(NOW_LONG, enteredSecondCar.getArrival());
        assertEquals("Limousine", enteredSecondCar.getCarType());
        assertEquals("#e4796c", enteredSecondCar.getColor());
        assertEquals("SU-K 18", enteredSecondCar.getLicencePlate());
        assertEquals(8, enteredSecondCar.getNr());
        assertEquals(1, enteredSecondCar.getSpace());
        assertEquals("068428844fb0db8d9ae4a241943e718b", enteredSecondCar.getTicketId());


        // OCCUPIED
        // Setting up occupiedCSV POST request
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setContent(occupiedCSV.getBytes(StandardCharsets.UTF_8));

        // Send POST request
        servlet.doPost(request, response);

        // Test log
        logs.assertContains("Car with Nr: 8 deleted");

        // Test CarList
        List<CarIF> occupiedCarList = (List<CarIF>) servlet.getContext().getAttribute("cars");
        assertEquals(1, occupiedCarList.size());
        CarIF occupiedCar = occupiedCarList.get(0);
        assertEquals(NOW_LONG, occupiedCar.getArrival());
        assertEquals("SUV", occupiedCar.getCarType());
        assertEquals("#b4209b", occupiedCar.getColor());
        assertEquals("SU-X 3", occupiedCar.getLicencePlate());
        assertEquals(42, occupiedCar.getNr());
        assertEquals(1, occupiedCar.getSpace());
        assertEquals(TICKET_ID_1, occupiedCar.getTicketId());


        // LEAVE
        // Setting up leaveCSV POST request
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setContent(leaveCSV.getBytes(StandardCharsets.UTF_8));

        // Send POST request
        servlet.doPost(request, response);

        // Test log
        logs.assertContains("Car with Nr: 42 left");

        // Test CarList
        List<CarIF> leaveCarList = (List<CarIF>) servlet.getContext().getAttribute("cars");
        assertEquals(1, leaveCarList.size());
        assertFalse(leaveCarList.get(0).isParking());

        // Test Tickets
        List<Ticket> leaveTicketList = (List<Ticket>) servlet.getContext().getAttribute("tickets");
        assertEquals(1, leaveTicketList.size());
        Ticket leaveTicket = leaveTicketList.get(0);
        assertEquals(NOW_LONG, leaveTicket.getArrival());
        assertEquals(42, leaveTicket.getNr());
        assertEquals(NOW_LONG + 51513300L, leaveTicket.getDeparture());
        assertEquals(51513300L, leaveTicket.getDuration());
        assertEquals(TICKET_ID_1, leaveTicket.getId());
        assertEquals(new BigDecimal("24.31"), leaveTicket.getPrice());
        assertEquals(
                "{" +
                "\"nr\": 42," +
                        ARRIVAL_JSON +
                        DEPARTURE_JSON +
                        DURATION_JSON +
                "\"licensePlate\": \"SU-X 3\"," +
                "\"vehicleType\": \"SUV\"," +
                "\"customerType\": \"Senior\"," +
                "\"price\": 24.31," +
                "\"ticketId\": \"135e6d8aaa2ea8b8e68e013b910e8e6e\"" +
                "}",
                leaveTicket.getAsJson()
        );


        // CSV-String for change POSTs
        String changeMaxCSV = "change_max,20,21";
        String changeOpenFromCSV = "change_open_from,0,1";
        String changeOpenToCSV = "change_open_to,24,23";

        // CHANGE MAX
        // Setting up changeMaxCSV POST request
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setContent(changeMaxCSV.getBytes(StandardCharsets.UTF_8));

        // Send POST request
        servlet.doPost(request, response);

        // Test Config
        assertEquals("21", servlet.getContext().getAttribute("cfgMax"));


        // CHANGE OPEN FROM
        // Setting up changeOpenFromCSV POST request
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setContent(changeOpenFromCSV.getBytes(StandardCharsets.UTF_8));

        // Send POST request
        servlet.doPost(request, response);

        // Test Config
        assertEquals("1", servlet.getContext().getAttribute("cfgFrom"));


        // CHANGE OPEN TO
        // Setting up changeOpenToCSV POST request
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setContent(changeOpenToCSV.getBytes(StandardCharsets.UTF_8));

        // Send POST request
        servlet.doPost(request, response);

        // Test Config
        assertEquals("23", servlet.getContext().getAttribute("cfgTo"));


        // CSV-String for management POSTs
        String roiCSV = "roi,1000,0.1,0.5";
        String incomestatementCSV = "incomestatement,0.5";

        // ROI
        // Setting up roiCSV POST request
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setContent(roiCSV.getBytes(StandardCharsets.UTF_8));

        // Send POST request
        servlet.doPost(request, response);

        assertEquals(
                "{" +
                "\"roi\": 70.00," +
                "\"months\": 17.10," +
                "\"years\": 1.43" +
                "}",
                response.getContentAsString().trim()
        );

        // INCOME STATEMENT
        // Setting up incomestatementCSV POST request
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setContent(incomestatementCSV.getBytes(StandardCharsets.UTF_8));

        // Send POST request
        servlet.doPost(request, response);

        assertEquals(
                "{" +
                        "\"turnover\": 24.31," +
                        "\"taxes\": 4.62," +
                        "\"turnoverAfterTax\": 19.69," +
                        "\"cost\": 0.50," +
                        "\"profit\": 19.19" +
                        "}",
                response.getContentAsString().trim()
        );

        // CSV-String for unknown POSTs
        String unknownCSV = "foo,bar";

        // Setting up unknownCSV POST request
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setContent(unknownCSV.getBytes(StandardCharsets.UTF_8));

        // Send POST request
        servlet.doPost(request, response);

        // Test log
        logs.assertContains("Unknown POST: \"foo\" detected.");
    }

    @Test
    void doGet() throws IOException {
        // Setting up GET requests
        MockHttpServletRequest sumReq = new MockHttpServletRequest();
        sumReq.setParameter("cmd", "sum");

        MockHttpServletRequest averagePriceReq = new MockHttpServletRequest();
        averagePriceReq.setParameter("cmd", "averagePrice");

        MockHttpServletRequest ticketCountReq = new MockHttpServletRequest();
        ticketCountReq.setParameter("cmd", "ticketCount");

        MockHttpServletRequest averageDiagramReq = new MockHttpServletRequest();
        averageDiagramReq.setParameter("cmd", "averageDiagram");

        MockHttpServletRequest carTypeDiagramReq = new MockHttpServletRequest();
        carTypeDiagramReq.setParameter("cmd", "carTypeDiagram");

        MockHttpServletRequest customerDiagramReq = new MockHttpServletRequest();
        customerDiagramReq.setParameter("cmd", "customerDiagram");

        MockHttpServletRequest subDurationDiagramReq = new MockHttpServletRequest();
        subDurationDiagramReq.setParameter("cmd", "subDurationDiagram");

        MockHttpServletRequest utilizationReq = new MockHttpServletRequest();
        utilizationReq.setParameter("cmd", "utilization");

        MockHttpServletRequest utilizationDiagramReq = new MockHttpServletRequest();
        utilizationDiagramReq.setParameter("cmd", "utilizationDiagram");

        MockHttpServletRequest ticketReq = new MockHttpServletRequest();
        ticketReq.setParameter("cmd", "ticket");
        ticketReq.setParameter("id", TICKET_ID_1);

        MockHttpServletRequest allTicketsReq = new MockHttpServletRequest();
        allTicketsReq.setParameter("cmd", "allTickets");

        MockHttpServletRequest configReq = new MockHttpServletRequest();
        configReq.setParameter("cmd", "config");

        MockHttpServletRequest carsReq = new MockHttpServletRequest();
        carsReq.setParameter("cmd", "cars");

        MockHttpServletRequest resetReq = new MockHttpServletRequest();
        resetReq.setParameter("cmd", "reset");

        MockHttpServletRequest fooReq = new MockHttpServletRequest();
        fooReq.setParameter("cmd", "foo");


        List<Ticket> tickets = (List<Ticket>) servlet.getContext().getAttribute("tickets");
        List<CarIF> cars = (List<CarIF>) servlet.getContext().getAttribute("cars");
        Stats stats = Stats.getInstance();

        // GET sum
        String expected = String.valueOf(stats.getSum(tickets));
        MockHttpServletResponse sumRes = new MockHttpServletResponse();
        servlet.doGet(sumReq, sumRes);
        assertEquals(expected, sumRes.getContentAsString().trim());

        // GET averagePrice
        expected = String.valueOf(stats.getAvg(tickets));
        MockHttpServletResponse averagePriceRes = new MockHttpServletResponse();
        servlet.doGet(averagePriceReq, averagePriceRes);
        assertEquals(expected, averagePriceRes.getContentAsString().trim());

        // GET ticketCount
        expected = String.valueOf(stats.getCarCount(tickets));
        MockHttpServletResponse ticketCountRes = new MockHttpServletResponse();
        servlet.doGet(ticketCountReq, ticketCountRes);
        assertEquals(expected, ticketCountRes.getContentAsString().trim());

        // GET averageDiagram
        expected = new AveragePriceDuration(tickets).getJson();

        MockHttpServletResponse averageDiagramRes = new MockHttpServletResponse();
        servlet.doGet(averageDiagramReq, averageDiagramRes);

        assertEquals(expected, averageDiagramRes.getContentAsString().trim());

        // GET carTypeDiagram
        expected = new CarType(cars).getJson();

        MockHttpServletResponse carTypeDiagramRes = new MockHttpServletResponse();
        servlet.doGet(carTypeDiagramReq, carTypeDiagramRes);

        assertEquals(expected, carTypeDiagramRes.getContentAsString().trim());

        // GET customerDiagram
        expected = new CustomerType(tickets).getJson();

        MockHttpServletResponse customerDiagramRes = new MockHttpServletResponse();
        servlet.doGet(customerDiagramReq, customerDiagramRes);

        assertEquals(expected, customerDiagramRes.getContentAsString().trim());

        // GET subDurationDiagram
        List<String[]> subscriberAvg = (List<String[]>) servlet.getContext().getAttribute("subscriberAvg");
        expected = new SubscriberDuration(subscriberAvg).getJson();

        MockHttpServletResponse subDurationDiagramRes = new MockHttpServletResponse();
        servlet.doGet(subDurationDiagramReq, subDurationDiagramRes);

        assertEquals(expected, subDurationDiagramRes.getContentAsString().trim());

        // GET utilization
        Utilization utilization = Utilization.getInstance();
        expected = utilization.getUtilization(cars, servlet.getContext()) + "%";

        MockHttpServletResponse utilizationRes = new MockHttpServletResponse();
        servlet.doGet(utilizationReq, utilizationRes);

        assertEquals(expected, utilizationRes.getContentAsString().trim());

        // GET utilizationDiagram
        List<String[]> utilizationList = (List<String[]>) servlet.getContext().getAttribute("utilizationList");
        expected = new UtilizationChart(utilizationList).getJson();

        MockHttpServletResponse utilizationDiagramRes = new MockHttpServletResponse();
        servlet.doGet(utilizationDiagramReq, utilizationDiagramRes);

        assertEquals(expected, utilizationDiagramRes.getContentAsString().trim());

        // GET ticket
        expected = servlet.getTicketJsonById(TICKET_ID_1);

        MockHttpServletResponse ticketRes = new MockHttpServletResponse();
        servlet.doGet(ticketReq, ticketRes);

        assertEquals(expected, ticketRes.getContentAsString().trim());

        // GET allTickets
        expected = servlet.allTicketsAsJson();

        MockHttpServletResponse allTicketsRes = new MockHttpServletResponse();
        servlet.doGet(allTicketsReq, allTicketsRes);

        assertEquals(expected, allTicketsRes.getContentAsString().trim());

        // GET config
        MockHttpServletResponse configRes = new MockHttpServletResponse();
        servlet.doGet(configReq, configRes);

        assertEquals("20,0,24,200,2700", configRes.getContentAsString().trim());

        // GET cars
        expected = Car.getSavedCarsCSV(cars);

        MockHttpServletResponse carsRes = new MockHttpServletResponse();
        servlet.doGet(carsReq, carsRes);

        assertEquals(expected, carsRes.getContentAsString().trim());

        // GET reset
        MockHttpServletResponse resetRes = new MockHttpServletResponse();
        servlet.doGet(resetReq, resetRes);
        assertEquals(200, resetRes.getStatus());
        assertFalse(servlet.getContext().getAttributeNames().hasMoreElements());

        // GET foo
        MockHttpServletResponse fooRes = new MockHttpServletResponse();
        servlet.doGet(fooReq, fooRes);
        logs.assertContains("Unknown GET: \"foo\" detected.");
    }

    @Test
    void allTicketsAsJson() {
        assertEquals(
        "[" +
                    "{\"nr\": 42,\"arrival\": 1625756316229,\"departure\": 1625807829529,\"duration\": 51513300,\"licensePlate\": \"SU-X 3\",\"vehicleType\": \"SUV\",\"customerType\": \"Senior\",\"price\": 24.31,\"ticketId\": \"135e6d8aaa2ea8b8e68e013b910e8e6e\"}," +
                    "{\"nr\": 35,\"arrival\": 1625756316229,\"departure\": 1625807829529,\"duration\": 51513300,\"licensePlate\": \"SU-J 69\",\"vehicleType\": \"Kombi\",\"customerType\": \"Abonnent\",\"price\": 0,\"ticketId\": \"a531b24e184b1e8b5fac3d649a00f913\"}" +
                "]",
                servlet.allTicketsAsJson()
        );
    }

    @Test
    void getTicketJsonById() {
        assertEquals(
                "{" +
                "\"nr\": 42," +
                        ARRIVAL_JSON +
                        DEPARTURE_JSON +
                        DURATION_JSON +
                "\"licensePlate\": \"SU-X 3\"," +
                "\"vehicleType\": \"SUV\"," +
                "\"customerType\": \"Senior\"," +
                "\"price\": 24.31," +
                "\"ticketId\": \"135e6d8aaa2ea8b8e68e013b910e8e6e\"" +
                "}",
                servlet.getTicketJsonById(TICKET_ID_1)
        );
        assertEquals(
                "{" +
                "\"nr\": 35," +
                        ARRIVAL_JSON +
                        DEPARTURE_JSON +
                        DURATION_JSON +
                "\"licensePlate\": \"SU-J 69\"," +
                "\"vehicleType\": \"Kombi\"," +
                "\"customerType\": \"Abonnent\"," +
                "\"price\": 0," +
                "\"ticketId\": \"a531b24e184b1e8b5fac3d649a00f913\"" +
                "}",
                servlet.getTicketJsonById("a531b24e184b1e8b5fac3d649a00f913")
        );
    }
}