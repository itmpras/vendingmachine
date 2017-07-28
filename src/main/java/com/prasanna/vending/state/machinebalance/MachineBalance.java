package com.prasanna.vending.state.machinebalance;

import com.prasanna.vending.domain.Coin;

import java.math.BigDecimal;
import java.util.HashMap;

import java.util.Map;

public class MachineBalance {
    private Map<Coin, Integer> coinsCount;
    private BigDecimal balance;

    public MachineBalance(Map<Coin, Integer> coinsCount, BigDecimal balance) {
        this.coinsCount = coinsCount;
        this.balance = balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public MachineBalance() {
        this.balance = BigDecimal.ZERO;
        Coin[] values = Coin.values();
        coinsCount = new HashMap<Coin, Integer>();
        for (Coin c : values) {
            coinsCount.put(c, 0);
        }

    }


    public Map<Coin, Integer> getCoinsCount() {
        return coinsCount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public MachineBalance updateMachineBalanceBy(Coin c) {

        MachineBalance machineBalance = new MachineBalance(new HashMap<Coin, Integer>(coinsCount), balance);
        Integer integer = machineBalance.coinsCount.get(c);
        if (integer != null) {
            integer = integer + 1;
        } else {
            integer = 1;
        }

        machineBalance.coinsCount.put(c, integer);
        machineBalance.balance = machineBalance.balance.add(c.getValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        return machineBalance;
    }

    public MachineBalance reduceMachineBalanceBy(Coin c) {
        MachineBalance machineBalance = new MachineBalance(new HashMap<Coin, Integer>(coinsCount), balance);


        Integer integer = machineBalance.coinsCount.get(c);
        if (integer != null) {
            integer = integer - 1;
        } else {
            integer = 0;
        }

        machineBalance.coinsCount.put(c, integer);
        machineBalance.balance = machineBalance.balance.subtract(c.getValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN);


        return machineBalance;

    }

    @Override
    public String toString() {
        return "MachineBalance{" +
                "coinsCount=" + coinsCount +
                ", balance=" + balance +
                '}';
    }
}
