package com.eexposito.restaurant.realm;


import io.realm.Realm;

public class RealmFactory {

    private Realm mRealm;

    public Realm getRealm() {

        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }
        return mRealm;
    }

    public void closeRealm() {

        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
        mRealm = null;
    }
}
