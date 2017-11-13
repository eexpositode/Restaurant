package com.eexposito.restaurant.realm;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Reservation;
import com.eexposito.restaurant.realm.models.Table;

import java.util.List;
import java.util.function.Predicate;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ModelManager {

    public <M extends RealmObject> RealmResults<M> getAllModels(@NonNull final Realm realm,
                                                                @NonNull final Class<M> modelClass) {

        return realm.where(modelClass).findAll();
    }

    public <M extends RealmObject> void saveModels(@NonNull final List<M> models) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealm(models));
        realm.close();
    }

    public <M extends RealmObject> boolean checkPredicate(@NonNull final List<M> models, @NonNull final Predicate<M> predicate) {

        return models.stream().anyMatch(predicate);
    }

    public <M extends RealmObject> RealmResults<M> getModelByField(final Realm realm, final Class<M> modelClass, final String field, final String value) {

        return realm.where(modelClass).equalTo(field, value).findAll();
    }

    public void createReservation(@NonNull final String tableID, @NonNull final String customerID, @NonNull final String time) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {

            Table table = getModelByField(realm1, Table.class, Table.ID, tableID).first();
            Customer customer = getModelByField(realm1, Customer.class, Customer.ID, customerID).first();
            Reservation reservation = realm1.copyToRealm(new Reservation(customer, time));
            table.setReservation(reservation);
            realm1.insertOrUpdate(table);
        });
        realm.close();
    }

    public void removeReservation(final Table table) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {

            RealmResults<Reservation> rows = getModelByField(
                    realm1,
                    Reservation.class,
                    Reservation.ID,
                    table.getReservation().getID());
            rows.deleteAllFromRealm();
            table.setReservation(null);
            realm1.insertOrUpdate(table);
        });
        realm.close();
    }

    //    /**
    //     * Save a list of RealmObjects
    //     *
    //     * @param modelList to save
    //     * @param <M>       the accepted type
    //     */
    //    public <M extends RealmObject> void saveOrUpdateModelList(@NonNull List<M> modelList) {
    //
    //        Realm realm = Realm.getDefaultInstance();
    //        saveOrUpdateModelList(realm, modelList);
    //        realm.close();
    //    }
    //
    //    /**
    //     * Get all models from a concrete Class
    //     *
    //     * @param modelClass the class of the accepted type
    //     * @param <M>        the accepted type
    //     * @return a list with all models found
    //     */
    //    public <M extends RealmObject> RealmResults<M> getModels(@NonNull final Realm realm, @NonNull Class<M> modelClass) {
    //
    //        System.out.println(getClass().getName() + ": Current thread: " + Thread.currentThread().getName());
    //        System.out.println("Realm instance: " + realm);
    //        return realm.where(modelClass).findAll();
    //        //        Realm realm = Realm.getDefaultInstance();
    //        //        RealmResults<M> models = getModels(realm, modelClass);
    //        //        realm.close();
    //        //        return models;
    //    }
    //
    //    public <M extends RealmObject> RealmResults<M> getModelsAsync(@NonNull final Realm realm, @NonNull Class<M> modelClass) {
    //
    //        return realm.where(modelClass).findAllAsync();
    //    }
    //
    //    /**
    //     * Create all tables and save all customers we get from retrofit
    //     *
    //     * @param reservationList Object defining the count of tables
    //     * @param customers       pre-made list of available customers
    //     */
    //    public void initialTableSetup(@NonNull final TableReservationList reservationList,
    //                                  @NonNull final List<Customer> customers) {
    //
    //        List<Table> tables = reservationList.mAvailability.stream()
    //                .map(reserved -> new Table())
    //                .collect(Collectors.toList());
    //
    //        Realm realm = Realm.getDefaultInstance();
    //        saveOrUpdateModelList(realm, tables);
    //        saveOrUpdateModelList(realm, customers);
    //        realm.close();
    //    }
    //
    //    /**
    //     * Find a existing reservation on the table. If no reservations was found
    //     * a new one is created. Otherwise the reservation is updated
    //     *
    //     * @param table    where the reservation is happening
    //     * @param customer doing the reservation
    //     */
    //    public void createReservation(@NonNull final Table table, @NonNull final Customer customer) throws BusinessException {
    //
    //        Realm realm = Realm.getDefaultInstance();
    //
    //        Table managedTable = getManagedModel(realm, Table.class, table, Table.ID, table.getID());
    //        Customer managedCustomer = getManagedModel(realm, Customer.class, customer, Customer.ID, customer.getID());
    //
    //        RealmResults<Reservation> models = getModels(realm, Reservation.class);
    //        // Find existing reservation
    //        Reservation found = models.stream()
    //                .filter(reservation -> reservation.getTable().equals(managedTable))
    //                .findFirst()
    //                .orElse(null);
    //
    //        if (found == null) {
    //            // New reservation persisted
    //            persistObject(realm, new Reservation(managedTable, managedCustomer));
    //            realm.close();
    //        } else {
    //            realm.close();
    //            // Reservation already exist
    //            throw new BusinessException(BusinessError.RESERVATION_ALREADY_EXISTS,
    //                    String.format("Table %s already reserved by %s, %s",
    //                            managedTable.getID(), found.getCustomer().getLastName(),
    //                            found.getCustomer().getFirstName()));
    //        }
    //    }
    //
    //    /**
    //     * Removes a list of reservations
    //     *
    //     * @param reservations to remove
    //     */
    //    public void removeReservations(@NonNull List<Reservation> reservations) {
    //
    //        Realm realm = Realm.getDefaultInstance();
    //        List<String> reservationIDs = reservations.stream()
    //                .map(Reservation::getID)
    //                .collect(Collectors.toList());
    //        deleteModelsByID(realm, Reservation.class, Reservation.ID, reservationIDs);
    //        realm.close();
    //    }
    //
    //    ///////////////////////////////////////////////////////////////////////////////////////
    //    ///////////////////////////////////////////////////////////////////////////////////////
    //
    //    //    private <M extends RealmObject> RealmResults<M> getModels(final Realm realm,
    //    //                                                              Class<M> modelClass) {
    //    //
    //    //        return realm.where(modelClass).findAll();
    //    //    }
    //
    //    private <M extends RealmObject> void saveOrUpdateModelList(final Realm realm,
    //                                                               @NonNull List<M> modelList) {
    //
    //        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(modelList));
    //    }
    //
    //    private <M extends RealmObject> void persistObject(final Realm realm, M model) {
    //
    //        realm.executeTransaction(realm1 -> realm1.copyToRealm(model));
    //    }
    //
    //    private <M extends RealmObject> M getManagedModel(final Realm realm, final Class<M> modelClass, final M model,
    //                                                      final String field, final String value) throws BusinessException {
    //
    //        if (model.isManaged()) {
    //            return model;
    //        } else {
    //            RealmResults<M> models = getModelByField(realm, modelClass, field, value);
    //            M managedModel = models.stream()
    //                    .findAny()
    //                    .orElse(null);
    //
    //            if (managedModel == null) {
    //                throw new BusinessException(BusinessError.DATA_NOT_FOUND);
    //            } else {
    //                return managedModel;
    //            }
    //        }
    //    }
    //
    //    private <M extends RealmObject> RealmResults<M> getModelByField(final Realm realm, final Class<M> modelClass, final String field, final String value) {
    //
    //        return realm.where(modelClass).equalTo(field, value).findAll();
    //    }
    //
    //    private <M extends RealmObject> void deleteModelsByID(final Realm realm, final Class<M> modelClass,
    //                                                          final String field, final List<String> values) {
    //
    //        if (values.isEmpty()) {
    //            return;
    //        }
    //
    //        realm.executeTransaction(realm1 -> {
    //            RealmQuery<M> query = realm.where(modelClass).equalTo(field, values.get(0));
    //            if (values.size() > 1) {
    //                values.subList(1, values.size())
    //                        .forEach(value -> query.or().equalTo(field, value));
    //            }
    //            RealmResults<M> results = query.findAll();
    //            results.deleteAllFromRealm();
    //        });
    //    }
}
