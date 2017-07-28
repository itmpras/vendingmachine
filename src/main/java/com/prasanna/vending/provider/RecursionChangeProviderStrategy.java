package com.prasanna.vending.provider;

import com.prasanna.vending.domain.Coin;
import com.prasanna.vending.exception.ChangeDenominationNotAvailable;
import com.prasanna.vending.exception.InSufficientBalanceAvailableForChange;
import com.prasanna.vending.state.machinebalance.MachineBalance;

import java.math.BigDecimal;
import java.util.*;

public class RecursionChangeProviderStrategy implements ChangeProvider {

    private MachineBalance machineBalance;
    private BigDecimal[] denominationInDescOrder;
    private Map<Coin, Integer> coinIntegerMap;

    public RecursionChangeProviderStrategy() {
    }

    private void prepareForRecursion(MachineBalance machineBalance) {
        this.machineBalance = machineBalance;
        coinIntegerMap = new HashMap<Coin, Integer>(machineBalance.getCoinsCount());
        Coin[] values = Coin.values();
        denominationInDescOrder = new BigDecimal[values.length];
        int count = 0;

        for (Coin coin : values) {
            denominationInDescOrder[count] = coin.getValue();
            count++;
        }

        Arrays.sort(denominationInDescOrder, Collections.reverseOrder());
    }

    public List<Coin> provideChangeFor(MachineBalance machineBalance, BigDecimal change) {
        prepareForRecursion(machineBalance);
        BigDecimal machineBalanceBalance = machineBalance.getBalance();
        if (machineBalanceBalance.subtract(change).doubleValue() < 0) {
            throw new InSufficientBalanceAvailableForChange("Insufficient Balance Available.");
        }


        // Tail Recursion
        ChangeHolder changeHolder = new ChangeHolder(new ArrayList<Coin>(), change);
        provideChangeForRec(0, changeHolder);

        if (changeHolder.balance.compareTo(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN)) > 0) {

            throw new ChangeDenominationNotAvailable("Change denomination not available for " + changeHolder.balance);
        }
        return changeHolder.coins;
    }

    private ChangeHolder provideChangeForRec(int denominationIndex, ChangeHolder changeHolder) {

        // Check for Denomination Index
        if (denominationIndex == denominationInDescOrder.length) return changeHolder;

        // Check for Balance
        if (changeHolder.balance.compareTo(BigDecimal.ZERO) <= 0) return changeHolder;


        BigDecimal denomination = denominationInDescOrder[denominationIndex];

        if (denomination.compareTo(changeHolder.balance) == 1) {
            // Choose lower donomination
            provideChangeForRec(denominationIndex + 1, changeHolder);
        } else {
            // Check for Availability in current balance
            Coin coin = Coin.getCoinFor(denomination);
            Integer integer = coinIntegerMap.get(coin);
            if (integer == 0) {
                // If Nothing is available move to next denomination
                provideChangeForRec(denominationIndex + 1, changeHolder);
            } else {
                // If available reduce the count and balance
                integer--;
                coinIntegerMap.put(coin, integer);
                changeHolder.coins.add(coin);

                if (integer == 0) {
                    // Optimisation , if count is Zero move to next denomination
                    changeHolder.balance = changeHolder.balance.subtract(denomination);
                    provideChangeForRec(denominationIndex + 1, changeHolder);
                } else {
                    // Take the same changeHolder again.
                    changeHolder.balance = changeHolder.balance.subtract(denomination);
                    provideChangeForRec(denominationIndex, changeHolder);
                }

            }
        }


        return changeHolder;
    }


    private class ChangeHolder {

        public List<Coin> coins;
        public BigDecimal balance;

        public ChangeHolder(List<Coin> coins, BigDecimal bigDecimal) {
            this.coins = coins;
            this.balance = bigDecimal;
        }

    }
}
