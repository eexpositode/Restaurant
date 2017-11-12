package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.TableDataSource;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.lang.ref.WeakReference;

import io.realm.Realm;

public class TablePresenterImpl implements TablePresenter {

    private Realm mRealm;
    private WeakReference<DataCallback<Table>> mViewWeakReference;
    private TableDataSource mTableDataSource;

    public TablePresenterImpl(@NonNull TableDataSource tableDataSource) {

        mTableDataSource = tableDataSource;
    }

    @Override
    public void bind(final DataCallback<Table> view) {

        mRealm = Realm.getDefaultInstance();
        mViewWeakReference = new WeakReference<>(view);

        loadDataOnBind();
    }

    private void loadDataOnBind() {

        mViewWeakReference.get().onFetchDataStarted();

        mTableDataSource.getTablesFromRealm(mRealm)
                .subscribe(tables -> mViewWeakReference.get().onFetchDataSuccess(tables),
                        error -> mViewWeakReference.get().onFetchDataError(error));

        loadData();
    }

    @Override
    public void loadData() {

        mTableDataSource.getTables(mRealm)
                .subscribeOn(RxSchedulerConfiguration.getComputationThread())
                .observeOn(RxSchedulerConfiguration.getMainThread())
                .subscribe(tables -> {
                            mViewWeakReference.get().onFetchDataSuccess(tables);
                            mViewWeakReference.get().onFetchDataCompleted();
                        },
                        error -> {
                            mViewWeakReference.get().onFetchDataError(error);
                            mViewWeakReference.get().onFetchDataCompleted();
                        }
                );
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
