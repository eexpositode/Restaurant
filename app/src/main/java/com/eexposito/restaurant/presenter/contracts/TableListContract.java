package com.eexposito.restaurant.presenter.contracts;


import com.eexposito.restaurant.realm.models.Table;

import io.realm.RealmResults;

public interface TableListContract {

    interface View extends BaseView {

        void onFetchDataStarted();

        void onFetchDataCompleted();

        void onFetchDataSuccess(RealmResults<Table> modelList);

        void onFetchDataError(final Throwable e);
    }

    interface TablePresenter extends Presenter<View> {

        void loadDataOnBind();
    }
}
