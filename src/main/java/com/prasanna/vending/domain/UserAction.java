package com.prasanna.vending.domain;

import com.prasanna.vending.events.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum UserAction {
    ADD_NICKEL("N") {
        public VendingMachineEvent getEventForAction() {
            return nickelAddCoinEvent;
        }
    },
    ADD_DIME("DI") {
        public VendingMachineEvent getEventForAction() {
            return dimeAddCoinEvent;
        }
    },
    ADD_QUARTER("Q") {
        public VendingMachineEvent getEventForAction() {
            return quaterAddCoinEvent;
        }
    },

    ADD_DOLLER("D") {
        public VendingMachineEvent getEventForAction() {
            return dollerAddCointEvent;
        }
    },

    COIN_RETURN("COIN_RETURN") {
        public VendingMachineEvent getEventForAction() {
            return userCoinReturnEvent;
        }
    },

    GET_A("GET_A") {
        public VendingMachineEvent getEventForAction() {
            return sellProductAEvent;
        }
    },


    GET_B("GET_B") {
        public VendingMachineEvent getEventForAction() {
            return sellProductBEvent;
        }
    },

    GET_C("GET_C") {
        public VendingMachineEvent getEventForAction() {
            return sellProductCEvent;
        }
    },


    SERVICE("SERVICE") {
        public VendingMachineEvent getEventForAction() {
            return serviceEvent;
        }
    },

    EXIT("EXIT") {
        public VendingMachineEvent getEventForAction() {
            return exitEvent;
        }
    },

    HELP("HELP") {
        public VendingMachineEvent getEventForAction() {
            return helpEvent;
        }
    };

    UserAction(String code) {
        this.code = code;
    }


    private String code;
    private static final ExitEvent exitEvent = new ExitEvent();
    private static final HelpEvent helpEvent = new HelpEvent();
    private static Map<String, UserAction> map = new HashMap<String, UserAction>();
    private static AddCoinEvent nickelAddCoinEvent = new AddCoinEvent(UserAction.ADD_NICKEL, Coin.NICKLE);
    private static AddCoinEvent dimeAddCoinEvent = new AddCoinEvent(UserAction.ADD_DIME, Coin.DIME);
    private static AddCoinEvent quaterAddCoinEvent = new AddCoinEvent(UserAction.ADD_QUARTER, Coin.QUARTER);
    private static AddCoinEvent dollerAddCointEvent = new AddCoinEvent(UserAction.ADD_DOLLER, Coin.DOLLER);
    private static UserCoinReturnEvent userCoinReturnEvent = new UserCoinReturnEvent();
    private static SellProductEvent sellProductAEvent = new SellProductEvent(VendableProduct.A);
    private static SellProductEvent sellProductBEvent = new SellProductEvent(VendableProduct.B);
    private static SellProductEvent sellProductCEvent = new SellProductEvent(VendableProduct.C);
    private static ServiceEvent serviceEvent = new ServiceEvent();

    static {
        UserAction[] values = UserAction.values();
        for (UserAction user_action : values) {
            map.put(user_action.code, user_action);
        }

    }

    public static UserAction getUserActionFor(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Invalid code");
        }
        UserAction userAction = map.get(code);

        if (userAction == null) {
            throw new IllegalArgumentException("Invalid code");
        }

        return userAction;
    }

    public String getCode() {
        return code;
    }

    public VendingMachineEvent getEventForAction() {
        throw new UnsupportedOperationException("UnSupported Operation");
    }
}
