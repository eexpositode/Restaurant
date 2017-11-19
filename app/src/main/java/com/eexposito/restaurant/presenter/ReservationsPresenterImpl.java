package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.ReservationsDataService;
import com.eexposito.restaurant.presenter.contracts.ReservationsContract;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.lang.ref.WeakReference;

import io.realm.Realm;

public class ReservationsPresenterImpl implements ReservationsContract.ReservationsPresenter {

    private Realm mRealm;
    private WeakReference<ReservationsContract.View> mViewWeakReference;
    private ReservationsDataService mDataSource;

    public ReservationsPresenterImpl(@NonNull final ReservationsDataService dataSource) {

        mDataSource = dataSource;
    }

    @Override
    public void bind(final ReservationsContract.View view) {

        mRealm = Realm.getDefaultInstance();
        mViewWeakReference = new WeakReference<>(view);
    }

    @Override
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

    @Override
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

    @Override
    public void createReservation(@NonNull final String tableID, @NonNull final String customerID, @NonNull final String time) {

        mDataSource.createReservation(tableID, customerID, time);
    }

    @Override
    public void removeReservation(@NonNull final Table table) {

        mDataSource.removeReservation(table);
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

        mRealm.close();
    }
}
