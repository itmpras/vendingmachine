package com.prasanna.vending.publisher;

import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.listner.UserActionPublisherListner;

import java.util.ArrayList;
import java.util.List;

public class ListBasedUserActionPublisher implements UserActionPublisher {

    final List<UserActionPublisherListner> listnerList;

    public ListBasedUserActionPublisher() {
        this.listnerList = new ArrayList<UserActionPublisherListner>();
    }

    public void broadCast(UserAction actions) {

        for (UserActionPublisherListner listner : listnerList) {
            listner.process(actions);
        }

    }

    public void register(UserActionPublisherListner listner) {
        if (listner == null) {
            throw new IllegalArgumentException("Invalid listner provided");
        }

        this.listnerList.add(listner);
    }
}
