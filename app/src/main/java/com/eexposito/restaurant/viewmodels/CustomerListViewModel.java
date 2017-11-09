package com.eexposito.restaurant.viewmodels;

import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.views.Bindable;

import java.util.List;


public class CustomerListViewModel implements Bindable<Customer> {

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
