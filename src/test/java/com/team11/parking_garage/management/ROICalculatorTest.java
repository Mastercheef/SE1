/**
 * @author: ecetin2s
 */

package com.team11.parking_garage.management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ROICalculatorTest {
    private ROICalculator r1;
    private ROICalculator r2;

    @BeforeEach
    void setUp() {
        r1 = new ROICalculator("10000", "100", "0.1");
        r2 = new ROICalculator("50000", "200", "0.2");
    }

    @Test
    void returnInvest() {
        assertEquals("36.50", r1.returnInvest());
        assertEquals("29.20", r2.returnInvest());
    }

    @Test
    void amortisationMonths() {
        assertEquals("32.90", r1.amortisationMonths());
        assertEquals("41.00", r2.amortisationMonths());
    }

    @Test
    void amortisationYears() {
        assertEquals("2.74", r1.amortisationYears());
        assertEquals("3.42", r2.amortisationYears());
    }

    @Test
    void getAsJson() {
        assertEquals(
    "{" +
            "\"roi\": 36.50," +
            "\"months\": 32.90," +
            "\"years\": 2.74" +
            "}",
            r1.getAsJson()
        );
        assertEquals(
    "{" +
            "\"roi\": 29.20," +
            "\"months\": 41.00," +
            "\"years\": 3.42" +
            "}",
            r2.getAsJson()
        );
    }
}