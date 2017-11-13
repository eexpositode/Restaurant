package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.models.Table;

public interface ReservationsPresenter extends Presenter {

    void getTableFromID(@NonNull final String id);

    void getCustomerByID(@NonNull final String id);

    void createReservation(@NonNull String tableID, @NonNull String customerID, @NonNull String time);

    void removeReservation(@NonNull Table table);
}
