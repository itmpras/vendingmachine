package com.prasanna.vending.translator;

import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.events.VendingMachineEvent;

/**
 * Created by pgopal on 26/07/2017.
 */
public interface UserActionTranslator {

    VendingMachineEvent translate(UserAction action);

}
