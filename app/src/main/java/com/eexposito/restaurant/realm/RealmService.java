package com.eexposito.restaurant.realm;


import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Class to encapsulate realm handling out of the presenters
 */
public class RealmService {

    private final Realm mRealm;

    public RealmService(final Realm realm) {

        mRealm = realm;
    }

    public Realm getRealm() {

        return mRealm;
    }

    public void closeRealm() {

        mRealm.removeAllChangeListeners();
        mRealm.close();
    }
//
//    public <M extends RealmObject> Observable<RealmResults<M>> getAllModelsAsync(Class<M> modelClass) {
//
//        return Observable.just(mRealm.where(modelClass).findAllAsync());
//    }
}
