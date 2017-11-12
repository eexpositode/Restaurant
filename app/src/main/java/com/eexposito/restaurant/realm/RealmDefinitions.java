package com.eexposito.restaurant.realm;


import io.realm.RealmConfiguration;

public class RealmDefinitions {

    private static final String REALM_NAME = "com.exposito.reservations";
    public static final RealmConfiguration mConfiguration = new RealmConfiguration.Builder()
            .name(REALM_NAME)
            .deleteRealmIfMigrationNeeded()
            .modules(new ReservationsModule())
            .build();
}
