package com.eexposito.restaurant.realm;

import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Reservation;
import com.eexposito.restaurant.realm.models.Table;

import io.realm.annotations.RealmModule;

@RealmModule(classes = {Customer.class, Table.class, Reservation.class})
public class ReservationsModule {

}
