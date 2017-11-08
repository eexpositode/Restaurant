package com.eexposito.restaurant;

import android.app.Activity;
import android.app.Application;

import com.eexposito.restaurant.injections.components.ApplicationComponent;
import com.eexposito.restaurant.injections.components.DaggerApplicationComponent;
import com.eexposito.restaurant.realm.RealmDefinitions;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.realm.Realm;

// TODO: 4/11/17 Use Android Annotations @EApplication?
public class MainApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

//    protected ApplicationComponent mAppComponent;

    public MainApplication() {

    }

    @Override
    public void onCreate() {

        super.onCreate();

        getApplicationComponent();
        initializeRealm();

    }

    protected ApplicationComponent getApplicationComponent() {

        ApplicationComponent applicationComponent = DaggerApplicationComponent
                .builder()
                .application(this)
                .build();
        applicationComponent.inject(this);
        return applicationComponent;
    }

    protected void initializeRealm() {
        // securityKey
        char[] chars = "16CharacterLongPasswordKey4Realm".toCharArray();
        byte[] key = new byte[chars.length * 2];
        for (int i = 0; i < chars.length; i++) {
            key[i * 2] = (byte) (chars[i] >> 8);
            key[i * 2 + 1] = (byte) chars[i];
        }

        /* realmConfiguration */
        Realm.init(this);
        Realm.setDefaultConfiguration(RealmDefinitions.mConfiguration);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {

        return activityDispatchingAndroidInjector;
    }
}