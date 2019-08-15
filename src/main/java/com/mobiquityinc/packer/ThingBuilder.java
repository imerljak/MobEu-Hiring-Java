package com.mobiquityinc.packer;

import com.mobiquityinc.constraints.ThingContraintsChecker;
import com.mobiquityinc.exception.APIException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class ThingBuilder {

    private final static ThingContraintsChecker contraintsChecker = new ThingContraintsChecker();

    private ThingBuilder() {
    }

    static List<Thing> build(String[] manyThingsString) throws APIException {
        final List<Thing> extractedThings = new ArrayList<>();

        for (String stringifiedThing : manyThingsString) {
            extractedThings.add(build(stringifiedThing));
        }
        return extractedThings;
    }

    static Thing build(String thingString) throws APIException {
        final String[] splittedThing = thingString.replaceAll("[()€]", "").split(",");

        final Integer index = Integer.valueOf(splittedThing[0]);
        final Float weight = Float.valueOf(splittedThing[1]);
        final BigDecimal price = new BigDecimal(splittedThing[2]);

        final Thing extractedThing = new Thing(index, weight, price);

        contraintsChecker.check(extractedThing);

        return extractedThing;
    }
}
