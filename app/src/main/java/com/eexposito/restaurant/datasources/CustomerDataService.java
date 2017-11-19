package com.eexposito.restaurant.datasources;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.MainApplication;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.RealmFactory;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class CustomerDataService implements RealmDataService {

    @Inject
    RealmFactory mRealmFactory;

    @NonNull
    ModelManager mModelManager;

    @NonNull
    ReservationsServiceApi mReservationsApi;

    public CustomerDataService(@NonNull MainApplication application,
                               @NonNull final ModelManager modelManager,
                               @NonNull final ReservationsServiceApi reservationApi) {

        application.getApplicationComponent().inject(this);
        mModelManager = modelManager;
        mReservationsApi = reservationApi;
    }

    public Observable<RealmResults<Customer>> getCustomers() {

        return Observable.concat(getCustomersFromRealm(),
                getCustomersFromRetrofit());
    }

    public Observable<RealmResults<Customer>> getCustomersFromRealm() {

        return Observable.just(mModelManager.getAllModels(mRealmFactory.getRealm(), Customer.class));
    }

    private Observable<RealmResults<Customer>> getCustomersFromRetrofit() {

        return mReservationsApi.getCustomers()
                .subscribeOn(RxSchedulerConfiguration.getIOThread())
                .observeOn(RxSchedulerConfiguration.getComputationThread())
                .map(customers -> {
                            saveCustomers(customers);
                            return customers;
                        }
                )
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .map(customers -> mModelManager.getAllModels(mRealmFactory.getRealm(), Customer.class));
    }

    private void saveCustomers(@NonNull final List<Customer> newCustomers) {

        if (newCustomers.isEmpty()) {
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        List<Customer> allCustomers = realm.copyFromRealm(mModelManager.getAllModels(realm,
                Customer.class));
        realm.close();

        if (allCustomers.isEmpty()) {
            mModelManager.saveModels(newCustomers);
        } else {
            List<Customer> modelsToAdd = newCustomers.stream()
                    .filter(newCustomer ->
                            !checkPredicate(allCustomers, customer ->
                                    newCustomer.getOrder() == customer.getOrder()))
                    .collect(Collectors.toList());
            mModelManager.saveModels(modelsToAdd);
        }
    }

    private <M extends RealmObject> boolean checkPredicate(@NonNull final List<M> models,
                                                           @NonNull final Predicate<M> predicate) {

        return models.stream().anyMatch(predicate);
    }

    @Override
    public void closeRealm() {

        mRealmFactory.closeRealm();
    }
}
