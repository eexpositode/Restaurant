package com.eexposito.restaurant.realm.models;


import java.util.ArrayList;
import java.util.List;

public class TableReservationList {

    public List<Boolean> mAvailability = new ArrayList<>();

    public TableReservationList(final Boolean[] values) {

    }

    public TableReservationList(final List<Boolean> availabilityList) {

        mAvailability = availabilityList;
    }
}
//public class TableReservationList extends RealmObject implements Model {
//
//    @PrimaryKey
//    @Required
//    private String mId = UUID.randomUUID().toString();
//
//    public RealmList<Boolean> mAvailability;
//
//    public TableReservationList() {}
//
//    public TableReservationList(final RealmList<Boolean> availabilityList) {
//        mAvailability = availabilityList;
//    }
//
//    @Override
//    public String getID() {
//
//        return mId;
//    }
//}
