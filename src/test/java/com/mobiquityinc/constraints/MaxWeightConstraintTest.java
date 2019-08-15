package com.mobiquityinc.constraints;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Thing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MaxWeightConstraintTest {

    @ParameterizedTest(name = "[{index}] weight: {0}, limit: {1}")
    @CsvSource({"100,99.99", "150,110", "10,5", "1000,999.99"})
    public void shoultThrowExceptionIfConstraintIsViolated(String weight, String limit) {
        final Thing thingToTest = new Thing(0, Float.valueOf(weight), BigDecimal.ZERO);
        assertThrows(APIException.class, () ->
                new MaxWeightConstraint<>(Float.valueOf(limit), Thing::getWeight).check(thingToTest));
    }

    @ParameterizedTest(name = "[{index}] weight: {0}, limit: {1}")
    @CsvSource({"100,100", "150,150.01", "10,11", "1000,1000.01"})
    public void shoultNotThrowExceptionIfConstraintIsntViolated(String weight, String limit) {
        final Thing thingToTest = new Thing(0, 0, BigDecimal.ZERO);
        assertDoesNotThrow(() ->
                new MaxWeightConstraint<>(Float.valueOf(limit), Thing::getWeight).check(thingToTest));
    }

}