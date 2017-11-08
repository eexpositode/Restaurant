package com.eexposito.restaurant.realm.models;

import java.util.UUID;

import org.apache.commons.lang.builder.EqualsBuilder;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

@RealmClass
public class Reservation extends RealmObject implements Model {

    @PrimaryKey
    @Required
    private String mID = UUID.randomUUID().toString();

    public Table mTable;

    public Customer mCustomer;

    public Reservation() {
        super();
    }

    public Reservation(final Table table, final Customer customer) {
        super();
        mTable = table;
        mCustomer = customer;
    }
    ////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ////////////////////////////////////////////////////////////////////

    @Override
    public String getID() {

        return mID;
    }

    public Customer getCustomer() {
        return mCustomer;
    }

    public void setCustomer(final Customer customer) {
        mCustomer = customer;
    }

    public Table getTable() {
        return mTable;
    }

    public void setTable(final Table table) {
        mTable = table;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Reservation)) {
            return false;
        }

        Reservation reservation = ((Reservation) obj);
        return new EqualsBuilder()
                .append(mTable, reservation.mTable)
                .append(mCustomer, reservation.mCustomer)
                .isEquals();
    }
}
