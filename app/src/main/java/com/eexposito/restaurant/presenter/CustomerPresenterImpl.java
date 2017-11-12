package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.CustomerListDataSource;
import com.eexposito.restaurant.realm.RealmService;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;
import com.eexposito.restaurant.views.Bindable;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class CustomerPresenterImpl implements CustomerPresenter {

    @NonNull
    private CustomerListDataSource mCustomerDataSource;

    CompositeDisposable mCompositeDisposable;

    WeakReference<Bindable<Customer>> mViewWeakReference;

    RealmService mRealmService;

    public CustomerPresenterImpl(@NonNull RealmService service,
                                 @NonNull CustomerListDataSource customerDataSource) {

        mRealmService = service;
        mCustomerDataSource = customerDataSource;
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

        if (mCompositeDisposable.size() == 0) {
            mCompositeDisposable.add(

                    mCustomerDataSource.getAllModelsAsync(mRealmService.getRealm(), Customer.class)
                            //                            .filter(RealmResults::isLoaded)
                            //                            .filter(RealmResults::isValid)
                            //                            .filter(realmResults -> realmResults.size() > 0)
                            .subscribe(customers -> mViewWeakReference.get().onFetchDataSuccess(customers),
                                    error -> mViewWeakReference.get().onFetchDataError(error))

            );
        }

        loadData();
    }

    @Override
    public void loadData() {

        System.out.println(getClass().getName() + ": Current thread: " + Thread.currentThread().getName());
        System.out.println("Realm instance: " + mRealmService.getRealm());

        mCompositeDisposable.add(

                mCustomerDataSource.getCustomers(mRealmService.getRealm(), false)
                        .subscribeOn(RxSchedulerConfiguration.getComputationThread())
                        .observeOn(RxSchedulerConfiguration.getMainThread())
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
