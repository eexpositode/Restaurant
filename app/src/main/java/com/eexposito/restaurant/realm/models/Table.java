package com.eexposito.restaurant.realm.models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

@RealmClass
public class Table extends RealmObject implements Model {

    @PrimaryKey
    @Required
    private String mID = UUID.randomUUID().toString();

    private int mOrder;

    private Reservation mReservation;

    public Table() {

        super();
    }

    public Table(final int order) {

        mOrder = order;
    }
    ////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ////////////////////////////////////////////////////////////////////

    @Override
    public String getID() {

        return mID;
    }

    public Reservation getReservation() {

        return mReservation;
    }

    public int getOrder() {

        return mOrder;
    }

    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Model) {
            final Model other = (Model) obj;
            return mID.equals(other.getID());
        } else {
            return this == obj;
        }
    }
}
