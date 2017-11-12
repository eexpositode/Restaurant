package com.eexposito.restaurant.views;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.realm.models.Customer;

import java.util.List;

import javax.inject.Inject;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import dagger.android.AndroidInjection;

@EViewGroup(R.layout.view_customer_list)
public class CustomerListView extends FrameLayout implements Bindable<Customer> {

    @ViewById(R.id.customer_list_list_view)
    ListView mCustomerListView;

    CustomerPresenter mCustomerPresenter;

    public CustomerListView(@NonNull final Context context) {

        super(context);
    }

    public CustomerListView(@NonNull final Context context, @Nullable final AttributeSet attrs) {

        super(context, attrs);
    }

    public CustomerListView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    public CustomerListView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(final CustomerPresenter customerPresenter) {

        mCustomerPresenter = customerPresenter;
        mCustomerPresenter.bind(this);
    }

    @Override
    protected void onDetachedFromWindow() {

        mCustomerPresenter.unBind();
        mCustomerPresenter = null;
        super.onDetachedFromWindow();
    }

    //////////////////////////////////////////////////////////////
    // Callback methods
    //////////////////////////////////////////////////////////////
    @Override
    public void onFetchDataStarted() {
        // Show progress dialog
        Toast.makeText(getContext(), "Fetch data started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchDataCompleted() {
        // Hide progres dialog
        Toast.makeText(getContext(), "Fetch data completed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchDataSuccess(final List<Customer> modelList) {
        // Update list
        Toast.makeText(getContext(), "List found with size " + modelList.size(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchDataError(final Throwable e) {
        // Show error dialog
        Toast.makeText(getContext(), "Fetch data error: " + e, Toast.LENGTH_LONG).show();
    }
}
