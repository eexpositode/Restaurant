package com.eexposito.restaurant._support;

import com.eexposito.restaurant.MainApplication;

import io.realm.Realm;


public class JUnitTestApplication extends MainApplication {

    @Override
    protected void initRealm() {

        Realm.init(this);
        Realm.setDefaultConfiguration(TestRealmDefinitions.mConfiguration);
    }
}
