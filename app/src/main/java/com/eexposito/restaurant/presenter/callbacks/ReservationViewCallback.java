package com.eexposito.restaurant.presenter.callbacks;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Table;

public interface ReservationViewCallback extends BaseCallback {

    void onFetchTableByID(@NonNull final Table table);

    void onFetchCustomerByID(@NonNull final Customer customer);
}
