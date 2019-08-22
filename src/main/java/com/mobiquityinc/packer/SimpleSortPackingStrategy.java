package com.mobiquityinc.packer;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * This class implements a simple sorting strategy to pack things into a package base on the
 * ordering of things by its price in descending order and weight ascended.
 *
 * It is not an optimal solution for the knapsack problem since it doesn't solve every possible case depending on
 * the properties of the things, although it technically solves the problem, it does not take into account the
 * efficiency of the packing in suck a way that a high priced heavy thing could be given preference over to many lighter
 * things that when grouped sum to a higher price overall.
 */
public class SimpleSortPackingStrategy implements PackingStrategy {

  @Override
  public void pack(Package aPackage, Collection<Thing> thingsToPack) {
    Stream.of(thingsToPack)
      .flatMap(Collection::stream)
      .sorted(Comparator.comparing(Thing::getPrice).reversed().thenComparing(Thing::getWeight))
      .forEach(aPackage::putThing);
  }
}
