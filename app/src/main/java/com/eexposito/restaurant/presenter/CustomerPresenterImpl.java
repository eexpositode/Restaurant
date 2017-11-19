package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.CustomerDataService;
import com.eexposito.restaurant.presenter.contracts.CustomerListContract;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.lang.ref.WeakReference;

public class CustomerPresenterImpl implements CustomerListContract.CustomerPresenter {

    WeakReference<CustomerListContract.View> mViewWeakReference;
    private CustomerDataService mCustomerDataService;

    public CustomerPresenterImpl(@NonNull CustomerDataService customerDataSource) {

        mCustomerDataService = customerDataSource;
    }

    @Override
    public void bind(final CustomerListContract.View view) {

        mViewWeakReference = new WeakReference<>(view);
        loadDataOnBind();
    }

    @Override
    public void loadDataOnBind() {

        mViewWeakReference.get().onFetchDataStarted();

        mCustomerDataService.getCustomersFromRealm()
                .subscribe(customers -> mViewWeakReference.get().onFetchDataSuccess(customers),
                        error -> mViewWeakReference.get().onFetchDataError(error));

        loadData();
    }

    private void loadData() {

        mCustomerDataService.getCustomers()
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
    public boolean isViewBound() {

        return mViewWeakReference != null && mViewWeakReference.get() != null;
    }

    @Override
    public void unBind() {

        mViewWeakReference = null;
    }

    @Override
    public void clear() {

        mCustomerDataService.closeRealm();
    }
}
