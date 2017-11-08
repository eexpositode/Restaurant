package com.eexposito.restaurant.datasources;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

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
    public Observable<List<Customer>> getCustomers(final boolean isForced) {

        return Observable.concat(
                getCustomersFromRealm(isForced),
                getCustomerListFromRetrofit(),
                getDefaultResponse())
                .filter(customers -> customers != null && !customers.isEmpty())
                .take(1);
    }

    /**
     * Queries realm to get a list of customers
     *
     * @param isForced True to avoid querying the database. False to query it.
     * @return an observable on a list of customers if any founded or an empty observable otherwise.
     */
    Observable<List<Customer>> getCustomersFromRealm(boolean isForced) {

        return !isForced ?
                Observable.fromCallable(() -> mModelManager.getModels(Customer.class)) :
                Observable.empty();
    }

    /**
     * Get a list of customers accessing an api over retrofit
     *
     * @return an observable on a list of customers or an empty observable if any error happened.
     */
    Observable<List<Customer>> getCustomerListFromRetrofit() {
        //Check internet connection
        //        return internetConnection.isInternetOnObservable()
        //                .switchMap(connectionStatus -> {
        //                    if (connectionStatus) {
        return mReservationsApi.getCustomers()
                .map(customerList -> {
                    mModelManager.saveOrUpdateModelList(customerList);
                    return customerList;
                });
        //                    } else {
        //                        return Observable.empty();
        //                    }
        //                });
    }

    // TODO: 6/11/17 Is this really needed?
    Observable<List<Customer>> getDefaultResponse() {

        return Observable.fromCallable(this::getDefaultCustomerList);
    }

    private List<Customer> getDefaultCustomerList() {

        Customer customer = new Customer(1, "John", "Doe");
        return Collections.singletonList(customer);
    }
}
