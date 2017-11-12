package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;
import com.eexposito.restaurant.views.Bindable;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class EasyCustomerPresenterImpl implements CustomerPresenter {


    private Realm mRealm;
    private WeakReference<Bindable<Customer>> mViewWeakReference;
    private ReservationsServiceApi mServiceApi;

    public EasyCustomerPresenterImpl(final ReservationsServiceApi api) {

        mServiceApi = api;
    }

    @Override
    public void bind(final Bindable<Customer> view) {

        mRealm = Realm.getDefaultInstance();
        mViewWeakReference = new WeakReference<>(view);

        loadDataOnBind();
    }

    private void loadDataOnBind() {

        mViewWeakReference.get().onFetchDataStarted();

        Observable.just(getAllModels(mRealm, Customer.class))
                .subscribe(customers -> mViewWeakReference.get().onFetchDataSuccess(customers),
                        error -> mViewWeakReference.get().onFetchDataError(error));

        loadData();
    }

    @Override
    public void loadData() {

        Observable<RealmResults<Customer>> allRealmCustomersObservable = Observable.just(getAllModels(mRealm, Customer.class));
        Observable<RealmResults<Customer>> allRetrofitCustomersObservable = mServiceApi.getCustomers()
                .subscribeOn(RxSchedulerConfiguration.getIOThread())
                .observeOn(RxSchedulerConfiguration.getComputationThread())
                .map(customers -> {
                            saveCustomers(customers);
                            return customers;
                        }
                )
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .map(customers -> getAllModels(mRealm, Customer.class));

        Observable.concat(allRealmCustomersObservable,
                allRetrofitCustomersObservable)
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private void saveCustomers(@NonNull final List<Customer> newCustomers) {

        if (newCustomers.isEmpty()) {
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        List<Customer> allCustomers = realm.copyFromRealm(getAllModels(realm, Customer.class));
        realm.close();

        if (allCustomers.isEmpty()) {
            saveModels(newCustomers);
        } else {
            List<Customer> modelsToAdd = newCustomers.stream()
                    .filter(newCustomer ->
                            !checkModelExists(allCustomers, customer ->
                                    newCustomer.getOrder() == customer.getOrder()))
                    .collect(Collectors.toList());
            saveModels(modelsToAdd);
        }
    }

    private <M extends RealmObject> boolean checkModelExists(@NonNull final List<M> models, @NonNull final Predicate<M> predicate) {

        return models.stream().anyMatch(predicate);
    }

    private <M extends RealmObject> RealmResults<M> getAllModels(@NonNull final Realm realm,
                                                                 @NonNull final Class<M> modelClass) {

        return realm.where(modelClass).findAll();
    }

    private <M extends RealmObject> void saveModels(@NonNull final List<M> models) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealm(models));
        realm.close();
    }
}
