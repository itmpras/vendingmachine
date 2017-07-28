package com.prasanna.vending.events;

import com.prasanna.vending.domain.VendableProduct;
import com.prasanna.vending.handler.EventHandler;


public class SellProductEvent implements VendingMachineEvent {
    private final VendableProduct vendableProduct;

    public SellProductEvent(VendableProduct products) {
        this.vendableProduct = products;
    }

    public VendableProduct getVendableProduct() {
        return vendableProduct;
    }

    public void applyEvent(EventHandler handler) {
        handler.vendProduct(this);
    }

    @Override
    public String toString() {
        return "SellProductEvent{" +
                "vendableProduct=" + vendableProduct +
                '}';
    }
}
