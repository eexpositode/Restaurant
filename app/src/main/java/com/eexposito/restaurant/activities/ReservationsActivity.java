package com.eexposito.restaurant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.views.CustomerListView;

import javax.inject.Inject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import dagger.android.AndroidInjection;

@EActivity(R.layout.activity_reservations)
public class ReservationsActivity extends AppCompatActivity implements CustomerListView.OnCustomerActionCallback {

    public static final String RESERVATIONS_RESULT = "result";

    @Inject
    CustomerPresenter mCustomerPresenter;

    @ViewById(R.id.reservations_dialog_view)
    View mDialogView;

    @ViewById(R.id.toolbar_text)
    TextView mToolbarTitle;

    @ViewById(R.id.toolbar_cancel)
    View mCancel;

    @ViewById(R.id.toolbar_accept)
    View mAccept;

    @ViewById(R.id.reservations_customer_list)
    CustomerListView mCustomerListView;

    @ViewById(R.id.reservations_date_time_view)
    View mDateTimeView;


    private String mSelectedTableID;
    private String mSelectedCustomerID;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {

        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mSelectedTableID = intent.getStringExtra(RestaurantActivity.TABLE_ID);
        if (mSelectedTableID == null) {
            showError("No table was selected or wrong table id was found.");
        }
    }

    @AfterViews
    public void init() {

        mCustomerListView.bind(mCustomerPresenter, this);
    }

    // TODO: 12/11/17 any animation here
    private void showDateTimePicker() {

        mCustomerListView.setVisibility(View.INVISIBLE);
        mDateTimeView.setVisibility(View.VISIBLE);
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
    public void onCustomerClick(final String customerID) {

        mSelectedCustomerID = customerID;
        showDateTimePicker();
    }
}
