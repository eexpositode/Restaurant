package com.eexposito.restaurant.datasources;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.models.Reservation;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;

import java.util.List;

import io.reactivex.Observable;

// TODO: 7/11/17 Do it!
public class ReservationsDataSource {

    @NonNull
    ModelManager mModelManager;

    @NonNull
    ReservationsServiceApi mReservationsApi;

    public ReservationsDataSource(@NonNull final ModelManager modelManager,
                                  @NonNull final ReservationsServiceApi reservationApi) {

        mModelManager = modelManager;
        mReservationsApi = reservationApi;
    }

    public Observable<List<Reservation>> getReservations(final boolean isForced) {

        return Observable.empty();
    }
}
