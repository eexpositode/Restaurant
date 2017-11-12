package com.eexposito.restaurant.datasources;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class CustomerListDataSource {

    @NonNull
    ModelManager mModelManager;

    @NonNull
    ReservationsServiceApi mReservationsApi;

    public CustomerListDataSource(@NonNull final ModelManager modelManager,
                                  @NonNull final ReservationsServiceApi reservationApi) {

        mModelManager = modelManager;
        mReservationsApi = reservationApi;
    }

    /**
     * Get an observable with the list of customers from realm or retrofit
     *
     * @param isForced True to load data directly from the internet. False to load them
     *                 from realm too.
     * @return an Observable on a list of Customers
     */
    public Observable<List<Customer>> getCustomers(@NonNull final Realm realm,
                                                   final boolean isForced) {

        return Observable.concat(
                getCustomersFromRealm(realm, isForced),
                getCustomerListFromRetrofit(),
                getDefaultResponse())
                .filter(customers -> customers != null && !customers.isEmpty())
                .map(customers -> customers);
    }

    /**
     * Queries realm to get a list of customers
     *
     * @param isForced True to avoid querying the database. False to query it.
     * @return an observable on a list of customers if any founded or an empty observable otherwise.
     */
    Observable<List<Customer>> getCustomersFromRealm(@NonNull final Realm realm,
                                                     boolean isForced) {

        System.out.println(getClass().getName() + ": Current thread: " + Thread.currentThread().getName());
        return !isForced ?
                Observable.fromCallable(() -> mModelManager.getModels(realm, Customer.class)) :
                Observable.empty();
    }

    /**
     * Get a list of customers accessing an api over retrofit
     *
     * @return an observable on a list of customers or an empty observable if any error happened.
     */
    Observable<List<Customer>> getCustomerListFromRetrofit() {

        return mReservationsApi.getCustomers()
                .subscribeOn(RxSchedulerConfiguration.getIOThread())
                .observeOn(RxSchedulerConfiguration.getComputationThread())
                .map(customers -> {
                    mModelManager.saveOrUpdateModelList(customers);
                    return customers;
                });
        //Check internet connection
        //        return internetConnection.isInternetOnObservable()
        //                .switchMap(connectionStatus -> {
        //                    if (connectionStatus) {
        //        return mReservationsApi.getCustomers()
        //                .map(customerList -> {
        //                    mModelManager.saveOrUpdateModelList(customerList);
        //                    return customerList;
        //                });
        //                    } else {
        //                        return Observable.empty();
        //                    }
        //                });
    }

    // TODO: 6/11/17 Is this really needed?
    Observable<List<Customer>> getDefaultResponse() {

        return Observable.fromCallable(this::getDefaultCustomerList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public <M extends RealmObject> Observable<RealmResults<M>> getAllModelsAsync(@NonNull final Realm realm,
                                                                                 @NonNull Class<M> modelClass) {

        return Observable.just(mModelManager.getModelsAsync(realm, modelClass));
    }

    private List<Customer> getDefaultCustomerList() {

        Customer customer = new Customer(1, "John", "Doe");
        return Collections.singletonList(customer);
    }
}
