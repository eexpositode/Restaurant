package com.eexposito.restaurant.realm.models;


import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import com.eexposito.restaurant._support.RealmBaseTest;
import com.eexposito.restaurant._support.fixtures.TestModel;
import com.eexposito.restaurant._support.fixtures.TestModelManager;
import com.eexposito.restaurant.realm.exceptions.BusinessError;
import com.eexposito.restaurant.realm.exceptions.BusinessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.RealmResults;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class ModelManagerBaseTest extends RealmBaseTest {

    private TestModelManager mModelManager;

    @Before
    public void setup() {

        super.setup();
        mModelManager = new TestModelManager();
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
    // saveOrUpdateCustomerList()
    /////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void testSaveModelListIsNull() throws Exception {

        mModelManager.saveOrUpdateModelList(null);
    }

    @Test
    public void testSaveModelListSuccess() throws Exception {

        List<TestModel> models = Collections.singletonList(new TestModel());
        mModelManager.saveOrUpdateModelList(models);

        RealmResults<TestModel> results = getAllOfType(mRealm, TestModel.class);
        assertThat(results, hasSize(models.size()));
        results.forEach(result -> {
            assertThat(models.stream().filter(model -> model.equals(result)).findAny(),
                    notNullValue());
        });
    }

    @Test
    public void testSaveModelListNoDuplicates() throws Exception {

        TestModel model = new TestModel();
        List<TestModel> models = Collections.singletonList(saveModel(mRealm, model));
        mModelManager.saveOrUpdateModelList(models);
        mModelManager.saveOrUpdateModelList(models);

        RealmResults<TestModel> results = getAllOfType(mRealm, TestModel.class);
        assertThat(results, hasSize(1));
        assertThat(results.get(0), equalTo(model));
    }

    @Test
    public void testSaveModelListDifferentInstances() throws Exception {

        TestModel model = new TestModel();
        List<TestModel> models = Collections.singletonList(saveModel(mRealm, model));
        mModelManager.saveOrUpdateModelList(models);
        TestModel model2 = new TestModel();
        List<TestModel> models2 = Collections.singletonList(saveModel(mRealm, model2));
        mModelManager.saveOrUpdateModelList(models2);

        RealmResults<TestModel> results = getAllOfType(mRealm, TestModel.class);
        assertThat(results, hasSize(2));
        assertThat(results.get(0), equalTo(model));
        assertThat(results.get(1), equalTo(model2));
    }

    /////////////////////////////////////////////////////////////////////
    // getModels()
    /////////////////////////////////////////////////////////////////////
    @Test
    public void testGetModelListIsEmpty() throws Exception {

        List<TestModel> modelList = mModelManager.getModels(TestModel.class);
        assertThat(modelList, notNullValue());
        assertThat(modelList, hasSize(0));
    }

    @Test
    public void testGetModelListNotEmpty() throws Exception {

        TestModel model = new TestModel();
        saveModel(mRealm, model);

        List<TestModel> modelList = mModelManager.getModels(TestModel.class);
        assertThat(modelList, notNullValue());
        assertThat(modelList, hasSize(1));
        assertThat(modelList.get(0), equalTo(model));
    }

    /////////////////////////////////////////////////////////////////////
    // getModels()
    /////////////////////////////////////////////////////////////////////
    @Test
    public void testInitialTableSetupEmptyList() throws Exception {

        TableReservationList tableReservationList = fixtureTableReservationList();
        mModelManager.initialTableSetup(tableReservationList, new ArrayList<>());

        RealmResults<Table> tables = getAllOfType(mRealm, Table.class);
        assertThat(tables, notNullValue());
        assertThat(tables, hasSize(0));
        RealmResults<Reservation> reservations = getAllOfType(mRealm, Reservation.class);
        assertThat(reservations, notNullValue());
        assertThat(reservations, hasSize(0));
        RealmResults<Customer> customers = getAllOfType(mRealm, Customer.class);
        assertThat(customers, notNullValue());
        assertThat(customers, hasSize(0));
    }

    @Test
    public void testInitialTableSetupAllFalse() throws Exception {

        TableReservationList tableReservationList = fixtureTableReservationList(false, false, false, false);
        mModelManager.initialTableSetup(tableReservationList, new ArrayList<>());

        RealmResults<Table> tables = getAllOfType(mRealm, Table.class);
        assertThat(tables, notNullValue());
        assertThat(tables, hasSize(tableReservationList.mAvailability.size()));
        RealmResults<Reservation> reservations = getAllOfType(mRealm, Reservation.class);
        assertThat(reservations, notNullValue());
        assertThat(reservations, hasSize(0));
        RealmResults<Customer> customers = getAllOfType(mRealm, Customer.class);
        assertThat(customers, notNullValue());
        assertThat(customers, hasSize(0));
    }

    /////////////////////////////////////////////////////////////////////
    // createReservation()
    /////////////////////////////////////////////////////////////////////
    @Test(expected = BusinessException.class)
    public void testCreateReservationInvalidTable() throws Exception {

        Table table = fixtureTable();
        Customer customer = fixtureCustomer(1, "John", "Doe");
        try {
            mModelManager.createReservation(table, customer);
        } catch (BusinessException e) {
            assertThat(e.businessError, equalTo(BusinessError.DATA_NOT_FOUND));
            RealmResults<Reservation> reservations = getAllOfType(mRealm, Reservation.class);
            assertThat(reservations, notNullValue());
            assertThat(reservations, hasSize(0));
            throw e;
        }
    }

    @Test(expected = BusinessException.class)
    public void testCreateReservationInvalidCustomer() throws Exception {

        Table table = fixtureSavedTable();
        Customer customer = fixtureCustomer(1, "John", "Doe");
        try {
            mModelManager.createReservation(table, customer);
        } catch (BusinessException e) {
            assertThat(e.businessError, equalTo(BusinessError.DATA_NOT_FOUND));
            RealmResults<Reservation> reservations = getAllOfType(mRealm, Reservation.class);
            assertThat(reservations, notNullValue());
            assertThat(reservations, hasSize(0));
            throw e;
        }
    }

    @Test
    public void testCreateReservationValidInputData() throws Exception {

        Table table = fixtureSavedTable();
        Customer customer = fixtureSavedCustomer(1, "John", "Doe");

        mModelManager.createReservation(table, customer);
        RealmResults<Reservation> reservations = getAllOfType(mRealm, Reservation.class);
        assertThat(reservations, notNullValue());
        assertThat(reservations, hasSize(1));
        Reservation reservation = reservations.stream().findAny().orElse(null);
        assertThat(reservation, notNullValue());
        assertThat(reservation.getTable(), equalTo(table));
        assertThat(reservation.getCustomer(), equalTo(customer));
    }

    @Test(expected = BusinessException.class)
    public void testUpdateReservationInvalid() throws Exception {

        Table table = fixtureSavedTable();
        Customer customer = fixtureSavedCustomer(1, "John", "Doe");
        Reservation reservation = fixtureSavedReservation(table, customer);

        try {
            mModelManager.createReservation(table, customer);
        } catch (BusinessException e) {
            assertThat(e.businessError, equalTo(BusinessError.RESERVATION_ALREADY_EXISTS));

            RealmResults<Reservation> reservations = getAllOfType(mRealm, Reservation.class);
            assertThat(reservations, notNullValue());
            assertThat(reservations, hasSize(1));
            Reservation found = reservations.stream().findAny().orElse(null);
            assertThat(found, notNullValue());
            assertThat(found.getTable(), equalTo(reservation.getTable()));
            assertThat(found.getCustomer(), equalTo(reservation.getCustomer()));
            throw e;
        }
    }

    /////////////////////////////////////////////////////////////////////
    // removeReservations()
    /////////////////////////////////////////////////////////////////////
    @Test
    public void testRemoveReservationsEmptyList() throws Exception {

        Reservation reservation = fixtureSavedReservation(fixtureSavedTable(),
                fixtureSavedCustomer(1, "John", "Doe"));
        Reservation reservation2 = fixtureSavedReservation(fixtureSavedTable(),
                fixtureSavedCustomer(3, "Jane", "Carter"));

        mModelManager.removeReservations(new ArrayList<>());

        RealmResults<Reservation> reservations = getAllOfType(mRealm, Reservation.class);
        assertThat(reservations, notNullValue());
        assertThat(reservations, hasSize(2));
        assertThat(reservations.stream().anyMatch(model -> model.equals(reservation)), is(true));
        assertThat(reservations.stream().anyMatch(model -> model.equals(reservation2)), is(true));
    }

    @Test
    public void testRemoveReservationsRemoveOne() throws Exception {

        Reservation reservation = fixtureSavedReservation(fixtureSavedTable(),
                fixtureSavedCustomer(1, "John", "Doe"));
        Reservation reservation2 = fixtureSavedReservation(fixtureSavedTable(),
                fixtureSavedCustomer(3, "Jane", "Carter"));

        mModelManager.removeReservations(Collections.singletonList(reservation2));

        RealmResults<Reservation> reservations = getAllOfType(mRealm, Reservation.class);
        assertThat(reservations, notNullValue());
        assertThat(reservations, hasSize(1));
        assertThat(reservations.stream().anyMatch(model -> model.equals(reservation)), is(true));
    }

    @Test
    public void testRemoveReservationsRemoveMoreThanOne() throws Exception {

        Reservation reservation = fixtureSavedReservation(fixtureSavedTable(),
                fixtureSavedCustomer(1, "John", "Doe"));
        Reservation reservation2 = fixtureSavedReservation(fixtureSavedTable(),
                fixtureSavedCustomer(3, "Jane", "Carter"));
        Reservation reservation3 = fixtureSavedReservation(fixtureSavedTable(),
                fixtureSavedCustomer(5, "Mary", "Keller"));

        mModelManager.removeReservations(Arrays.asList(reservation, reservation3));

        RealmResults<Reservation> reservations = getAllOfType(mRealm, Reservation.class);
        assertThat(reservations, notNullValue());
        assertThat(reservations, hasSize(1));
        assertThat(reservations.stream().anyMatch(model -> model.equals(reservation2)), is(true));
    }

    /////////////////////////////////////////////////////////////////////
    // Fixtures
    /////////////////////////////////////////////////////////////////////

    @NonNull
    private Reservation fixtureSavedReservation(final Table table, final Customer customer) {

        Table managedTable = saveModel(mRealm, table);
        Customer managedCustomer = saveModel(mRealm, customer);
        Reservation reservation = fixtureReservation(managedTable, managedCustomer);
        return saveModel(mRealm, reservation);
    }

    @NonNull
    private Reservation fixtureReservation(final Table table, final Customer customer) {

        return new Reservation(table, customer);
    }

    @NonNull
    private TableReservationList fixtureTableReservationList(Boolean... values) {

        return new TableReservationList(values);
    }

    @NonNull
    private Table fixtureSavedTable() {

        return saveModel(mRealm, fixtureTable());
    }

    @NonNull
    private Table fixtureTable() {

        return new Table();
    }

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
}
