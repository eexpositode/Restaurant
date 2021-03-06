package com.eexposito.restaurant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;

import com.eexposito.restaurant.injections.ApplicationComponent;
import com.eexposito.restaurant.injections.ApplicationModule;
import com.eexposito.restaurant.injections.DaggerApplicationComponent;
import com.eexposito.restaurant.realm.RealmDefinitions;

import org.androidannotations.annotations.EApplication;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.realm.Realm;

@SuppressLint("Registered")
@EApplication
public class MainApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    public MainApplication() {

    }

    @Override
    public void onCreate() {

        super.onCreate();

        getApplicationComponent();
        initRealm();

    }

    public ApplicationComponent getApplicationComponent() {

        ApplicationComponent applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
        return applicationComponent;
    }

    protected void initRealm() {
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