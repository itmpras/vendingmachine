package com.prasanna.vending.listner;

import com.prasanna.vending.domain.UserAction;

public interface UserActionPublisherListner {

     void process(UserAction actions);
}
