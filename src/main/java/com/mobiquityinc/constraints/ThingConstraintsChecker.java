package com.mobiquityinc.constraints;

import com.mobiquityinc.packer.Thing;

import java.math.BigDecimal;
import java.util.List;

public class ThingConstraintsChecker extends AbstractConstraintChecker<Thing> {

    private static final BigDecimal MAX_PRICE = BigDecimal.TEN.multiply(BigDecimal.TEN);
    private static final Float MAX_WEIGHT = 100f;

    @Override
    protected void configureConstraints(List<Constraint<Thing>> constraints) {
        constraints.add(new MaxPriceConstraint<>(MAX_PRICE, Thing::getPrice));
        constraints.add(new MaxWeightConstraint<>(MAX_WEIGHT, Thing::getWeight));
    }

}
