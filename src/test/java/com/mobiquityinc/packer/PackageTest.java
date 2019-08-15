package com.mobiquityinc.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

/**
 * PackageTest
 */
public class PackageTest {

    @Test
    public void shoudNotIncludeThingsWithWeightAboveMaximum() {
        final Package p = new Package(8.0f);
        p.putThing(new Thing(1, 15.3f, new BigDecimal("34.00")));

        assertEquals(8.0, p.getMaximumWeight(), 0.0, "Maximum weight must reflect what was initialized in the constructor!");
        assertEquals(0.0, p.getCurrentWeight(), 0.0, "Current weight should be 0.0 since it shouldn't have added anything.");
        assertTrue(p.getThings().isEmpty(), "Things should be empty since the maximum weight was exceeded by the Thing added.");
    }

    @Test
    public void shoudNotIncludeMoreThingsIfAccumulatedWeightExceededMaximum() {
        final Package p = new Package(81.0f);
        p.putThing(new Thing(1,53.38f,new BigDecimal("45"))); // 53.38 
        p.putThing(new Thing(2,88.62f,new BigDecimal("98"))); // exceeds
        p.putThing(new Thing(3,78.48f,new BigDecimal("3"))); // exceeds
        p.putThing(new Thing(4,72.30f,new BigDecimal("76"))); // exceeds
        p.putThing(new Thing(5,30.18f,new BigDecimal("9"))); // exceeds
        p.putThing(new Thing(6,46.34f,new BigDecimal("48"))); // exceeds
        
        assertEquals(81, p.getMaximumWeight(), 0.0);
        assertEquals(53.38, p.getCurrentWeight(), 0.001);
        assertFalse(p.getThings().isEmpty());
    }

    @Test
    public void shouldOverrideToStringToPrintCommaSeparatedListOfThingsIndexes() {
        final Package p = new Package(10f);
        p.putThing(new Thing(1, 2f, new BigDecimal("10")));
        p.putThing(new Thing(2, 5f, new BigDecimal("10")));
        p.putThing(new Thing(3, 3f, new BigDecimal("10")));

        String expectedString = String.join(",", "1", "2" ,"3");
        assertEquals(expectedString, p.toString());
    }
}