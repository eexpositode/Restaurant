package com.eexposito.restaurant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.views.CreateReservationView;
import com.eexposito.restaurant.views.CustomerListView;
import com.eexposito.restaurant.views.ToolbarView;

import javax.inject.Inject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import dagger.android.AndroidInjection;

@EActivity(R.layout.activity_reservations)
public class ReservationsActivity extends AppCompatActivity implements CustomerListView.OnCustomerActionCallback,
        ToolbarView.OnToolbarActionCallback, CreateReservationView.OnCreateReservationActionCallback {

    public static final String RESERVATIONS_RESULT = "result";

    @Inject
    CustomerPresenter mCustomerPresenter;

    @ViewById(R.id.reservations_dialog_view)
    View mDialogView;

    @ViewById(R.id.reservations_toolbar_header)
    ToolbarView mToolbarView;

    @ViewById(R.id.toolbar_cancel)
    View mCancel;

    @ViewById(R.id.toolbar_accept)
    View mAccept;

    @ViewById(R.id.reservations_customer_list)
    CustomerListView mCustomerListView;

    @ViewById(R.id.reservations_date_time_view)
    CreateReservationView mCreateReservationView;


    private String mSelectedTableID;
    private String mSelectedCustomerID;
    private String mSelectedReservationTime;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {

        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mSelectedTableID = intent.getStringExtra(RestaurantActivity.TABLE_ID);
        if (mSelectedTableID == null) {
            showError(getString(R.string.reservations_no_table_selected));
        }
    }

    @AfterViews
    public void init() {

        // Set up toolbar
        mToolbarView.updateTitle(R.string.reservations_create_reservation);
        mToolbarView.bind(this);

        showCreateReservationView();
    }

    private void showCreateReservationView() {

        mCustomerListView.setVisibility(View.INVISIBLE);
        mCreateReservationView.setVisibility(View.VISIBLE);
        mCreateReservationView.bind(this);
        mCreateReservationView.setValues(mSelectedTableID, mSelectedCustomerID, mSelectedReservationTime);
    }

    // TODO: 12/11/17 any animation here
    private void showCustomerList() {

        mCustomerListView.bind(mCustomerPresenter, this);
        mCustomerListView.setVisibility(View.VISIBLE);
        mCreateReservationView.setVisibility(View.INVISIBLE);
    }

    private void showError(final String errorMsg) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESERVATIONS_RESULT, errorMsg);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onSelectCustomerClicked() {

        showCustomerList();
    }

    @Override
    public void onTimePicked(final String time) {

        mSelectedReservationTime = time;
        showCreateReservationView();
    }

    @Override
    public void onCustomerClick(final String customerID) {

        mSelectedCustomerID = customerID;
        // TODO: 12/11/17 animation here
        showCreateReservationView();
    }

    @Override
    public void onAcceptClicked() {

        if (mSelectedCustomerID == null) {
            // TODO: 12/11/17 Show error dialog
        }
        if (mSelectedReservationTime == null) {
            // TODO: 12/11/17 Show error dialog
        }

    }

    @Override
    public void onCancelClicked() {

        // TODO: 12/11/17 Show are you sure dialog
    }
}
