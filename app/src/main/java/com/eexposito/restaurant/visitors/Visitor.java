package com.eexposito.restaurant.visitors;

import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Reservation;
import com.eexposito.restaurant.realm.models.Table;

public interface Visitor {

    void visit(Customer customer);

    void visit(Table table);

    void visit(Reservation reservation);
}

