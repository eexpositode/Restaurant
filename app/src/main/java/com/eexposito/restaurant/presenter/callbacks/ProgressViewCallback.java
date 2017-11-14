package com.eexposito.restaurant.presenter.callbacks;


import io.realm.RealmObject;
import io.realm.RealmResults;

public interface ProgressViewCallback<M extends RealmObject> extends BaseCallback {

    void onFetchDataStarted();

    void onFetchDataCompleted();

    void onFetchDataSuccess(RealmResults<M> modelList);
}
