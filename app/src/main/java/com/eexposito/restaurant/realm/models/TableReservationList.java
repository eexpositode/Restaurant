package com.eexposito.restaurant.realm.models;


import java.util.ArrayList;
import java.util.List;

public class TableReservationList {

    private List<Boolean> mAvailability = new ArrayList<>();

    public TableReservationList(final Boolean[] values) {

    }

    public TableReservationList(final List<Boolean> availabilityList) {

        mAvailability = availabilityList;
    }

    public List<Boolean> getAvailability() {

        return mAvailability;
    }
}
