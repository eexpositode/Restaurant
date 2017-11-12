package com.eexposito.restaurant.activities;

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
public class RestaurantActivity extends AppCompatActivity {

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

        mTableGridView.bind(mTablePresenter);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
