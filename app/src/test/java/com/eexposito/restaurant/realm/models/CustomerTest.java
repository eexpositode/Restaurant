package com.eexposito.restaurant.realm.models;


import com.eexposito.restaurant._support.BaseTest;

import org.junit.Test;

import static com.eexposito.reservations._support.fixtures.FixtureModels.fixtureCustomer;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class CustomerTest extends BaseTest {

    @Test
    public void testConstructor() {

        String firstName = "John";
        String lastName = "Doe";
        int order = 1;
        Customer model = fixtureCustomer(order, firstName, lastName);
        assertThat("UUID cannot be null", model.getID(), notNullValue());
        assertThat(String.format("Firstname has to be %s", firstName), model.getFirstName(), equalTo(firstName));
        assertThat(String.format("Lastname has to be %s", lastName), model.getLastName(), equalTo(lastName));
        assertThat(String.format("Id has to be %s", String.valueOf(order)), model.getOrder(), equalTo(order));
    }

    @Test
    public void testSetFirstName() {

        String firstName = "John";
        String lastName = "Doe";
        int order = 1;
        Customer model = fixtureCustomer(order, firstName, lastName);
        assertThat(String.format("Firstname has to be %s", firstName), model.getFirstName(), equalTo(firstName));
        String newName = "Jane";
        model.setFirstName(newName);
        assertThat(String.format("Firstname has to be %s", newName), model.getFirstName(), equalTo(newName));
    }

    @Test
    public void testSetLastName() {

        String firstName = "John";
        String lastName = "Doe";
        int id = 1;
        Customer model = fixtureCustomer(id, firstName, lastName);
        assertThat(String.format("LastName has to be %s", lastName), model.getLastName(), equalTo(lastName));
        String newName = "Carter";
        model.setLastName(newName);
        assertThat(String.format("LastName has to be %s", newName), model.getLastName(), equalTo(newName));
    }

    @Test
    public void testSetId() {

        String firstName = "John";
        String lastName = "Doe";
        int order = 1;
        Customer model = fixtureCustomer(order, firstName, lastName);
        assertThat(String.format("Id has to be %s", String.valueOf(order)), model.getOrder(), equalTo(order));
        order = 3;
        model.setOrder(order);
        assertThat(String.format("Id has to be %s", String.valueOf(order)), model.getOrder(), equalTo(order));
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // Fixtures
    /////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testEqualsSameObject() {

        Customer model = fixtureCustomer(1, "John","Doe");
        assertThat("Models should be equal", model, equalTo(model));
    }

    @Test
    public void testEqualsDifferentClasses() {

        Customer model = fixtureCustomer(1, "John","Doe");
        Object any = new Object();
        assertThat("Objects should not be equal", model, not(equalTo(any)));
    }

    @Test
    public void testEqualsDifferentInstances() {

        Customer model = fixtureCustomer(1, "John","Doe");
        Customer model2 = fixtureCustomer(5, "Jane", "Doe");
        assertThat("Models should not be equal", model, not(equalTo(model2)));
    }
}
