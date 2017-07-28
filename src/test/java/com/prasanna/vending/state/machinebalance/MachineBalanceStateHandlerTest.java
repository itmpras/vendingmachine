package com.prasanna.vending.state.machinebalance;


import com.prasanna.vending.domain.Coin;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.LinkedList;

public class MachineBalanceStateHandlerTest {

    private MachineBalanceStateHandler stateHandler;

    @Test
    public void shouldUpdateMachineBalance() throws Exception {

        stateHandler = new MachineBalanceStateHandler(new MachineBalance());
        MachineBalanceStateUpdate machineBalanceStateUpdate = stateHandler.updateMachineBalance(new LinkedList<Coin>() {{
            add(Coin.DOLLER);
        }});


        MachineBalance machineBalance = machineBalanceStateUpdate.getMachineBalance();
        BigDecimal currentBalance = machineBalance.getBalance();
        Integer count = machineBalance.getCoinsCount().get(Coin.DOLLER);

        MatcherAssert.assertThat("MachineBalance is Updated", currentBalance.equals(Coin.DOLLER.getValue()));
        MatcherAssert.assertThat("Coin Count is Updated", count == 1);
    }
}