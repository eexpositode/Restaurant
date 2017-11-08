package com.eexposito.restaurant.datasources;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;

import java.util.List;

import io.reactivex.Observable;

// TODO: 7/11/17 Do it!
public class TablesDataSource {

    @NonNull
    ModelManager mModelManager;

    @NonNull
    ReservationsServiceApi mReservationsApi;

    public TablesDataSource(@NonNull final ModelManager modelManager,
                            @NonNull final ReservationsServiceApi reservationApi) {

        mModelManager = modelManager;
        mReservationsApi = reservationApi;
    }

    public Observable<List<Table>> getTables(final boolean isForced) {

        return Observable.empty();
    }
}
