package com.prasanna.vending.domain;

import java.math.BigDecimal;

/**
 * Created by pgopal on 27/07/2017.
 */
public enum VendableProduct {
    A(new BigDecimal(0.65)),
    B(new BigDecimal(1)),
    C(new BigDecimal(1.50));

    VendableProduct(BigDecimal price) {
        this.price = price.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private final BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return " {Product=" + name() + ", Price=" + price + "}";
    }
}
