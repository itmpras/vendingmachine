package com.prasanna.vending;


import com.prasanna.vending.domain.Coin;
import com.prasanna.vending.domain.VendableProduct;
import com.prasanna.vending.provider.ChangeProvider;
import com.prasanna.vending.provider.RecursionChangeProviderStrategy;
import com.prasanna.vending.state.VendingMachineStateUpdateHandler;
import com.prasanna.vending.state.inventory.InventoryStateHandler;
import com.prasanna.vending.state.machinebalance.MachineBalance;
import com.prasanna.vending.state.machinebalance.MachineBalanceStateHandler;
import com.prasanna.vending.state.userbalance.UserBalanceBalanceStateUpdateHandler;
import com.prasanna.vending.listner.ActionListner;
import com.prasanna.vending.listner.UserInputListner;
import com.prasanna.vending.parser.CommaBasedStringTokenParser;
import com.prasanna.vending.parser.InputParser;
import com.prasanna.vending.publisher.ListBasedUserActionPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;


public class MachineStartUp {
    static final Logger LOG = LoggerFactory.getLogger(MachineStartUp.class);

    public static void main(String[] args) throws IOException {
        LOG.info("Application Startup");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        InputParser parser = new CommaBasedStringTokenParser();
        UserBalanceBalanceStateUpdateHandler userBalanceStateHandler = new UserBalanceBalanceStateUpdateHandler(BigDecimal.ZERO);
        InventoryStateHandler mapBasedVendingMachineInventory = new InventoryStateHandler(getMap());
        MachineBalanceStateHandler machineBalanceStateHandler = new MachineBalanceStateHandler(getMachineBalanceWithSomeChange());
        ChangeProvider changeProvider = new RecursionChangeProviderStrategy();
        ListBasedUserActionPublisher publisher = new ListBasedUserActionPublisher();

        VendingMachineStateUpdateHandler state = new VendingMachineStateUpdateHandler(userBalanceStateHandler,
                mapBasedVendingMachineInventory,
                machineBalanceStateHandler,
                changeProvider);

        VendingMachine vendingMachine = new ChangeProvidingVendingMachine(state);
        VendingMachineCollaborator collaborator = new VendingMachineCollaborator(vendingMachine);
        publisher.register(collaborator);

        collaborator.start();

        ActionListner listner = new UserInputListner(parser, publisher);

        while (true) {
            System.out.println("\n Please Enter User Input :");
            String input = br.readLine();
            try {
                listner.processInput(input.toUpperCase());
            } catch (IllegalArgumentException exception) {
                LOG.info("\n Possible Input's are :");
                listner.processInput("HELP");
            }

        }
    }


    private static MachineBalance getMachineBalanceWithSomeChange() {
        return new MachineBalance().updateMachineBalanceBy(Coin.QUARTER).updateMachineBalanceBy(Coin.QUARTER).updateMachineBalanceBy(Coin.QUARTER).updateMachineBalanceBy(Coin.DIME)
                .updateMachineBalanceBy(Coin.DIME).updateMachineBalanceBy(Coin.NICKLE).updateMachineBalanceBy(Coin.NICKLE).updateMachineBalanceBy(Coin.NICKLE);
    }

    private static HashMap<VendableProduct, Integer> getMap() {
        HashMap<VendableProduct, Integer> vendableProductIntegerHashMap = new HashMap<VendableProduct, Integer>();
        vendableProductIntegerHashMap.put(VendableProduct.A, 1);
        vendableProductIntegerHashMap.put(VendableProduct.B, 1);
        vendableProductIntegerHashMap.put(VendableProduct.C, 1);

        return vendableProductIntegerHashMap;
    }


}
