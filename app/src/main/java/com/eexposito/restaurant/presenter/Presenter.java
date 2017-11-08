package com.eexposito.restaurant.presenter;


import com.eexposito.restaurant.views.Bindable;

import io.realm.RealmObject;

public interface Presenter<M extends RealmObject> {

    void bind(final Bindable<M> view);

    void loadData();

    void unBind();

    void onDestroy();
}
