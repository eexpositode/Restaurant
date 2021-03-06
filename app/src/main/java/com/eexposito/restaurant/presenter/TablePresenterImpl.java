package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.TableDataService;
import com.eexposito.restaurant.presenter.contracts.TableListContract;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.lang.ref.WeakReference;

public class TablePresenterImpl implements TableListContract.TablePresenter {

    private WeakReference<TableListContract.View> mViewWeakReference;
    private TableDataService mTableDataService;

    public TablePresenterImpl(@NonNull TableDataService tableDataService) {

        mTableDataService = tableDataService;
    }

    @Override
    public void bind(final TableListContract.View view) {

        mViewWeakReference = new WeakReference<>(view);
        loadDataOnBind();
    }

    @Override
    public void loadDataOnBind() {

        mViewWeakReference.get().onFetchDataStarted();

        mTableDataService.getTablesFromRealm()
                .subscribe(tables -> mViewWeakReference.get().onFetchDataSuccess(tables),
                        error -> mViewWeakReference.get().onFetchDataError(error));

        loadData();
    }

    private void loadData() {

        mTableDataService.getTables()
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
    public boolean isViewBound() {

        return mViewWeakReference != null && mViewWeakReference.get() != null;
    }

    @Override
    public void unBind() {

        mViewWeakReference = null;
    }

    @Override
    public void clear() {

        mTableDataService.closeRealm();
    }
}
