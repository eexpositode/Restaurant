package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.CustomerDataSource;
import com.eexposito.restaurant.presenter.callbacks.BaseCallback;
import com.eexposito.restaurant.presenter.callbacks.ProgressCallback;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.lang.ref.WeakReference;

import io.realm.Realm;

public class CustomerPresenterImpl implements CustomerPresenter {

    private Realm mRealm;
    private WeakReference<ProgressCallback> mViewWeakReference;
    private CustomerDataSource mCustomerDataSource;

    public CustomerPresenterImpl(@NonNull CustomerDataSource customerDataSource) {

        mCustomerDataSource = customerDataSource;
    }

    @Override
    public void bind(final BaseCallback view) {

        mRealm = Realm.getDefaultInstance();
        mViewWeakReference = new WeakReference<>((ProgressCallback) view);

        loadDataOnBind();
    }

    private void loadDataOnBind() {

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

    @Override
    public boolean isViewBound() {

        return mViewWeakReference != null && mViewWeakReference.get() != null;
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
