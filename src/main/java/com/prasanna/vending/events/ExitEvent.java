package com.prasanna.vending.events;

import com.prasanna.vending.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExitEvent implements VendingMachineEvent {
    static final Logger LOG = LoggerFactory.getLogger(ExitEvent.class);

    public void applyEvent(EventHandler handler) {
        LOG.info("Exit event Applied");
        handler.exit();
    }
}
