package com.eexposito.restaurant._support.injections.components;

import com.eexposito.restaurant._support.TestApplication;
import com.eexposito.restaurant._support.injections.modules.TestApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestApplicationModule.class})
public interface TestApplicationComponent {


    void inject(TestApplication application);
}