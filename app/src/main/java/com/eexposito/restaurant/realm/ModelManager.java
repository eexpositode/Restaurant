package com.eexposito.restaurant.realm;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Model;
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

    public <M extends RealmObject> RealmResults<M> getModelByID(final Realm realm, final Class<M> modelClass, final String value) {

        return realm.where(modelClass).equalTo(Model.ID, value).findAll();
    }

    public void createReservation(@NonNull final String tableID, @NonNull final String customerID, @NonNull final String time) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {

            Table table = getModelByID(realm1, Table.class, tableID).first();
            Customer customer = getModelByID(realm1, Customer.class, customerID).first();
            Reservation reservation = realm1.copyToRealm(new Reservation(customer, time));
            table.setReservation(reservation);
            realm1.insertOrUpdate(table);
        });
        realm.close();
    }

    public void removeReservation(final Table table) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {

            RealmResults<Reservation> rows = getModelByID(
                    realm1,
                    Reservation.class,
                    table.getReservation().getID());
            rows.deleteAllFromRealm();
            table.setReservation(null);
            realm1.insertOrUpdate(table);
        });
        realm.close();
    }
}
