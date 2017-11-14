package com.eexposito.restaurant.presenter;


import com.eexposito.restaurant.presenter.callbacks.BaseCallback;
import com.eexposito.restaurant.presenter.callbacks.ProgressViewCallback;

import java.lang.ref.WeakReference;

import io.realm.Realm;

public abstract class BasePresenter<C extends BaseCallback> implements Presenter {

    protected Realm mRealm;
    protected WeakReference<C> mViewWeakReference;

    @Override
    public void bind(final BaseCallback view) {

        mRealm = Realm.getDefaultInstance();
        mViewWeakReference = new WeakReference<>((C) view);
        loadDataOnBind();
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
    public void onDestroy() {

        mRealm.close();
    }
}
