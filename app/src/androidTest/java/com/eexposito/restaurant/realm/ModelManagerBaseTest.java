package com.eexposito.restaurant.realm;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.RealmBaseTest;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.models.Customer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;

public class ModelManagerBaseTest extends RealmBaseTest {

    private ModelManager mModelManager;

    @Before
    public void setup() {

        super.setup();
        mModelManager = new ModelManager();
    }

    @After
    public void tearDown() {

        super.tearDown();
        mModelManager = null;
    }

    @Test
    public void shouldBeAbleToGetDefaultInstance() throws Exception {

        assertThat(mRealm, notNullValue());
    }

    /////////////////////////////////////////////////////////////////////
    // getModels()
    /////////////////////////////////////////////////////////////////////
    @Test
    public void testGetModelListIsEmpty() throws Exception {

        RealmResults<Customer> result = mModelManager.getAllModels(mRealm, Customer.class);
        assertThat(result, notNullValue());
        assertThat(result, hasSize(0));
    }

    @Test
    public void testGetModelListNotEmpty() throws Exception {

        Customer customer = fixtureSavedCustomer(1, "John", "Doe");

        RealmResults<Customer> modelList = mModelManager.getAllModels(mRealm, Customer.class);
        assertThat(modelList, notNullValue());
        assertThat(modelList, hasSize(1));
        assertThat(modelList.get(0), equalTo(customer));
    }

    /////////////////////////////////////////////////////////////////////
    // saveModels()
    /////////////////////////////////////////////////////////////////////
    @Test
    public void testSaveModels() throws Exception {

        List<Customer> customers = Arrays.asList(fixtureCustomer(1, "John", "Doe"),
                fixtureCustomer(2, "Jane", "Doe"),
                fixtureCustomer(3, "Mike", "Jagger"),
                fixtureCustomer(4, "Perry", "Mason"));

        assertThat(mModelManager.getAllModels(mRealm, Customer.class), is(empty()));
        mModelManager.saveModels(customers);
        RealmResults<Customer> results = mModelManager.getAllModels(mRealm, Customer.class);

        assertThat(results, hasSize(4));
        assertThat(results, hasItem(customers.get(0)));
        assertThat(results, hasItem(customers.get(1)));
        assertThat(results, hasItem(customers.get(2)));
        assertThat(results, hasItem(customers.get(3)));
    }

    @Test(expected = RealmPrimaryKeyConstraintException.class)
    public void testSaveModelsIfAlreadyExists() throws Exception {

        Customer customer = fixtureSavedCustomer(1, "John", "Doe");

        mModelManager.saveModels(Collections.singletonList(customer));
        RealmResults<Customer> allModels = mModelManager.getAllModels(mRealm, Customer.class);

        assertThat(allModels, hasSize(1));
        assertThat(allModels.get(0), equalTo(customer));
    }

    /////////////////////////////////////////////////////////////////////
    // getModelByID()
    /////////////////////////////////////////////////////////////////////
    @Test
    public void testGetModelByIDNoExists() throws Exception {

        RealmResults<Customer> results = mModelManager.getModelByID(mRealm, Customer.class, "1");

        assertThat(results, notNullValue());
        assertThat(results, is(empty()));
    }

    @Test
    public void testGetModelByIDExists() throws Exception {

        Customer customer = fixtureSavedCustomer(1, "John", "Doe");

        RealmResults<Customer> results = mModelManager.getModelByID(mRealm, Customer.class, customer.getID());

        assertThat(results, notNullValue());
        assertThat(results, hasItem(customer));
    }

    /////////////////////////////////////////////////////////////////////
    // createReservation
    /////////////////////////////////////////////////////////////////////
    // TODO: 14/11/17 Do it
    /////////////////////////////////////////////////////////////////////
    // removeReservation
    /////////////////////////////////////////////////////////////////////
    // TODO: 14/11/17 Do it

    /////////////////////////////////////////////////////////////////////
    // Fixtures
    /////////////////////////////////////////////////////////////////////
    @NonNull
    private Customer fixtureSavedCustomer(final int order, final String firstname, final String lastname) {

        return saveModel(mRealm, fixtureCustomer(order, firstname, lastname));
    }

    @NonNull
    private Customer fixtureCustomer(final int order, final String firstname, final String lastname) {

        Customer customer = new Customer();
        customer.setFirstName(firstname);
        customer.setLastName(lastname);
        customer.setOrder(order);
        return customer;
    }

    private <M extends RealmObject> M saveModel(final Realm realm, M model) {

        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(model));
        return model;
    }
}
