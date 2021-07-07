package com.team11.parking_garage;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.List;

public class Utilization {
    private static Utilization instance = new Utilization();
    private static final int DEFAULT_MAX = ParkingGarageServlet.DEFAULT_MAX;

    private Utilization(){}

    public static Utilization getInstance() {
        return instance;
    }

    public int getUtilization(List<CarIF> cars, ServletContext context) {
        int maxCars;
        if (context.getAttribute("cfgMax") == null) {
            maxCars = DEFAULT_MAX;
        } else {
            maxCars = Integer.parseInt((String)(context.getAttribute("cfgMax")));
        }
        float currentCars = cars.stream().filter(CarIF::isParking).count();
        return (int) (currentCars / maxCars * 100);
    }

    public List<String[]> getUtilizationNow(List<String[]> utilizationList, List<CarIF> cars, ServletContext context) {
        Date now = new Date();
        long unixtimestamp = now.getTime();
        String[] stat = new String[] {String.valueOf(unixtimestamp), String.valueOf(this.getUtilization(cars, context))};
        utilizationList.add(stat);
        return utilizationList;
    }
}
