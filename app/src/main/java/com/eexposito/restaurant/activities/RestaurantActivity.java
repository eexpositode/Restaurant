package com.eexposito.restaurant.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.presenter.contracts.ReservationsContract;
import com.eexposito.restaurant.presenter.contracts.TableListContract;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.views.CustomDialog;
import com.eexposito.restaurant.views.TableGridView;

import javax.inject.Inject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import dagger.android.AndroidInjection;

@SuppressLint("Registered")
@EActivity(R.layout.activity_restaurant)
public class RestaurantActivity extends AppCompatActivity implements TableGridView.OnTableActionCallback {

    public static final String TABLE_ID = "TABLE_ID";
    public static final int CREATE_RESERVATION_REQ_CODE = 1001;

    @Inject
    TableListContract.TablePresenter mTablePresenter;

    @Inject
    ReservationsContract.ReservationsPresenter mReservationsPresenter;

    @ViewById(R.id.restaurant_table_grid)
    TableGridView mTableGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void afterViews() {

        mTableGridView.bind(this);
        mTablePresenter.bind(mTableGridView);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_RESERVATION_REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                createReservation(data.getStringExtra(ReservationsActivity.RESERVATIONS_TABLE_ID),
                        data.getStringExtra(ReservationsActivity.RESERVATIONS_CUSTOMER_ID),
                        data.getStringExtra(ReservationsActivity.RESERVATIONS_TIME));
            } else {
                String error = data.getStringExtra(ReservationsActivity.RESERVATIONS_ERR_MSG);
                if (error != null) {
                    CustomDialog.showAlertDialog(this,
                            error,
                            (dialog, which) -> dialog.dismiss());
                }
            }
        }
    }

    private void createReservation(final String tableID, final String customerID, final String time) {

        mReservationsPresenter.createReservation(tableID, customerID, time);
    }

    @Override
    protected void onPause() {

        mTablePresenter.unBind();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        mTablePresenter.clear();
        super.onDestroy();
    }

    @Override
    public void onTableClick(final Table table) {

        if (table.getReservation() == null) {
            Intent intent = new Intent(this, ReservationsActivity_.class);
            intent.putExtra(TABLE_ID, table.getID());
            startActivityForResult(intent, CREATE_RESERVATION_REQ_CODE);
        } else {
            CustomDialog.showAlertDialog(this,
                    getString(R.string.restaurant_remove_reservation),
                    (dialog, which) -> mReservationsPresenter.removeReservation(table),
                    (dialog, which) -> dialog.dismiss());
        }
    }
}
