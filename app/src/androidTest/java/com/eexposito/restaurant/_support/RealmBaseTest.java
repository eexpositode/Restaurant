package com.eexposito.restaurant._support;


import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

@RunWith(AndroidJUnit4.class)
@Ignore("No testing class")
public class RealmBaseTest {

    protected Realm mRealm;

    @Before
    public void setup() {

        mRealm = Realm.getDefaultInstance();
    }

    @After
    public void tearDown() {

        purgeRealm();
        mRealm.close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    protected <M extends RealmObject> RealmResults<M> getAllOfType(final Realm realm, Class<M> realmClass) {

        return realm.where(realmClass).findAll();
    }

    protected <M extends RealmObject> M saveModel(final Realm realm, M model) {

        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(model));
        return model;
    }

    private void purgeRealm() {

        mRealm.executeTransaction(realm -> realm.deleteAll());
    }
}
