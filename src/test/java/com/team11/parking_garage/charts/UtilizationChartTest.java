package com.team11.parking_garage.charts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilizationChartTest {
    private static final String commaString = "\",\"";

    UtilizationChart utilizationChart;
    String layout;
    String data;

    @BeforeEach
    void setUp() {
        String t1 = "1625744456000";
        String t2 = "1625744457000";
        String t3 = "1625744458000";
        String t4 = "1625744459000";
        String t5 = "1625744460000";
        String t6 = "1625744461000";
        String t7 = "1625744462000";
        String t8 = "1625744463000";
        String t9 = "1625744464000";
        String t10 = "1625744465000";

        String u1 = "10";
        String u2 = "20";
        String u3 = "30";
        String u4 = "40";
        String u5 = "50";
        String u6 = "60";
        String u7 = "70";
        String u8 = "80";
        String u9 = "90";
        String u10 = "100";


        List<String[]> utilizationList = new ArrayList<>();
        utilizationList.add(new String[]{t1,u1});
        utilizationList.add(new String[]{t2,u2});
        utilizationList.add(new String[]{t3,u3});
        utilizationList.add(new String[]{t4,u4});
        utilizationList.add(new String[]{t5,u5});
        utilizationList.add(new String[]{t6,u6});
        utilizationList.add(new String[]{t7,u7});
        utilizationList.add(new String[]{t8,u8});
        utilizationList.add(new String[]{t9,u9});
        utilizationList.add(new String[]{t10,u10});

        layout = "{\"xaxis\":" +
                "{\"title\":{\"text\":\"Zeitpunkt der Messung\"}}," +
                "\"yaxis\":{\"title\":{\"text\":\"Auslastung (in %)\"}}," +
                "\"title\":{\"text\":\"Momentane Auslastung des Parkhauses\"}," +
                "\"font\":{\"color\":\"#2196f3\"}," +
                "\"paper_bgcolor\":\"rgba(0,0,0,0)\"," +
                "\"plot_bgcolor\":\"rgba(0,0,0,0)\"}";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss:SS");

        String times = "[\"" +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t1)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t2)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t3)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t4)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t5)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t6)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t7)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t8)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t9)), ZoneId.systemDefault()).format(formatter) +
                commaString +
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(t10)), ZoneId.systemDefault()).format(formatter) +
                "\"]";

        String utilization = "[" +
                u1 +
                "," +
                u2 +
                "," +
                u3 +
                "," +
                u4 +
                "," +
                u5 +
                "," +
                u6 +
                "," +
                u7 +
                "," +
                u8 +
                "," +
                u9 +
                "," +
                u10 +
                "]";

        data = "[{\"x\":" + times + "," +
                "\"y\":" + utilization + "," +
                "\"type\":\"line\"," +
                "\"name\":\"Maximale Auslastung\"}]";

        utilizationChart = new UtilizationChart(utilizationList);
    }

    @Test
    void getLayout() {
        assertEquals(layout,utilizationChart.getLayout().toString());
    }

    @Test
    void getData() {
        assertEquals(data,utilizationChart.getData().toString());

    }

    @Test
    void getJson() {
        assertEquals("{\"data\":" +
                        data + "," +
                        "\"layout\":" +
                        layout +
                        "}",
                utilizationChart.getJson().toString()
        );
    }
}