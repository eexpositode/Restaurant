package com.eexposito.restaurant.visitors;


import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Reservation;
import com.eexposito.restaurant.realm.models.Table;

public class PrintModelVisitor implements Visitor {

    String modelToString = "";

    public String getModelToString() {

        return modelToString;
    }

    @Override
    public void visit(final Customer customer) {

        modelToString = modelToString.concat(customer.getLastName()).concat(", ").concat(customer.getFirstName());
    }

    @Override
    public void visit(final Table table) {

        modelToString = modelToString.concat("Table ").concat(String.valueOf(table.getOrder() + 1));
    }

    @Override
    public void visit(final Reservation reservation) {

        modelToString = modelToString.concat("at: ").concat(reservation.getTime());
    }
}
