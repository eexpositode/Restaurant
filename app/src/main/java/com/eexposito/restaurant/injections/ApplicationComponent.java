package com.eexposito.restaurant.injections;

import com.eexposito.restaurant.MainApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ActivityBinderModule.class})
public interface ApplicationComponent extends ServiceModule.ServiceComponent {

    void inject(MainApplication application);
}