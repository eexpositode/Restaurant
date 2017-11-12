package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.CustomerListDataSource;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;
import com.eexposito.restaurant.views.Bindable;

import java.lang.ref.WeakReference;

import io.realm.Realm;

public class CustomerPresenterImpl implements CustomerPresenter {

    private Realm mRealm;
    private WeakReference<Bindable<Customer>> mViewWeakReference;
    private CustomerListDataSource mCustomerDataSource;

    public CustomerPresenterImpl(@NonNull CustomerListDataSource customerDataSource) {

        mCustomerDataSource = customerDataSource;
    }

    @Override
    public void bind(final Bindable<Customer> view) {

        mRealm = Realm.getDefaultInstance();
        mViewWeakReference = new WeakReference<>(view);

        loadDataOnBind();
    }

    private void loadDataOnBind() {

        mViewWeakReference.get().onFetchDataStarted();

        mCustomerDataSource.getCustomersFromRealm(mRealm)
                .subscribe(customers -> mViewWeakReference.get().onFetchDataSuccess(customers),
                        error -> mViewWeakReference.get().onFetchDataError(error));

        loadData();
    }

    @Override
    public void loadData() {

        mCustomerDataSource.getCustomers(mRealm)
                .subscribeOn(RxSchedulerConfiguration.getComputationThread())
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .subscribe(customers -> {
                            mViewWeakReference.get().onFetchDataSuccess(customers);
                            mViewWeakReference.get().onFetchDataCompleted();
                        },
                        error -> {
                            mViewWeakReference.get().onFetchDataError(error);
                            mViewWeakReference.get().onFetchDataCompleted();
                        }
                );
    }

    @Override
    public void unBind() {

        mViewWeakReference = null;
    }

    @Override
    public void onDestroy() {

        mRealm.close();
    }
}
