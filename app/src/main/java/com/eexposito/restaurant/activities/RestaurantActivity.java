package com.eexposito.restaurant.activities;

import android.content.Intent;
import android.os.Bundle;
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

@EActivity(R.layout.activity_restaurant)
public class RestaurantActivity extends AppCompatActivity implements TableGridView.OnTableActionCallback {

    public static final String TABLE_ID = "TABLE_ID";
    public static final int CREATE_RESERVATION_REQ_CODE = 1001;

    @Inject
    TablePresenter mTablePresenter;

    @ViewById(R.id.restaurant_table_grid)
    TableGridView mTableGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void afterViews() {

        mTableGridView.bind(mTablePresenter, this);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onTableClick(final String tableID) {

        Intent intent = new Intent(this, ReservationsActivity_.class);
        intent.putExtra(TABLE_ID, tableID);
        startActivityForResult(intent, CREATE_RESERVATION_REQ_CODE);
    }
}
