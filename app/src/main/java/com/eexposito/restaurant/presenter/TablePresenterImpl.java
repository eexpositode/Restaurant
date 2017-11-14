package com.eexposito.restaurant.presenter;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.datasources.TableDataSource;
import com.eexposito.restaurant.presenter.contracts.TableListContract;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.lang.ref.WeakReference;

import io.realm.Realm;

public class TablePresenterImpl implements TableListContract.TablePresenter {

    private Realm mRealm;
    private WeakReference<TableListContract.View> mViewWeakReference;
    private TableDataSource mTableDataSource;

    public TablePresenterImpl(@NonNull TableDataSource tableDataSource) {

        mTableDataSource = tableDataSource;
    }

    @Override
    public void bind(final TableListContract.View view) {

        mRealm = Realm.getDefaultInstance();
        mViewWeakReference = new WeakReference<>(view);
        loadDataOnBind();
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
