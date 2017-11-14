package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.CustomerDataSource;
import com.eexposito.restaurant.presenter.callbacks.ProgressViewCallback;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

public class CustomerPresenter extends BasePresenter<ProgressViewCallback> {

    private CustomerDataSource mCustomerDataSource;

    public CustomerPresenter(@NonNull CustomerDataSource customerDataSource) {

        mCustomerDataSource = customerDataSource;
    }

    @Override
    public void loadDataOnBind() {

        mViewWeakReference.get().onFetchDataStarted();

        mCustomerDataSource.getCustomersFromRealm(mRealm)
                .subscribe(customers -> mViewWeakReference.get().onFetchDataSuccess(customers),
                        error -> mViewWeakReference.get().onFetchDataError(error));

        loadData();
    }

    private void loadData() {

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
}
