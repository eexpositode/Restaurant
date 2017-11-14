package com.eexposito.restaurant._support;


import com.eexposito.restaurant.MainApplication;
import com.eexposito.restaurant._support.injections.components.DaggerTestApplicationComponent;
import com.eexposito.restaurant._support.injections.components.TestApplicationComponent;

public class TestApplication extends MainApplication {

    @Override
    protected TestApplicationComponent getApplicationComponent() {

        TestApplicationComponent applicationComponent = DaggerTestApplicationComponent
                .builder()
                .application(this)
                //                .testApplicationModule(new TestApplicationModule(this))
                //                .testContextModule(new TestContextModule(this))
                .build();
        applicationComponent.inject(this);
        return applicationComponent;
    }

    @Override
    protected void initRealm() {

        // No Realm is initialized
    }
}
