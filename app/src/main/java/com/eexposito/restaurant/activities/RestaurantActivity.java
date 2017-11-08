package com.eexposito.restaurant.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.presenter.CustomerPresenterImpl;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class RestaurantActivity extends AppCompatActivity {

    @Inject
    CustomerPresenterImpl mCustomerPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
    }

    public void showCustomerList() {
        mCustomerPresenterImpl.loadData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
