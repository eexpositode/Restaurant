package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.ReservationsDataSource;
import com.eexposito.restaurant.presenter.callbacks.ReservationViewCallback;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

public class ReservationsPresenter extends BasePresenter<ReservationViewCallback> {

    private ReservationsDataSource mDataSource;

    public ReservationsPresenter(@NonNull final ReservationsDataSource dataSource) {

        mDataSource = dataSource;
    }

    @Override
    public void loadDataOnBind() {
        //Do nothing
    }

    public void getTableFromID(@NonNull final String id) {

        mDataSource.getTableByID(mRealm, id)
                .subscribeOn(RxSchedulerConfiguration.getComputationThread())
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .subscribe(tables -> {
                            Table found = mDataSource.checkValidSingleData(tables);
                            if (found != null) {
                                mViewWeakReference.get().onFetchTableByID(found);
                            }
                        },
                        error -> mViewWeakReference.get().onFetchDataError(error));
    }

    public void getCustomerByID(@NonNull final String id) {

        mDataSource.getCustomerByID(mRealm, id)
                .subscribeOn(RxSchedulerConfiguration.getComputationThread())
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .subscribe(customers -> {
                            Customer found = mDataSource.checkValidSingleData(customers);
                            if (found != null) {
                                mViewWeakReference.get().onFetchCustomerByID(found);
                            }
                        },
                        error -> mViewWeakReference.get().onFetchDataError(error));
    }

    public void createReservation(@NonNull final String tableID, @NonNull final String customerID, @NonNull final String time) {

        mDataSource.createReservation(tableID, customerID, time);
    }

    public void removeReservation(@NonNull final Table table) {

        mDataSource.removeReservation(table);
    }
}
