package com.mobiquityinc.packer;

import com.mobiquityinc.constraints.ThingConstraintsChecker;
import com.mobiquityinc.exception.APIException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for building Things instances based on their String representation extracted
 * from the formatted .txt files.
 */
class ThingBuilder {

    private final static ThingConstraintsChecker constraintsChecker = new ThingConstraintsChecker();

    private ThingBuilder() {
    }

    /**
     * Builds many Thing's and returns a List of them..
     * @param manyThingsString many things as a String
     * @return List of Thing
     * @throws APIException when some constraint for a Thing is violated.
     */
    static List<Thing> build(String[] manyThingsString) throws APIException {
        final List<Thing> extractedThings = new ArrayList<>();

        for (String stringThing : manyThingsString) {
            extractedThings.add(build(stringThing));
        }
        return extractedThings;
    }

    /**
     * Builds one Thing and return it..
     * @param thingString a 'thing' as a String
     * @return an instance of a Thing
     * @throws APIException when some constraint for a Thing is violated.
     */
    static Thing build(String thingString) throws APIException {
        final String[] splitThing = thingString.replaceAll("[()€]", "").split(",");

        final Integer index = Integer.valueOf(splitThing[0]);
        final Float weight = Float.valueOf(splitThing[1]);
        final BigDecimal price = new BigDecimal(splitThing[2]);

        final Thing extractedThing = new Thing(index, weight, price);

        constraintsChecker.check(extractedThing);

        return extractedThing;
    }
}
