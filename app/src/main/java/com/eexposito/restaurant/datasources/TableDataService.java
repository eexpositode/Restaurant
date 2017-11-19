package com.eexposito.restaurant.datasources;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.MainApplication;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.RealmFactory;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

public class TableDataService implements RealmDataService {

    @Inject
    RealmFactory mRealmFactory;

    @NonNull
    ModelManager mModelManager;

    @NonNull
    ReservationsServiceApi mReservationsApi;

    public TableDataService(@NonNull MainApplication application,
                            @NonNull final ModelManager modelManager,
                            @NonNull final ReservationsServiceApi reservationApi) {

        application.getApplicationComponent().inject(this);
        mModelManager = modelManager;
        mReservationsApi = reservationApi;
    }

    public Observable<RealmResults<Table>> getTables() {

        return Observable.concat(getTablesFromRealm(),
                getTablesFromRetrofit());
    }

    public Observable<RealmResults<Table>> getTablesFromRealm() {

        return Observable.just(mModelManager.getAllModels(mRealmFactory.getRealm(), Table.class));
    }

    // TODO: 15/11/17 Make this observable happen in the io thread
    private Observable<RealmResults<Table>> getTablesFromRetrofit() {

        // TODO: 13/11/17 Put some internet checking stuff
        return mReservationsApi.getTableAvailability()
//                .subscribeOn(RxSchedulerConfiguration.getIOThread())
//                .observeOn(RxSchedulerConfiguration.getComputationThread())
                .map(responses -> {
                            saveTables(responses);
                            return responses;
                        }
                )
                // Realm instance lives in the main thread
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .map(responses -> mModelManager.getAllModels(mRealmFactory.getRealm(), Table.class));
    }

    private void saveTables(final List<Boolean> responses) {

        if (responses == null || responses.isEmpty()) {
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        List<Table> currentTables = realm.copyFromRealm(mModelManager.getAllModels(realm, Table
                .class));
        realm.close();

        if (!currentTables.isEmpty()) {
            return;
        }

        final AtomicInteger autoIncrement = new AtomicInteger(0);
        List<Table> tables = responses.stream()
                .map(available -> new Table(autoIncrement.getAndIncrement()))
                .collect(Collectors.toList());

        mModelManager.saveModels(tables);
    }

    @Override
    public void closeRealm() {

        mRealmFactory.closeRealm();
    }
}
