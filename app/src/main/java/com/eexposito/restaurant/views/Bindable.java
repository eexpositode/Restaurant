package com.eexposito.restaurant.views;


import java.util.List;

import io.realm.RealmModel;
import okhttp3.ResponseBody;

public interface Bindable<M extends RealmModel> {

    void onFetchDataStarted();

    void onFetchDataCompleted();

    void onFetchDataSuccess(List<M> modelList);

    // TODO: 1/11/17 We need this?
    void onResponseFail(ResponseBody responseBody);

    void onFetchDataError(final Throwable e);

}
