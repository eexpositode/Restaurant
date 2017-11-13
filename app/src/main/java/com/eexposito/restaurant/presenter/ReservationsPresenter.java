package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

public interface ReservationsPresenter extends Presenter {

    void getTableFromID(@NonNull final String id);

    void getCustomerByID(@NonNull final String id);
}
