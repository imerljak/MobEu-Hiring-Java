package com.mobiquityinc.constraints;

import com.mobiquityinc.exception.APIException;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConstraintChecker<T> implements Constraint<T> {

    private final List<Constraint<T>> constraints;

    AbstractConstraintChecker() {
        constraints = new ArrayList<>();
        configureConstraints(constraints);
    }

    protected abstract void configureConstraints(List<Constraint<T>> constraints);

    @Override
    public void check(T subject) throws APIException {
        for (Constraint<T> constraint : constraints) {
            constraint.check(subject);
        }
    }
}
