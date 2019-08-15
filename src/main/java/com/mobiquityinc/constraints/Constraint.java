package com.mobiquityinc.constraints;

import com.mobiquityinc.exception.APIException;

public interface Constraint<T> {

    void check(T subject) throws APIException;

}
