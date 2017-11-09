package com.eexposito.restaurant._support.realm;


import com.eexposito.restaurant._support.realm.fixtures.TestReservationsModule;
import com.eexposito.restaurant.realm.ReservationsModule;

import io.realm.RealmConfiguration;

public class TestRealmDefinitions {

    private static final String REALM_NAME = "com.exposito.test-reservations";
    public static final RealmConfiguration mConfiguration = new RealmConfiguration.Builder()
            .inMemory()
            .deleteRealmIfMigrationNeeded()
            .name(REALM_NAME)
            .modules(new ReservationsModule(), new TestReservationsModule())
            .build();
}
