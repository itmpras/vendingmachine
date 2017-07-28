package com.prasanna.vending.state.userbalance;

import com.prasanna.vending.domain.VendableProduct;
import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.exception.NotEnoughBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UserBalanceBalanceStateUpdateHandler implements UserBalanceStateUpdate {
    static final Logger LOG = LoggerFactory.getLogger(UserBalanceBalanceStateUpdateHandler.class);

    final List<AddCoinEvent> userInputCoins;
    final BigDecimal userBalance;

    public UserBalanceBalanceStateUpdateHandler(BigDecimal userBalance) {
        this.userBalance = userBalance;
        this.userBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        userInputCoins = new LinkedList<AddCoinEvent>();
    }

    private UserBalanceBalanceStateUpdateHandler(BigDecimal userBalance, List<AddCoinEvent> userInputCoins) {
        this.userBalance = userBalance;
        this.userBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.userInputCoins = userInputCoins;

    }

    public UserBalanceStateUpdate addUserBalance(AddCoinEvent coinEvent) {
        userInputCoins.add(coinEvent);
        BigDecimal userBalance = this.userBalance.add(coinEvent.getValue()).setScale(2);
        if (userBalance.doubleValue() < 0) {
            throw new IllegalStateException("UserBalanceBalanceStateUpdateHandler Cannot be negative");
        }
        return new UserBalanceBalanceStateUpdateHandler(userBalance, new ArrayList<AddCoinEvent>(userInputCoins));
    }

    public UserBalanceStateUpdate updateUserBalance(SellProductEvent sellProductEvent) throws NotEnoughBalanceException {
        VendableProduct vendableProduct = sellProductEvent.getVendableProduct();
        BigDecimal productPrice = vendableProduct.getPrice();
        BigDecimal remainingBalance = userBalance.subtract(productPrice).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        if (remainingBalance.doubleValue() < 0) {
            throw new NotEnoughBalanceException("User Don't have enough Balance to Buy :" + vendableProduct.toString());
        }

         return new UserBalanceBalanceStateUpdateHandler(remainingBalance, new ArrayList<AddCoinEvent>(userInputCoins));
    }

    public UserBalanceStateUpdate resetUserBalance() {
        return new UserBalanceBalanceStateUpdateHandler(BigDecimal.ZERO);
    }

    public UserBalanceStateUpdate setUserBalance(BigDecimal bigDecimal) {
        return new UserBalanceBalanceStateUpdateHandler(bigDecimal);
    }

    public UserBalanceStateUpdate returnUserCoinsAndRestBalance() {

        if (userInputCoins.size() > 0) {
            LOG.info("Returning User Coins :");
            for (AddCoinEvent userCoin : userInputCoins) {
                LOG.info(userCoin.getUserAction().getCode());
            }

            return new UserBalanceBalanceStateUpdateHandler(BigDecimal.ZERO);
        } else {
            LOG.info("No user Balance to return");
            return this;
        }
    }

    public List<AddCoinEvent> getUserCoins() {
        return Collections.unmodifiableList(userInputCoins);
    }

    public BigDecimal getUserBalance() {
        return new BigDecimal(userBalance.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public String toString() {
        return "UserBalance=" + userBalance;
    }
}