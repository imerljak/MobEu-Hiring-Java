package com.mobiquityinc.packer;

import java.math.BigDecimal;

/**
 * Thing
 */
public class Thing {

    private final int index;
    private final float weight;
    private final BigDecimal price;

    public Thing(int index, float weight, BigDecimal price) {
        this.index = index;
        this.weight = weight;
        this.price = price;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

}