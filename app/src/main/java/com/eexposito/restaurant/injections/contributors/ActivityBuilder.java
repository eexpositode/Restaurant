package com.eexposito.restaurant.injections.contributors;


import com.eexposito.restaurant.activities.ReservationsActivity_;
import com.eexposito.restaurant.activities.RestaurantActivity_;
import com.eexposito.restaurant.injections.modules.ReservationsActivityModule;
import com.eexposito.restaurant.injections.modules.RestaurantActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = RestaurantActivityModule.class)
    abstract RestaurantActivity_ bindRestaurantActivity();

    @ContributesAndroidInjector(modules = ReservationsActivityModule.class)
    abstract ReservationsActivity_ bindReservationsActivity();
}
