package com.eexposito.restaurant.presenter;


import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmResults;
import okhttp3.ResponseBody;

public interface DataCallback<M extends RealmModel> {

    void onFetchDataStarted();

    void onFetchDataCompleted();

    void onFetchDataSuccess(RealmResults<M> modelList);

    void onFetchDataError(final Throwable e);

}
