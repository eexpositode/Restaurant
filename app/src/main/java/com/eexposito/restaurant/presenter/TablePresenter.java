package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.TableDataSource;
import com.eexposito.restaurant.presenter.callbacks.ProgressViewCallback;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

public class TablePresenter extends BasePresenter<ProgressViewCallback> {

    private TableDataSource mTableDataSource;

    public TablePresenter(@NonNull TableDataSource tableDataSource) {

        mTableDataSource = tableDataSource;
    }

    @Override
    public void loadDataOnBind() {

        mViewWeakReference.get().onFetchDataStarted();

        mTableDataSource.getTablesFromRealm(mRealm)
                .subscribe(tables -> mViewWeakReference.get().onFetchDataSuccess(tables),
                        error -> mViewWeakReference.get().onFetchDataError(error));

        loadData();
    }

    private void loadData() {

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
}
