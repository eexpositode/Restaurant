package com.eexposito.restaurant._support;


import com.eexposito.restaurant._support.fixtures.TestReservationsModule;
import com.eexposito.restaurant.realm.ReservationsModule;

import io.realm.RealmConfiguration;

public class TestRealmDefinitions {

    public static final RealmConfiguration mConfiguration = new RealmConfiguration.Builder()
            .inMemory()
            .deleteRealmIfMigrationNeeded()
            .name("com.exposito.test-reservations")
            .modules(new ReservationsModule(), new TestReservationsModule())
            .build();
}
