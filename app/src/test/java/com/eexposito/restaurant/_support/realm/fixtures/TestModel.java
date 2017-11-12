package com.eexposito.restaurant._support.realm.fixtures;

import com.eexposito.restaurant.realm.models.Model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

@RealmClass
public class TestModel extends RealmObject implements Model {

    @PrimaryKey
    @Required
    private String mId = UUID.randomUUID().toString();

    @Override
    public String getID() {

        return mId;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Model) {
            final Model other = (Model) obj;
            return mId.equals(other.getID());
        } else {
            return this == obj;
        }
    }
}