package com.eexposito.restaurant.realm.models;

import com.eexposito.restaurant.visitors.Visitor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import org.apache.commons.lang.builder.EqualsBuilder;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

// TODO: 3/11/17 use Lombok to set @getter and @setter
@RealmClass
public class Customer extends RealmObject implements Model {

    public static final String FIRST_NAME = "customerFirstName";
    public static final String LAST_NAME = "customerLastName";
    public static final String ORDER = "id";

    @PrimaryKey
    @Required
    private String mID = UUID.randomUUID().toString();

    @SerializedName(ORDER)
    @Expose
    private int mOrder;

    @SerializedName(FIRST_NAME)
    @Expose
    private String mFirstName;

    @SerializedName(LAST_NAME)
    @Expose
    private String mLastName;

    public Customer() {

        super();
    }

    public Customer(final int order, final String firstName, final String lastName) {

        super();
        mOrder = order;
        mFirstName = firstName;
        mLastName = lastName;
    }

    ////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ////////////////////////////////////////////////////////////////////

    @Override
    public String getID() {

        return mID;
    }

    public String getFirstName() {

        return mFirstName;
    }

    public void setFirstName(String firstName) {

        this.mFirstName = firstName;
    }

    public String getLastName() {

        return mLastName;
    }

    public void setLastName(String lastName) {

        this.mLastName = lastName;
    }

    public int getOrder() {

        return mOrder;
    }

    public void setOrder(Integer order) {

        this.mOrder = order;
    }

    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Customer)) {
            return false;
        }

        Customer customer = ((Customer) obj);
        return new EqualsBuilder()
                .append(mOrder, customer.mOrder)
                .append(mFirstName, customer.mFirstName)
                .append(mLastName, customer.mLastName)
                .isEquals();
    }
    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////

    @Override
    public void accept(final Visitor visitor) {

        visitor.visit(this);
    }
}
