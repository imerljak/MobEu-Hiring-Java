package com.mobiquityinc.constraints;

import com.mobiquityinc.exception.APIException;

import java.util.function.Function;

public class MaxWeightConstraint<T> implements Constraint<T> {
    private final float amount;
    private final Function<T, Float> function;

    public MaxWeightConstraint(float amount, Function<T, Float> function) {
        this.amount = amount;
        this.function = function;
    }

    @Override
    public void check(T subject) throws APIException {
        if (function.apply(subject) > amount) {
            throw new APIException("Max weight is " + amount);
        }
    }
}
