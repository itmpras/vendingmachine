package com.prasanna.vending.domain;


import com.prasanna.vending.state.userbalance.UserBalanceBalanceStateUpdateHandler;
import com.prasanna.vending.state.userbalance.UserBalanceStateUpdate;
import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.exception.NotEnoughBalanceException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class UserBalanceStateHandlerTest {

    UserBalanceBalanceStateUpdateHandler userBalanceStateHandler;

    @Test
    public void userBalanceshoulddReturnEmptyListWhenCoinReturnRequestedWithZeroBalance() throws Exception {
        this.userBalanceStateHandler = new UserBalanceBalanceStateUpdateHandler(BigDecimal.ZERO);
        UserBalanceStateUpdate userBalanceStateUpdate = this.userBalanceStateHandler.returnUserCoinsAndRestBalance();
        assertThat("UserBalanceBalanceStateUpdateHandler is not null", userBalanceStateUpdate, is(notNullValue()));
        assertThat("UserBalanceBalanceStateUpdateHandler is Zero", userBalanceStateUpdate.getUserBalance(), is(equalTo(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN))));
    }


    private AddCoinEvent getCoinEventFor(Coin value, UserAction addDoller) {
        return new AddCoinEvent(addDoller, value);
    }

    @Test
    public void shouldUpdateUserBalanceWhenRequested() throws Exception {
        this.userBalanceStateHandler = new UserBalanceBalanceStateUpdateHandler(BigDecimal.ZERO);
        UserBalanceStateUpdate userBalanceStateUpdate = this.userBalanceStateHandler.addUserBalance(getCoinEventFor(Coin.DOLLER, UserAction.ADD_DOLLER));
        assertThat("UserBalanceBalanceStateUpdateHandler is not null", userBalanceStateUpdate, is(notNullValue()));
        assertThat("UserBalanceBalanceStateUpdateHandler is One", userBalanceStateUpdate.getUserBalance(), is(equalTo(BigDecimal.ONE.setScale(2, BigDecimal.ROUND_HALF_EVEN))));

    }

    @Test
    public void shouldReturnAddUserCoinsWhenRequested() throws Exception {
        userBalanceStateHandler = new UserBalanceBalanceStateUpdateHandler(BigDecimal.ZERO);
        UserBalanceStateUpdate userBalanceStateUpdate = userBalanceStateHandler.addUserBalance(getCoinEventFor(Coin.DOLLER, UserAction.ADD_DOLLER))
                .addUserBalance(getCoinEventFor(Coin.DOLLER, UserAction.ADD_DOLLER));
        UserBalanceStateUpdate result = userBalanceStateUpdate.returnUserCoinsAndRestBalance();

        assertThat("UserBalanceBalanceStateUpdateHandler is not null", result, is(notNullValue()));
        assertThat("UserBalanceBalanceStateUpdateHandler is Zero", result.getUserBalance(), is(equalTo(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN))));
    }

    @Test(expected = NotEnoughBalanceException.class)
    public void shouldThrownAnExcptionWhenUserDontHaveEnoughBalance() throws Exception {
        userBalanceStateHandler = new UserBalanceBalanceStateUpdateHandler(BigDecimal.ZERO);
        SellProductEvent sellProductEvent = new SellProductEvent(VendableProduct.A);
        userBalanceStateHandler.updateUserBalance(sellProductEvent);
    }

    @Test
    public void shouldProvideCorrectUserBalanceAfterSellingProduct() throws Exception {
        userBalanceStateHandler = new UserBalanceBalanceStateUpdateHandler(BigDecimal.ONE);
        SellProductEvent sellProductEvent = new SellProductEvent(VendableProduct.A);
        UserBalanceStateUpdate userBalanceStateUpdate = userBalanceStateHandler.updateUserBalance(sellProductEvent);

        BigDecimal result = userBalanceStateHandler.getUserBalance().subtract(VendableProduct.A.getPrice());
        assertThat("userBalanceStateUpdate is not null", userBalanceStateUpdate, is(notNullValue()));
        assertThat("userBalanceStateUpdate is having correctBalance", result.equals(userBalanceStateUpdate.getUserBalance()));

    }
}