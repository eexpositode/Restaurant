package com.eexposito.restaurant.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.presenter.CustomerPresenterImpl;
import com.eexposito.restaurant.presenter.ReservationsPresenterImpl;
import com.eexposito.restaurant.presenter.contracts.ReservationsContract;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Table;
import com.eexposito.restaurant.views.CreateReservationView;
import com.eexposito.restaurant.views.CustomerListView;
import com.eexposito.restaurant.views.ToolbarView;
import com.eexposito.restaurant.visitors.PrintModelVisitor;

import javax.inject.Inject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import dagger.android.AndroidInjection;

@SuppressLint("Registered")
@EActivity(R.layout.activity_reservations)
public class ReservationsActivity extends AppCompatActivity implements
        ReservationsContract.View,
        CustomerListView.OnCustomerActionCallback,
        ToolbarView.OnToolbarActionCallback,
        CreateReservationView.OnCreateReservationActionCallback {

    public static final String RESERVATIONS_TABLE_ID = "SELECTED_TABLE";
    public static final String RESERVATIONS_CUSTOMER_ID = "SELECTED_CUSTOMER";
    public static final String RESERVATIONS_TIME = "SELECTED_TIME";
    public static final String RESERVATIONS_ERR_MSG = "ERROR_MSG";

    @Inject
    ReservationsPresenterImpl mReservationsPresenterImpl;

    @Inject
    CustomerPresenterImpl mCustomerPresenterImpl;

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

        mReservationsPresenterImpl.bind(this);
        mReservationsPresenterImpl.getTableFromID(selectedTableID);
    }

    @AfterViews
    public void init() {

        // Set up toolbar
        mToolbarView.bind(this);

        showCreateReservationView();
    }

    @Override
    protected void onPause() {

        mReservationsPresenterImpl.unBind();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        mReservationsPresenterImpl.clear();
        super.onDestroy();
    }

    private void showCreateReservationView() {

        mCustomerListView.setVisibility(View.INVISIBLE);
        mCreateReservationView.setVisibility(View.VISIBLE);
        mCreateReservationView.bind(this);
    }

    // TODO: 12/11/17 any animation here
    private void showCustomerList() {

        if (!mCustomerPresenterImpl.isViewBound()) {
            mCustomerPresenterImpl.bind(mCustomerListView);
        }
        mCustomerListView.bind(this);
        mCustomerListView.setVisibility(View.VISIBLE);
        mCreateReservationView.setVisibility(View.INVISIBLE);
    }

    private void finishWithError(final String errorMsg) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESERVATIONS_ERR_MSG, errorMsg);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void showError(@NonNull final String error) {

        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
    /////////////////////////////////////////////////////////////////////////////////
    // Reservations presenter view
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onFetchDataError(final Throwable e) {

        showError(getString(R.string.reservations_on_fetch_data_err, e.toString()));
    }

    @Override
    public void onFetchTableByID(@NonNull final Table table) {

        mSelectedTable = table;
        PrintModelVisitor visitor = new PrintModelVisitor();
        mSelectedTable.accept(visitor);
        mCreateReservationView.setSelectedTable(visitor.getModelToString());
    }

    @Override
    public void onFetchCustomerByID(@NonNull final Customer customer) {

        mSelectedCustomer = customer;
        PrintModelVisitor visitor = new PrintModelVisitor();
        mSelectedCustomer.accept(visitor);
        mCreateReservationView.setSelectedCustomer(visitor.getModelToString());
    }

    /////////////////////////////////////////////////////////////////////////////////
    // CreateReservationView
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

    /////////////////////////////////////////////////////////////////////////////////
    // CustomerListView
    /////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCustomerClick(final String customerID) {

        mReservationsPresenterImpl.getCustomerByID(customerID);

        showCreateReservationView();
    }

    @Override
    public void onCustomerViewDetachedFromWindow() {

        mCustomerPresenterImpl.unBind();
        mCustomerPresenterImpl.clear();
    }

    /////////////////////////////////////////////////////////////////////////////////
    // Toolbar
    /////////////////////////////////////////////////////////////////////////////////
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
