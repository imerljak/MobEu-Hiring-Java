package com.mobiquityinc.constraints;

import com.mobiquityinc.packer.Package;

import java.util.List;

public class PackageConstraintsChecker extends AbstractConstraintChecker<Package> {

    private static final Float MAX_WEIGHT = 100f;

    @Override
    protected void configureConstraints(List<Constraint<Package>> constraints) {
        constraints.add(new MaxWeightConstraint<>(MAX_WEIGHT, Package::getMaximumWeight));
    }

}
