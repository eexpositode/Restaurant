package com.eexposito.restaurant.presenter;


import io.realm.RealmObject;

public interface Presenter<M extends RealmObject> {

    void bind(final DataCallback<M> view);

    void unBind();

    boolean isViewBound();

    void loadData();

    void onDestroy();
}
