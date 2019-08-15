package com.mobiquityinc.constraints;

import com.mobiquityinc.packer.Package;

import java.util.List;

public class PackageConstraintsChecker extends AbstractConstraintChecker<Package> {

    @Override
    protected void configureConstraints(List<Constraint<Package>> constraints) {
        constraints.add(new MaxWeightConstraint<>(100.00f, Package::getMaximumWeight));
    }

}
