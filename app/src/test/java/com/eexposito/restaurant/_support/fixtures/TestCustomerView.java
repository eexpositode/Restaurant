package com.eexposito.restaurant._support.fixtures;

import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.presenter.DataCallback;

import java.util.List;


public class TestCustomerView implements DataCallback<Customer> {

    @Override
    public void onFetchDataStarted() {

    }

    @Override
    public void onFetchDataCompleted() {

    }

    @Override
    public void onFetchDataSuccess(final List<Customer> modelList) {

    }

    @Override
    public void onFetchDataError(final Throwable e) {

    }
}
