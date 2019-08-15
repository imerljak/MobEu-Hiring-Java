package com.mobiquityinc.constraints;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Thing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MaxPriceConstraintTest {

    @ParameterizedTest(name = "[{index}] price: {0}, limit: {1}")
    @CsvSource({"100,99.99", "150,110", "10,5", "1000,999.99"})
    void shouldThrowExceptionIfConstraintIsViolated(String price, String limit) {
        final Thing thingToTest = new Thing(0, 0, new BigDecimal(price));
        assertThrows(APIException.class, () ->
                new MaxPriceConstraint<>(new BigDecimal(limit), Thing::getPrice).check(thingToTest));
    }

    @ParameterizedTest(name = "[{index}] price: {0}, limit: {1}")
    @CsvSource({"100,100", "150,150.01", "10,11", "1000,1000.01"})
    void shouldNotThrowExceptionIfConstraintNotViolated(String price, String limit) {
        final Thing thingToTest = new Thing(0, 0, new BigDecimal(price));
        assertDoesNotThrow(() ->
                new MaxPriceConstraint<>(new BigDecimal(limit), Thing::getPrice).check(thingToTest));
    }

}