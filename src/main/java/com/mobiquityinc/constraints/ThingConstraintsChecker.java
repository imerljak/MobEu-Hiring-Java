package com.mobiquityinc.constraints;

import com.mobiquityinc.packer.Thing;

import java.math.BigDecimal;
import java.util.List;

public class ThingConstraintsChecker extends AbstractConstraintChecker<Thing> {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.TEN.multiply(BigDecimal.TEN);

    @Override
    protected void configureConstraints(List<Constraint<Thing>> constraints) {
        constraints.add(new MaxPriceConstraint<>(ONE_HUNDRED, Thing::getPrice));
        constraints.add(new MaxWeightConstraint<>(100.00f, Thing::getWeight));
    }

}
