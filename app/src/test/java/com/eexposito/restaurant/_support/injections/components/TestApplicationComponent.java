package com.eexposito.restaurant._support.injections.components;

import android.app.Application;

import com.eexposito.restaurant._support.TestApplication;
import com.eexposito.restaurant._support.injections.modules.TestApplicationModule;
import com.eexposito.restaurant.injections.components.ApplicationComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        TestApplicationModule.class})
public interface TestApplicationComponent extends ApplicationComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        TestApplicationComponent build();
    }

    void inject(TestApplication application);
}