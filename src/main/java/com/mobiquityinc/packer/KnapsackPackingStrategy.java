package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.*;

/**
 * V[i][w] = max(V[i-1][w], V[i-1][w - w[1]] + P[i]);
 */
public class KnapsackPackingStrategy implements PackingStrategy {

    private static final int MAXIMUM_WEIGHT = 100;

    @Override
    public void pack(Package aPackage, Collection<Thing> thingsToPack) {

        int maxWeight = normalizeWeight(aPackage.getMaximumWeight());

        knapsack(maxWeight, thingsToPack.toArray(new Thing[]{})).forEach(aPackage::putThing);
    }

    public List<Thing> knapsack(int capacity, Thing[] things) {

        int itemsCount = things.length;

        BigDecimal[][] table = new BigDecimal[itemsCount + 1][capacity + 1];

        // Sort items by weight ascending
        Arrays.parallelSort(things, Comparator.comparing(Thing::getWeight));

        for (int i = 0; i <= itemsCount; i++) {

            for (int w = 0; w <= capacity; w++) {

                if (i == 0 || w == 0) {
                    table[i][w] = BigDecimal.ZERO;
                } else if (normalizeWeight(things[i - 1].getWeight()) <= w) {
                    table[i][w] = things[i - 1].getPrice().add(table[i - 1][w - normalizeWeight(things[i - 1].getWeight())]).max(table[i - 1][w]);
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

            System.out.println(Arrays.toString(table[i]));

            if (table[i][temp].equals(table[i - 1][temp])) continue;

            selectedThings.add(things[i - 1]);

            temp -= normalizeWeight(things[i - 1].getWeight());
        }

        System.out.println("-----------------------");

        return selectedThings;
    }

    private int normalizeWeight(float floatWeight) {
        return (int) (floatWeight);
    }

}