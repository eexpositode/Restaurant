package com.eexposito.restaurant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.presenter.ReservationsPresenter;
import com.eexposito.restaurant.presenter.callbacks.ReservationsCallback;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.views.CreateReservationView;
import com.eexposito.restaurant.views.CustomerListView;
import com.eexposito.restaurant.views.ToolbarView;

import javax.inject.Inject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import dagger.android.AndroidInjection;

@EActivity(R.layout.activity_reservations)
public class ReservationsActivity extends AppCompatActivity implements
        ReservationsCallback,
        CustomerListView.OnCustomerActionCallback,
        ToolbarView.OnToolbarActionCallback, CreateReservationView.OnCreateReservationActionCallback {

    public static final String RESERVATIONS_RESULT = "result";

    @Inject
    ReservationsPresenter mReservationsPresenter;

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

    private Table mSelectedTable;
    private Customer mSelectedCustomer;
    private String mSelectedReservationTime;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {

        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String selectedTableID = intent.getStringExtra(RestaurantActivity.TABLE_ID);
        if (selectedTableID == null) {
            showError(getString(R.string.reservations_no_table_selected));
        }

        mReservationsPresenter.bind(this);
        mReservationsPresenter.getTableFromID(selectedTableID);
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
    }

    // TODO: 12/11/17 any animation here
    private void showCustomerList() {

        if (!mCustomerPresenter.isViewBound()) {
            mCustomerPresenter.bind(mCustomerListView);
        }
        mCustomerListView.bind(this);
        mCustomerListView.setVisibility(View.VISIBLE);
        mCreateReservationView.setVisibility(View.INVISIBLE);
    }

    private void showError(final String errorMsg) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESERVATIONS_RESULT, errorMsg);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    protected void onPause() {

        mCustomerPresenter.unBind();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        mCustomerPresenter.onDestroy();
        super.onDestroy();
    }

    /////////////////////////////////////////////////////////////////////////////////
    // Reservations presenter callbacks
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onFetchDataError(final Throwable e) {

    }

    @Override
    public void onFetchTableByID(@NonNull final Table table) {

        mSelectedTable = table;
        mCreateReservationView.setSelectedTable("Table " + table.getOrder());
    }

    @Override
    public void onFetchCustomerByID(@NonNull final Customer customer) {

        mSelectedCustomer = customer;
        mCreateReservationView.setSelectedCustomer(
                getString(R.string.customer_formatted_name,
                        String.valueOf(customer.getOrder() + 1),
                        customer.getLastName(),
                        customer.getFirstName()));
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
        mCreateReservationView.setSelectedTime(mSelectedReservationTime);
        showCreateReservationView();
    }

    @Override
    public void onCustomerClick(final String customerID) {

        mReservationsPresenter.getCustomerByID(customerID);

        showCreateReservationView();
    }

    @Override
    public void onAcceptClicked() {

        if (mSelectedCustomer == null) {
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
