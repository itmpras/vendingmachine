package com.prasanna.vending.events;

import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelpEvent implements VendingMachineEvent {
    static final Logger LOG = LoggerFactory.getLogger(HelpEvent.class);

    public void applyEvent(EventHandler handler) {

        LOG.info("Possible Actions :");
        UserAction[] values = UserAction.values();
        for (UserAction action : values) {
            LOG.info(action.getCode() + " -- " + action.name());
        }
    }
}
