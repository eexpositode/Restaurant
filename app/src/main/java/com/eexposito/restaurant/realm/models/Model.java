package com.eexposito.restaurant.realm.models;


import com.eexposito.restaurant.visitors.Visitable;

public interface Model extends Visitable {

    String ID = "mID";

    String getID();
}
