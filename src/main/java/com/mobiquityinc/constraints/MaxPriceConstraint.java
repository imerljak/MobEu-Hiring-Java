package com.mobiquityinc.constraints;

import com.mobiquityinc.exception.APIException;

import java.math.BigDecimal;
import java.util.function.Function;

public class MaxPriceConstraint<T> implements Constraint<T> {
    private final BigDecimal price;
    private final Function<T, BigDecimal> function;

    public MaxPriceConstraint(BigDecimal price, Function<T, BigDecimal> function) {
        this.price = price;
        this.function = function;
    }

    @Override
    public void check(T subject) throws APIException {
        if (function.apply(subject).compareTo(price) > 0) {
            throw new APIException("Max price is " + price);
        }
    }
}
