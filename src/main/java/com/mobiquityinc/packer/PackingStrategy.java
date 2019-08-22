package com.mobiquityinc.packer;

import java.util.Collection;

public interface PackingStrategy {

  void pack(Package aPackage, Collection<Thing> thingsToPack);

}
