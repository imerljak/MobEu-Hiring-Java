package com.mobiquityinc.packer;

import com.mobiquityinc.constraints.PackageConstraintsChecker;
import com.mobiquityinc.exception.APIException;

class PackageBuilder {

    private static final PackageConstraintsChecker contraintsChecker = new PackageConstraintsChecker();

    private PackageBuilder() {
    }

    static Package build(String stringWeight) throws APIException {
        final float packageWeight = Float.valueOf(stringWeight);
        final Package aPackage = new Package(packageWeight);

        contraintsChecker.check(aPackage);

        return aPackage;
    }

}
