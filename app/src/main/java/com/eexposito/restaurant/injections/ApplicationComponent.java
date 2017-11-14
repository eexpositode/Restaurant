package com.eexposito.restaurant.injections;

import android.app.Application;

import com.eexposito.restaurant.MainApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ApplicationModule.class,
        ActivityModule.class,
        PresentersModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(MainApplication application);
}