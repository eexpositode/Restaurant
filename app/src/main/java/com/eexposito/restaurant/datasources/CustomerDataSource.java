package com.eexposito.restaurant.datasources;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

public class CustomerDataSource {

    @NonNull
    ModelManager mModelManager;

    @NonNull
    ReservationsServiceApi mReservationsApi;

    public CustomerDataSource(@NonNull final ModelManager modelManager,
                              @NonNull final ReservationsServiceApi reservationApi) {

        mModelManager = modelManager;
        mReservationsApi = reservationApi;
    }

    public Observable<RealmResults<Customer>> getCustomers(@NonNull final Realm realm) {

        return Observable.concat(getCustomersFromRealm(realm),
                getCustomersFromRetrofit(realm));
    }

    public Observable<RealmResults<Customer>> getCustomersFromRealm(@NonNull final Realm realm) {

        return Observable.just(mModelManager.getAllModels(realm, Customer.class));
    }

    private Observable<RealmResults<Customer>> getCustomersFromRetrofit(@NonNull final Realm realm) {

        return mReservationsApi.getCustomers()
                .subscribeOn(RxSchedulerConfiguration.getIOThread())
                .observeOn(RxSchedulerConfiguration.getComputationThread())
                .map(customers -> {
                            saveCustomers(customers);
                            return customers;
                        }
                )
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .map(customers -> mModelManager.getAllModels(realm, Customer.class));
    }

    private void saveCustomers(@NonNull final List<Customer> newCustomers) {

        if (newCustomers.isEmpty()) {
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        List<Customer> allCustomers = realm.copyFromRealm(mModelManager.getAllModels(realm, Customer.class));
        realm.close();

        if (allCustomers.isEmpty()) {
            mModelManager.saveModels(newCustomers);
        } else {
            List<Customer> modelsToAdd = newCustomers.stream()
                    .filter(newCustomer ->
                            !mModelManager.checkPredicate(allCustomers, customer ->
                                    newCustomer.getOrder() == customer.getOrder()))
                    .collect(Collectors.toList());
            mModelManager.saveModels(modelsToAdd);
        }
    }
}
