package com.eexposito.restaurant._support.injections.modules;

import com.eexposito.restaurant._support.TestApplication;

import dagger.Module;

@Module
public class TestApplicationModule {

    private final TestApplication mApplication;

    public TestApplicationModule(TestApplication app) {

        mApplication = app;
    }
}