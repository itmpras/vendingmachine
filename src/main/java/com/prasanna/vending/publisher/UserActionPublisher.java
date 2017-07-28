package com.prasanna.vending.publisher;

import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.listner.UserActionPublisherListner;

/**
 * Created by pgopal on 26/07/2017.
 */
public interface UserActionPublisher {
    void broadCast(UserAction actions);

    void register(UserActionPublisherListner listner);
}
