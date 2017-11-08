package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.CustomerListDataSource;
import com.eexposito.restaurant.realm.RealmService;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;
import com.eexposito.restaurant.views.Bindable;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.RealmResults;

public class CustomerPresenterImpl implements CustomerPresenter {

    @NonNull
    private CustomerListDataSource mDataSource;

    @NonNull
    RxSchedulerConfiguration mSchedulerConfiguration;

    CompositeDisposable mCompositeDisposable;

    WeakReference<Bindable<Customer>> mViewWeakReference;

    RealmService mRealmService;

    public CustomerPresenterImpl(@NonNull RealmService service,
                                 @NonNull RxSchedulerConfiguration configuration,
                                 @NonNull CustomerListDataSource dataSource) {

        mRealmService = service;
        mSchedulerConfiguration = configuration;
        mDataSource = dataSource;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void bind(final Bindable<Customer> view) {

        mViewWeakReference = new WeakReference<>(view);

        loadDataOnBind();
    }

    /**
     *
     */
    private void loadDataOnBind() {

        mViewWeakReference.get().onFetchDataStarted();

        if (mCompositeDisposable == null || mCompositeDisposable.size() == 0) {
            mCompositeDisposable.add(

                    mRealmService.getAllModelsAsync(Customer.class)
                            .filter(RealmResults::isLoaded)
                            .filter(RealmResults::isValid)
                            .filter(realmResults -> realmResults.size() > 0)
                            .subscribe(mViewWeakReference.get()::onFetchDataSuccess,
                                    mViewWeakReference.get()::onFetchDataError)

            );
        }

        loadData();
    }

    @Override
    public void loadData() {

        mCompositeDisposable.add(

                mDataSource.getCustomers(false)
                        .subscribeOn(mSchedulerConfiguration.getComputationThread())
                        .observeOn(mSchedulerConfiguration.getMainThread())
                        .subscribe(customers -> {
                                    mViewWeakReference.get().onFetchDataSuccess(customers);
                                    mViewWeakReference.get().onFetchDataCompleted();
                                },
                                error -> {
                                    mViewWeakReference.get().onFetchDataError(error);
                                    mViewWeakReference.get().onFetchDataCompleted();
                                })
        );
    }

    @Override
    public void unBind() {

        if (mCompositeDisposable != null && mCompositeDisposable.size() > 0) {
            mCompositeDisposable.clear();
        }
        mViewWeakReference = null;
    }

    @Override
    public void onDestroy() {

        mRealmService.closeRealm();
    }
}
