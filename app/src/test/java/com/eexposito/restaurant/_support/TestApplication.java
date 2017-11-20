package com.eexposito.restaurant._support;


import android.app.Application;

import com.eexposito.restaurant._support.injections.components.DaggerTestApplicationComponent;
import com.eexposito.restaurant._support.injections.components.TestApplicationComponent;
import com.eexposito.restaurant.realm.RealmDefinitions;

import io.realm.Realm;

public class TestApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        getApplicationComponent();
        initRealm();
    }

    protected TestApplicationComponent getApplicationComponent() {

        TestApplicationComponent applicationComponent = DaggerTestApplicationComponent
                .builder()
//                .testApplicationModule(new TestApplicationModule(this))
                .build();
        applicationComponent.inject(this);
        return applicationComponent;
    }

    protected void initRealm() {

        Realm.init(this);
        Realm.setDefaultConfiguration(RealmDefinitions.mConfiguration);
    }
}
