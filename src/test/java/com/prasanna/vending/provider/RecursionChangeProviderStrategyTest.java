package com.prasanna.vending.provider;


import com.prasanna.vending.domain.Coin;
import com.prasanna.vending.exception.ChangeDenominationNotAvailable;
import com.prasanna.vending.exception.InSufficientBalanceAvailableForChange;
import com.prasanna.vending.state.machinebalance.MachineBalance;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNull.notNullValue;

public class RecursionChangeProviderStrategyTest {
    RecursionChangeProviderStrategy changeProvider;
    MachineBalance machineBalance;

    @Test(expected = InSufficientBalanceAvailableForChange.class)
    public void shouldThrowExceptionIfBalanceIsNotAvailable() throws Exception {
        machineBalance = new MachineBalance().updateMachineBalanceBy(Coin.NICKLE);
        changeProvider = new RecursionChangeProviderStrategy();
        changeProvider.provideChangeFor(machineBalance, Coin.DOLLER.getValue());
    }

    @Test
    public void shouldProvideChangeForRequestedBalance() throws Exception {
        machineBalance = new MachineBalance().updateMachineBalanceBy(Coin.QUARTER).
                updateMachineBalanceBy(Coin.NICKLE).
                updateMachineBalanceBy(Coin.NICKLE);

        changeProvider = new RecursionChangeProviderStrategy();

        List<Coin> coins = changeProvider.provideChangeFor(machineBalance, new BigDecimal(0.30).setScale(2, BigDecimal.ROUND_HALF_EVEN));

        assertThat("Coins are null", coins, is(notNullValue()));
        assertThat("Change size", coins.size(), is(2));
        assertThat("Correct Change", coins, hasItems(Coin.QUARTER, Coin.NICKLE));
    }

    @Test
    public void shouldProvideChangeForComplexInput() throws Exception {
        machineBalance = new MachineBalance().updateMachineBalanceBy(Coin.DOLLER).
                updateMachineBalanceBy(Coin.DIME).
                updateMachineBalanceBy(Coin.NICKLE).updateMachineBalanceBy(Coin.QUARTER).updateMachineBalanceBy(Coin.QUARTER);


        changeProvider = new RecursionChangeProviderStrategy();

        List<Coin> coins = changeProvider.provideChangeFor(machineBalance, new BigDecimal(0.60).setScale(2, BigDecimal.ROUND_HALF_EVEN));

        assertThat("Coins are null", coins, is(notNullValue()));
        assertThat("Change size", coins.size(), is(3));
        assertThat("Correct Change", coins, hasItems(Coin.QUARTER, Coin.DIME));
    }

    @Test(expected = ChangeDenominationNotAvailable.class)
    public void shouldThrowChangeNotAvailableExceptionIfChangeIsNotAvailable() throws Exception {
        machineBalance = new MachineBalance().updateMachineBalanceBy(Coin.DOLLER);

        changeProvider = new RecursionChangeProviderStrategy();

        List<Coin> coins = changeProvider.provideChangeFor(machineBalance, new BigDecimal(0.60).setScale(2, BigDecimal.ROUND_HALF_EVEN));

        assertThat("Coins are null", coins, is(notNullValue()));
        assertThat("Change size", coins.size(), is(0));

    }
}