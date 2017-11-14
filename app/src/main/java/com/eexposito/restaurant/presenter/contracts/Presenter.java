package com.eexposito.restaurant.presenter.contracts;


public interface Presenter<V extends BaseView> {

    void bind(final V view);

    void unBind();

    boolean isViewBound();

    void clear();
}
