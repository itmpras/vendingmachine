package com.prasanna.vending.state.machinebalance;

import com.prasanna.vending.domain.Coin;
import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.ServiceEvent;

import java.math.BigDecimal;
import java.util.List;


public interface MachineBalanceStateUpdate extends MachineBalanceState {
    MachineBalanceStateUpdate updateMachineBalance(List<Coin> coin);

    MachineBalanceStateUpdate reduceMachineBalance(List<Coin> coin);

    MachineBalanceStateUpdate performService(ServiceEvent serviceEvent);


}
