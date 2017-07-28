package com.prasanna.vending.state;


import com.prasanna.vending.domain.Coin;
import com.prasanna.vending.events.ServiceEvent;
import com.prasanna.vending.exception.ChangeDenominationNotAvailable;
import com.prasanna.vending.exception.InSufficientBalanceAvailableForChange;
import com.prasanna.vending.provider.ChangeProvider;
import com.prasanna.vending.provider.RecursionChangeProviderStrategy;
import com.prasanna.vending.state.inventory.InventoryState;
import com.prasanna.vending.state.inventory.InventoryStateUpdate;
import com.prasanna.vending.state.machinebalance.MachineBalanceStateUpdate;
import com.prasanna.vending.state.userbalance.UserBalanceStateUpdate;
import com.prasanna.vending.state.userbalance.UserBalanceState;
import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.exception.NotEnoughBalanceException;
import com.prasanna.vending.exception.ProductNotAvailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


public class VendingMachineStateUpdateHandler implements VendingMachineStateUpdate {
    static final Logger LOG = LoggerFactory.getLogger(VendingMachineStateUpdateHandler.class);

    private final UserBalanceStateUpdate userState;
    private final InventoryStateUpdate inventoryState;
    private final MachineBalanceStateUpdate machineBalanceStateUpdate;
    private final ChangeProvider changeProvider;


    public UserBalanceState getUserBalanceState() {
        return userState;
    }


    public VendingMachineStateUpdateHandler(UserBalanceStateUpdate userState, InventoryStateUpdate inventoryState, MachineBalanceStateUpdate machineBalanceStateUpdate, ChangeProvider changeProvider) {
        this.userState = userState;
        this.inventoryState = inventoryState;
        this.machineBalanceStateUpdate = machineBalanceStateUpdate;
        this.changeProvider = changeProvider;

    }


    public VendingMachineStateUpdate addUserBalance(AddCoinEvent coinEvent) {
        UserBalanceStateUpdate listBasedUserBalanceState = this.userState.addUserBalance(coinEvent);
        return new VendingMachineStateUpdateHandler(listBasedUserBalanceState, inventoryState, machineBalanceStateUpdate, changeProvider);
    }

    public VendingMachineStateUpdate returnUserCoins() {
        return new VendingMachineStateUpdateHandler(userState.returnUserCoinsAndRestBalance(), inventoryState, machineBalanceStateUpdate, changeProvider);
    }


    public VendingMachineStateUpdate processSell(SellProductEvent event) {
        VendingMachineStateUpdateHandler vendingMachineStateHandler = this;
        try {

            BigDecimal changeForUser = getBalanceForUser(event);
            InventoryStateUpdate inventoryStateUpdate = updateInventory(event);
            UserBalanceStateUpdate userBalanceStateUpdate = updateUserBalance(event).resetUserBalance();

            MachineBalanceStateUpdate machineBalanceStateUpdate = updateMachineBalance(userState.getUserCoins());
            LOG.info("Product For User : " + event.getVendableProduct().name());
            List<Coin> coins = getChangeToReturnToUser(changeForUser, machineBalanceStateUpdate);
            machineBalanceStateUpdate = machineBalanceStateUpdate.reduceMachineBalance(coins);
            // Create new state
            vendingMachineStateHandler = new VendingMachineStateUpdateHandler(userBalanceStateUpdate, inventoryStateUpdate, machineBalanceStateUpdate, changeProvider);
        } catch (NotEnoughBalanceException e) {
            LOG.error("User Don't have enough balance to buy");

        } catch (ProductNotAvailableException e) {
            LOG.error("Product is not available for vending");
        } catch (ChangeDenominationNotAvailable changeDenominationNotAvailable) {
            LOG.error("Change Denomination is not available. Please select suitable product");
        } catch (InSufficientBalanceAvailableForChange inSufficientBalanceAvailableForChange) {
            LOG.error("InSufficient Balance in Vending Machine");
        }

        return vendingMachineStateHandler;
    }

    private List<Coin> getChangeToReturnToUser(BigDecimal changeForUser, MachineBalanceStateUpdate machineBalanceStateUpdate) {

        List<Coin> coins = changeProvider.provideChangeFor(machineBalanceStateUpdate.getMachineBalance(), changeForUser);

        LOG.info("Change for User :");
        for (Coin coin : coins) {
            LOG.info(coin.getCode());

        }
        return coins;
    }

    private BigDecimal getBalanceForUser(SellProductEvent event) {
        BigDecimal productPrice = event.getVendableProduct().getPrice();
        BigDecimal userBalance = userState.getUserBalance();

        return userBalance.subtract(productPrice).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private MachineBalanceStateUpdate updateMachineBalance(List<AddCoinEvent> userEvents) {


        List<Coin> userCoins = new LinkedList<Coin>();
        for (AddCoinEvent addCoinEvent : userEvents) {
            userCoins.add(addCoinEvent.getCoin());
        }

        return machineBalanceStateUpdate.updateMachineBalance(userCoins);
    }

    private UserBalanceStateUpdate updateUserBalance(SellProductEvent event) {
        LOG.debug("Updating UserBalance");
        return userState.updateUserBalance(event);
    }

    private InventoryStateUpdate updateInventory(SellProductEvent event) {
        LOG.debug("Updating Inventory");
        return inventoryState.updateInventory(event);
    }

    public InventoryState getInventoryState() {
        return inventoryState;
    }

    public VendingMachineStateUpdate performService(ServiceEvent event) {
        InventoryStateUpdate inventoryStateUpdate = inventoryState.performService(event);
        MachineBalanceStateUpdate machineBalanceStateUpdate = this.machineBalanceStateUpdate.performService(event);

        return new VendingMachineStateUpdateHandler(userState, inventoryStateUpdate, machineBalanceStateUpdate, changeProvider);
    }

    @Override
    public String toString() {
        return "VendingMachineStateUpdateHandler {" + "\n" +
                "userState=" + userState + "\n" +
                ", inventoryState=" + inventoryState + "\n" +
                ", machineBalanceStateUpdate=" + machineBalanceStateUpdate + "\n" +
                '}';
    }
}
