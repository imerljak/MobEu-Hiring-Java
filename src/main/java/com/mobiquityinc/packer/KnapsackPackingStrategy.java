package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.*;

/**
 * This class implements a dynamic programming solution of the knapsack algorithm.
 * To find out the selected items from the solution table we backtrack it from the last value,
 * checking if its included in the upper row, if not, pick it and go left by the i:th items weight,
 * to find out the where the next item value was added until the end.
 *
 */
public class KnapsackPackingStrategy implements PackingStrategy {

    @Override
    public void pack(Package aPackage, Collection<Thing> thingsToPack) {

        int maxWeight = floatToInt(aPackage.getMaximumWeight());

        dpKnapsack(maxWeight, thingsToPack.toArray(new Thing[]{})).forEach(aPackage::putThing);
    }

    private List<Thing> dpKnapsack(int capacity, Thing[] things) {

        int itemsCount = things.length;

        BigDecimal[][] table = new BigDecimal[itemsCount + 1][capacity + 1];

        // Sort items by weight ascending
        Arrays.parallelSort(things, Comparator.comparing(Thing::getWeight));

        for (int i = 0; i <= itemsCount; i++) {

            for (int w = 0; w <= capacity; w++) {

                // Fill with ZERO first line & row to serve as reference when comparing.
                if (i == 0 || w == 0) {
                    table[i][w] = BigDecimal.ZERO;

                } else if (floatToInt(things[i - 1].getWeight()) <= w) {

                    /*T[i][w] = max(v[i-1] + T[i-1][w - w[i-1], w[i-1])*/
                    table[i][w] = things[i - 1].getPrice().add(table[i - 1][w - floatToInt(things[i - 1].getWeight())]).max(table[i - 1][w]);

                } else {
                    table[i][w] = table[i - 1][w];
                }
            }
        }

        return backtrackItemsFromTable(table, things, capacity);
    }

    private List<Thing> backtrackItemsFromTable(BigDecimal[][] table, Thing[] things, int capacity) {
        final List<Thing> selectedThings = new ArrayList<>();

        int temp = capacity;

        for (int i = things.length; i > 0 && temp > 0; i--) {

            // if value on top is equal to this, go to previous line.
            if (table[i][temp].equals(table[i - 1][temp])) continue;

            // found the item relative to the selected item, add it to the result list.
            selectedThings.add(things[i - 1]);

            temp -= floatToInt(things[i - 1].getWeight());
        }

        return selectedThings;
    }

    private int floatToInt(float floatWeight) {
        return (int) (floatWeight);
    }

}