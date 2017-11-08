package com.eexposito.restaurant._support.fixtures;

import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.views.Bindable;

import java.util.List;

import okhttp3.ResponseBody;


public class TestCustomerView implements Bindable<Customer> {

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
    public void onResponseFail(final ResponseBody responseBody) {

    }

    @Override
    public void onFetchDataError(final Throwable e) {

    }
}
