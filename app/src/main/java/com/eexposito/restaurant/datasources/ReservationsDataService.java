package com.eexposito.restaurant.datasources;


import android.support.annotation.NonNull;

import com.eexposito.restaurant.MainApplication;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.exceptions.BusinessError;
import com.eexposito.restaurant.realm.exceptions.BusinessException;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Reservation;
import com.eexposito.restaurant.realm.models.Table;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ReservationsDataService {

    @NonNull
    ModelManager mModelManager;

    public ReservationsDataService(@NonNull MainApplication application,
                                   @NonNull final ModelManager modelManager) {

        application.getApplicationComponent().inject(this);
        mModelManager = modelManager;
    }

    public Observable<List<Reservation>> getReservations(final boolean isForced) {

        return Observable.empty();
    }

    public Observable<RealmResults<Table>> getTableByID(final Realm realm, final String id) {

        return Observable.just(mModelManager.getModelByID(realm, Table.class, id));
    }

    public Observable<RealmResults<Customer>> getCustomerByID(final Realm realm, final String id) {

        return Observable.just(mModelManager.getModelByID(realm, Customer.class, id));
    }

    public <M extends RealmObject> M checkValidSingleData(final RealmResults<M> models)
            throws BusinessException {

        if (models.isEmpty()) {
            throw new BusinessException(BusinessError.DATA_NOT_FOUND);
        }
        if (models.size() > 1) {
            throw new BusinessException(BusinessError.UNEXPECTED_DATA_FOUND);
        }
        M found = models.first();
        if (found == null) {
            throw new BusinessException(BusinessError.DATA_NOT_FOUND);
        }
        return found;
    }

    public void createReservation(@NonNull final String tableID, @NonNull final String
            customerID, @NonNull final String time) {

        mModelManager.createReservation(tableID, customerID, time);
    }

    public void removeReservation(final Table table) {

        mModelManager.removeReservation(table);
    }
}
