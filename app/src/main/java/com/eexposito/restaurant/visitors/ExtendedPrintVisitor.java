package com.eexposito.restaurant.visitors;


import com.eexposito.restaurant.realm.models.Customer;

public class ExtendedPrintVisitor extends PrintModelVisitor {

    @Override
    public void visit(final Customer customer) {

        modelToString = modelToString.concat(String.valueOf(customer.getOrder()))
                .concat(". ")
                .concat(customer.getLastName())
                .concat(", ")
                .concat(customer.getFirstName());
    }
}
