package com.prasanna.vending.state.inventory;


import com.prasanna.vending.domain.VendableProduct;

import java.util.Map;

public interface InventoryState {
    Map<VendableProduct, Integer> getCurrentInventory();
}
