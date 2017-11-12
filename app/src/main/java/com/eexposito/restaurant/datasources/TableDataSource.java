package com.eexposito.restaurant.datasources;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.realm.models.TableReservationList;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

// TODO: 7/11/17 Do it!
public class TableDataSource {

    @NonNull
    ModelManager mModelManager;

    @NonNull
    ReservationsServiceApi mReservationsApi;

    public TableDataSource(@NonNull final ModelManager modelManager,
                           @NonNull final ReservationsServiceApi reservationApi) {

        mModelManager = modelManager;
        mReservationsApi = reservationApi;
    }

    public Observable<RealmResults<Table>> getTables(@NonNull final Realm realm) {

        return Observable.concat(getTablesFromRealm(realm),
                getTablesFromRetrofit(realm));
    }

    public Observable<RealmResults<Table>> getTablesFromRealm(@NonNull final Realm realm) {

        return Observable.just(mModelManager.getAllModels(realm, Table.class));
    }

    private Observable<RealmResults<Table>> getTablesFromRetrofit(@NonNull final Realm realm) {

        return mReservationsApi.getTableAvailability()
                .subscribeOn(RxSchedulerConfiguration.getIOThread())
                .observeOn(RxSchedulerConfiguration.getComputationThread())
                .map(responses -> {
                            saveTables(realm, responses);
                            return responses;
                        }
                )
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .map(responses -> mModelManager.getAllModels(realm, Table.class));
    }

    private void saveTables(@NonNull final Realm realm,
                            final List<Boolean> responses) {

        if (responses == null || responses.isEmpty()) {
            return;
        }
        List<Table> tables = responses.stream()
                .map(available -> new Table())
                .collect(Collectors.toList());

        mModelManager.saveModels(tables);
    }
}
