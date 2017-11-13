package com.eexposito.restaurant.presenter;


import com.eexposito.restaurant.presenter.callbacks.BaseCallback;

public interface Presenter {

    void bind(final BaseCallback view);

    void unBind();

    boolean isViewBound();

    void onDestroy();
}
