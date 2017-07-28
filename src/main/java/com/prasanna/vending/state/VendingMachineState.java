package com.prasanna.vending.state;

import com.prasanna.vending.state.inventory.InventoryState;
import com.prasanna.vending.state.userbalance.UserBalanceState;

public interface VendingMachineState {
    UserBalanceState getUserBalanceState();

    InventoryState getInventoryState();
}
