package com.eexposito.restaurant.presenter.contracts;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Table;

public interface ReservationsContract {

    interface View extends BaseView {

        void onFetchTableByID(@NonNull final Table table);

        void onFetchCustomerByID(@NonNull final Customer customer);

        void onFetchDataError(final Throwable e);
    }

    interface ReservationsPresenter extends Presenter<View> {

        void getTableFromID(@NonNull final String id);

        void getCustomerByID(@NonNull final String id);

        void createReservation(@NonNull final String tableID, @NonNull final String customerID, @NonNull final String time);

        void removeReservation(@NonNull final Table table);
    }
}
