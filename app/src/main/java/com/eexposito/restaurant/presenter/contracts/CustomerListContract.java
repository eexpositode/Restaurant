package com.eexposito.restaurant.presenter.contracts;


import com.eexposito.restaurant.realm.models.Customer;

import io.realm.RealmResults;

public interface CustomerListContract {

    interface View extends BaseView {

        void onFetchDataStarted();

        void onFetchDataCompleted();

        void onFetchDataSuccess(RealmResults<Customer> modelList);

        void onFetchDataError(final Throwable e);

    }

    interface CustomerPresenter extends Presenter<View> {

        void loadDataOnBind();
    }
}
