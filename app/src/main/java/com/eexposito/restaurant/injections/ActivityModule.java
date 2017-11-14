package com.eexposito.restaurant.injections;


import com.eexposito.restaurant.activities.ReservationsActivity_;
import com.eexposito.restaurant.activities.RestaurantActivity_;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract RestaurantActivity_ bindRestaurantActivity();

    @ContributesAndroidInjector
    abstract ReservationsActivity_ bindReservationsActivity();
}
