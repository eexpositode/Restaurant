package com.eexposito.restaurant._support.fixtures;


import com.eexposito.restaurant.realm.models.Customer;

import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;

import io.realm.RealmObject;
import io.realm.RealmResults;

import static org.mockito.Mockito.mock;

public class FixtureModels {

    public static Customer fixtureCustomer(final int id, final String firstname, final String lastname) {

        Customer customer = new Customer();
        customer.setFirstName(firstname);
        customer.setLastName(lastname);
        customer.setOrder(id);
        return customer;
    }

    @SuppressWarnings("unchecked")
    public static <T extends RealmObject> RealmResults<T> mockRealmResults(final int desiredSize) {

        RealmResults mockResults = mock(RealmResults.class);
        // To make methods think this list is not empty
        Mockito.doAnswer(invocation -> desiredSize).when(mockResults).size();
        return mockResults;
    }

    public static List<Customer> fixtureCustomerList() {

        return Arrays.asList(
                fixtureCustomer(1, "John", "Doe"),
                fixtureCustomer(2, "Jane", "Carter"),
                fixtureCustomer(3, "Jeff", "Hudson"),
                fixtureCustomer(4, "Liam", "Neeson"));
    }
}
