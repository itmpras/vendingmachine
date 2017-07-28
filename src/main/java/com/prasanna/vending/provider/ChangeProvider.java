package com.prasanna.vending.provider;

import com.prasanna.vending.domain.Coin;
import com.prasanna.vending.state.machinebalance.MachineBalance;

import java.math.BigDecimal;
import java.util.List;

public interface ChangeProvider {

    List<Coin> provideChangeFor(MachineBalance machineBalance, BigDecimal changeRequested);
}
