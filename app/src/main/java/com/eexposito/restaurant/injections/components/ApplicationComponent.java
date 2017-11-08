package com.eexposito.restaurant.injections.components;

import android.app.Application;

import com.eexposito.restaurant.MainApplication;
import com.eexposito.restaurant.injections.contributors.ActivityBuilder;
import com.eexposito.restaurant.injections.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ApplicationModule.class,
        ActivityBuilder.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(MainApplication application);
}