package com.prasanna.vending.events;

import com.prasanna.vending.domain.Coin;
import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.handler.EventHandler;

import java.math.BigDecimal;

public class AddCoinEvent implements VendingMachineEvent {
    private final UserAction userAction;
    private final BigDecimal value;
    private final Coin coin;

    public AddCoinEvent(UserAction userAction, Coin coin) {
        this.userAction = userAction;
        this.coin = coin;
        this.value = coin.getValue();
    }

    public void applyEvent(EventHandler handler) {
        handler.inputCoin(this);
    }

    public BigDecimal getValue() {
        return value;
    }

    public UserAction getUserAction() {
        return userAction;
    }

    public Coin getCoin() {
        return coin;
    }

    @Override
    public String toString() {
        return "AddCoinEvent{" +
                "userAction=" + userAction +
                ", value=" + value +
                '}';
    }
}
