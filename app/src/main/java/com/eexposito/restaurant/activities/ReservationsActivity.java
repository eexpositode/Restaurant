package com.eexposito.restaurant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.presenter.ReservationsPresenter;
import com.eexposito.restaurant.presenter.callbacks.ReservationViewCallback;
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
        ReservationViewCallback,
        CustomerListView.OnCustomerActionCallback,
        ToolbarView.OnToolbarActionCallback,
        CreateReservationView.OnCreateReservationActionCallback {

    public static final String RESERVATIONS_TABLE_ID = "SELECTED_TABLE";
    public static final String RESERVATIONS_CUSTOMER_ID = "SELECTED_CUSTOMER";
    public static final String RESERVATIONS_TIME = "SELECTED_TIME";

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
            finishWithError(getString(R.string.reservations_no_table_selected));
            return;
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

    @Override
    protected void onPause() {

        mReservationsPresenter.unBind();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        mReservationsPresenter.onDestroy();
        super.onDestroy();
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

    private void finishWithError(final String errorMsg) {

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void showError(@NonNull final String error) {

        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
    /////////////////////////////////////////////////////////////////////////////////
    // Reservations presenter callbacks
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onFetchDataError(final Throwable e) {

        showError(getString(R.string.reservations_on_fetch_data_err, e.toString()));
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

    // TODO: 14/11/17 Dont do this
    @Override
    public void onDetachFromView() {

        mCustomerPresenter.unBind();
        mCustomerPresenter.onDestroy();
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
            showError(getString(R.string.reservations_select_customer_err));
            return;
        }
        if (mSelectedReservationTime == null) {
            showError(getString(R.string.reservations_select_time_err));
            return;
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESERVATIONS_TABLE_ID, mSelectedTable.getID());
        returnIntent.putExtra(RESERVATIONS_CUSTOMER_ID, mSelectedCustomer.getID());
        returnIntent.putExtra(RESERVATIONS_TIME, mSelectedReservationTime);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onCancelClicked() {

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
