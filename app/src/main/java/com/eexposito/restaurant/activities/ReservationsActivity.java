package com.eexposito.restaurant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.presenter.TablePresenter;
import com.eexposito.restaurant.views.CustomerListView;
import com.eexposito.restaurant.views.TableGridView;

import javax.inject.Inject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import dagger.android.AndroidInjection;

@EActivity(R.layout.activity_reservations)
public class ReservationsActivity extends AppCompatActivity {

    @Inject
    CustomerPresenter mCustomerPresenter;

    @ViewById(R.id.reservations_customer_list)
    CustomerListView mCustomerListView;

    public static final String RESERVATIONS_RESULT = "result";

    private String mTableID;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {

        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mTableID = intent.getStringExtra(TableGridView.TABLE_ID);
        if (mTableID == null) {
            showError("No table was selected or wrong table id was found.");
        }
    }

    @AfterViews
    public void init() {

        mCustomerListView.bind(mCustomerPresenter);
    }

    private void showError(final String errorMsg) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESERVATIONS_RESULT, errorMsg);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

}
