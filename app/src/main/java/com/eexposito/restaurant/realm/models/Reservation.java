package com.eexposito.restaurant.realm.models;

import com.eexposito.restaurant.visitors.Visitor;

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

    private Customer mCustomer;

    private String mDateTime;

    public Reservation() {

        super();
    }

    public Reservation(final Customer customer, final String dateTime) {

        super();
        mCustomer = customer;
        mDateTime = dateTime;
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

    public String getTime() {

        return mDateTime;
    }

    public void setDateTime(final String dateTime) {

        mDateTime = dateTime;
    }

    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
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
                .append(mDateTime, reservation.mDateTime)
                .append(mCustomer, reservation.mCustomer)
                .isEquals();
    }
    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////

    @Override
    public void accept(final Visitor visitor) {

        visitor.visit(this);
    }
}
