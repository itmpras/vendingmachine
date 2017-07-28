package com.prasanna.vending.state.machinebalance;

import com.prasanna.vending.domain.Coin;
import com.prasanna.vending.events.ServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class MachineBalanceStateHandler implements MachineBalanceState, MachineBalanceStateUpdate {

    static final Logger LOG = LoggerFactory.getLogger(MachineBalanceStateHandler.class);
    private MachineBalance machineBalance;

    public MachineBalanceStateHandler(MachineBalance machineBalance) {
        this.machineBalance = machineBalance;
    }


    public MachineBalanceStateUpdate updateMachineBalance(List<Coin> coins) {
        MachineBalance balance = machineBalance;
        for (Coin c : coins) {
            balance = balance.updateMachineBalanceBy(c);
        }

        return new MachineBalanceStateHandler(balance);
    }

    public MachineBalanceStateUpdate reduceMachineBalance(List<Coin> coins) {
        MachineBalance balance = machineBalance;
        for (Coin c : coins) {
            balance = balance.reduceMachineBalanceBy(c);
        }

        return new MachineBalanceStateHandler(balance);
    }


    public MachineBalanceStateUpdate performService(ServiceEvent serviceEvent) {
        LOG.info("Performing Machine Update Service");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        HashMap<Coin, Integer> newCount = new HashMap<Coin, Integer>();
        BigDecimal newBalance = BigDecimal.ZERO;

        Coin[] values = Coin.values();

        for (Coin coin : values) {
            Integer integer = machineBalance.getCoinsCount().get(coin);
            System.out.println("Please Enter quantity to update  for:" + coin + " Available :" + integer);

            String input = null;
            try {
                input = br.readLine();
                int serviceInput = Integer.parseInt(input);
                newBalance = newBalance.add(coin.getValue().multiply(new BigDecimal(serviceInput)));

                newCount.put(coin, serviceInput);
            } catch (IOException e) {
                LOG.error("System error: Ignoring update for " + coin);
            } catch (NumberFormatException e) {
                LOG.error("System error: Ignoring update for " + coin);
            }

        }

        return new MachineBalanceStateHandler(new MachineBalance(newCount, newBalance));
    }

    public MachineBalance getMachineBalance() {
        return machineBalance;
    }

    @Override
    public String toString() {
        return machineBalance.toString();
    }
}
