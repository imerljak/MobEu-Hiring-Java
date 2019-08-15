package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a package that contains "Things"
 */
public class Package {
    private final float maximumWeight;
    private float currentWeight = 0;

    private final List<Thing> things = new ArrayList<>();

    public Package(float maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    /**
     * @return the maximumWeight
     */
    public float getMaximumWeight() {
        return maximumWeight;
    }

    /**
     * @return the currentWeight
     */
    public float getCurrentWeight() {
        return currentWeight;
    }

    /**
     * @return the things
     */
    public Collection<Thing> getThings() {
        return Collections.unmodifiableCollection(things);
    }

    public void putThing(Thing thingToPut) {
        final float addedWeight = getCurrentWeight() + thingToPut.getWeight();

        if (thingToPut.getWeight() > getMaximumWeight() || addedWeight > getMaximumWeight()) {
            return;
        }

        this.currentWeight = addedWeight;
        this.things.add(thingToPut);
    }

    @Override
    public String toString() {
        return getThings().isEmpty() ? "-" : getThings()
        .parallelStream()
        .map(Thing::getIndex)
        .map(Object::toString)
        .collect(Collectors.joining(","));
    }
}