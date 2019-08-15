package com.mobiquityinc.packer;

import com.mobiquityinc.constraints.PackageConstraintsChecker;
import com.mobiquityinc.exception.APIException;

/**
 * Class responsible for creating a Package instance
 */
class PackageBuilder {

    private static final PackageConstraintsChecker constraintsChecker = new PackageConstraintsChecker();

    private PackageBuilder() {
    }

    /**
     * Creates a Package instance and return it.
     * @param stringWeight string representation of its maximum weight (e.g: 15.00)
     * @return A Package
     * @throws APIException when a Package constraint is violated.
     */
    static Package build(String stringWeight) throws APIException {
        final float packageWeight = Float.valueOf(stringWeight);
        final Package aPackage = new Package(packageWeight);

        constraintsChecker.check(aPackage);

        return aPackage;
    }

}
