package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.ReservationsDataService;
import com.eexposito.restaurant.presenter.contracts.ReservationsContract;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.lang.ref.WeakReference;

public class ReservationsPresenterImpl implements ReservationsContract.ReservationsPresenter {

    private WeakReference<ReservationsContract.View> mViewWeakReference;
    private ReservationsDataService mReservationsDataService;

    public ReservationsPresenterImpl(@NonNull final ReservationsDataService dataSource) {

        mReservationsDataService = dataSource;
    }

    @Override
    public void bind(final ReservationsContract.View view) {

        mViewWeakReference = new WeakReference<>(view);
    }

    @Override
    public void getTableFromID(@NonNull final String id) {

        mReservationsDataService.getTableByID(id)
                .subscribeOn(RxSchedulerConfiguration.getComputationThread())
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .subscribe(tables -> {
                            Table found = mReservationsDataService.checkValidSingleData(tables);
                            if (found != null) {
                                mViewWeakReference.get().onFetchTableByID(found);
                            }
                        },
                        error -> mViewWeakReference.get().onFetchDataError(error));
    }

    @Override
    public void getCustomerByID(@NonNull final String id) {

        mReservationsDataService.getCustomerByID(id)
                .subscribeOn(RxSchedulerConfiguration.getComputationThread())
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .subscribe(customers -> {
                            Customer found = mReservationsDataService.checkValidSingleData(customers);
                            if (found != null) {
                                mViewWeakReference.get().onFetchCustomerByID(found);
                            }
                        },
                        error -> mViewWeakReference.get().onFetchDataError(error));
    }

    @Override
    public void createReservation(@NonNull final String tableID, @NonNull final String customerID, @NonNull final String time) {

        mReservationsDataService.createReservation(tableID, customerID, time);
    }

    @Override
    public void removeReservation(@NonNull final Table table) {

        mReservationsDataService.removeReservation(table);
    }

    @Override
    public boolean isViewBound() {

        return mViewWeakReference != null && mViewWeakReference.get() != null;
    }

    @Override
    public void unBind() {

        mViewWeakReference = null;
    }

    @Override
    public void clear() {

        mReservationsDataService.closeRealm();
    }
}
