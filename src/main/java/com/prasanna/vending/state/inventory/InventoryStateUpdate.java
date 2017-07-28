package com.prasanna.vending.state.inventory;

import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.events.ServiceEvent;


public interface InventoryStateUpdate extends InventoryState {
    InventoryStateUpdate updateInventory(SellProductEvent event);
    InventoryStateUpdate performService(ServiceEvent serviceEvent);
}
