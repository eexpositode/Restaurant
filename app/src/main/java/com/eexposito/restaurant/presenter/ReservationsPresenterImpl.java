package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eexposito.restaurant.datasources.ReservationsDataSource;
import com.eexposito.restaurant.presenter.callbacks.BaseCallback;
import com.eexposito.restaurant.presenter.callbacks.ReservationsCallback;
import com.eexposito.restaurant.realm.exceptions.BusinessError;
import com.eexposito.restaurant.realm.exceptions.BusinessException;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.lang.ref.WeakReference;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ReservationsPresenterImpl implements ReservationsPresenter {

    private Realm mRealm;
    private WeakReference<ReservationsCallback> mViewWeakReference;
    private ReservationsDataSource mDataSource;

    public ReservationsPresenterImpl(@NonNull final ReservationsDataSource dataSource) {

        mDataSource = dataSource;
    }

    @Override
    public void bind(final BaseCallback view) {

        mRealm = Realm.getDefaultInstance();
        mViewWeakReference = new WeakReference<>((ReservationsCallback) view);
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

    @Override
    public boolean isViewBound() {

        return mViewWeakReference != null && mViewWeakReference.get() != null;
    }

    @Override
    public void unBind() {

        mViewWeakReference = null;
    }

    @Override
    public void onDestroy() {

        mRealm.close();
    }
}
