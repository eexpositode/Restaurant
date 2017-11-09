package com.eexposito.restaurant.injections.contributors;


import com.eexposito.restaurant.activities.RestaurantActivity_;
import com.eexposito.restaurant.injections.modules.RestaurantActivityModule;
import com.eexposito.restaurant.activities.RestaurantActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = RestaurantActivityModule.class)
    abstract RestaurantActivity_ bindMainActivity();
}
