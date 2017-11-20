package com.eexposito.restaurant._support.injections.modules;

import android.app.Application;

import com.eexposito.restaurant._support.TestApplication;

import dagger.Module;
import dagger.Provides;


@Module
public class TestApplicationModule {

    private final TestApplication mApplication;

    public TestApplicationModule(TestApplication app) {

        mApplication = app;
    }

    @Provides
    public Application provideTestApplication() {

        return mApplication;
    }
}