package com.prasanna.vending.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum Coin {
    NICKLE("N", new BigDecimal(0.05).setScale(2, BigDecimal.ROUND_DOWN)),
    DIME("DI", new BigDecimal(0.10).setScale(2, BigDecimal.ROUND_DOWN)),
    QUARTER("Q", new BigDecimal(0.25).setScale(2, BigDecimal.ROUND_DOWN)),
    DOLLER("D", new BigDecimal(1.00).setScale(2, BigDecimal.ROUND_DOWN));

    private final String code;
    private final BigDecimal value;

    Coin(String code, BigDecimal value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getValue() {
        return value;
    }

    private static Map<BigDecimal, Coin> map = new HashMap<BigDecimal, Coin>();

    static {
        Coin[] values = Coin.values();
        for (Coin coin : values) {
            map.put(coin.getValue(), coin);
        }
    }

    public static Coin getCoinFor(BigDecimal bigDecimal) {
        Coin coin = map.get(bigDecimal);
        if (coin == null) {
            throw new IllegalArgumentException("Invalid Coin denomination");
        }

        return coin;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "code='" + code + '\'' +
                ", value=" + value +
                '}';
    }
}
