package com.prasanna.vending.state.userbalance;

import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.SellProductEvent;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by pgopal on 27/07/2017.
 */
public interface UserBalanceStateUpdate extends UserBalanceState {
    UserBalanceStateUpdate addUserBalance(AddCoinEvent bigDecimal);

    UserBalanceStateUpdate setUserBalance(BigDecimal bigDecimal);

    UserBalanceStateUpdate returnUserCoinsAndRestBalance();

    UserBalanceStateUpdate updateUserBalance(SellProductEvent sellProductEvent);

    UserBalanceStateUpdate resetUserBalance();

    List<AddCoinEvent> getUserCoins();
}
