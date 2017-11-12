package com.eexposito.restaurant.views;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.adapters.CustomerListAdapter;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.presenter.DataCallback;
import com.eexposito.restaurant.realm.models.Customer;

import javax.inject.Inject;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import io.realm.RealmResults;

@EViewGroup(R.layout.view_customer_list)
public class CustomerListView extends FrameLayout implements DataCallback<Customer> {

    @Inject
    CustomerPresenter mPresenter;

    @ViewById(R.id.customer_list_list_view)
    ListView mCustomerListView;

    private CustomerListAdapter mCustomerListAdapter;

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

    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    public void bind(@NonNull final CustomerPresenter presenter) {

        mPresenter = presenter;
        mPresenter.bind(this);
    }

    @Override
    protected void onDetachedFromWindow() {

        mPresenter.unBind();
        mPresenter = null;
        super.onDetachedFromWindow();
    }

    //////////////////////////////////////////////////////////////
    // Callback methods
    //////////////////////////////////////////////////////////////
    @Override
    public void onFetchDataStarted() {

    }

    @Override
    public void onFetchDataCompleted() {

    }

    @Override
    public void onFetchDataSuccess(final RealmResults<Customer> modelList) {
        // Update list
        if (mCustomerListAdapter == null) {
            mCustomerListAdapter = new CustomerListAdapter(modelList);
            mCustomerListView.setAdapter(mCustomerListAdapter);
        } else {
            mCustomerListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFetchDataError(final Throwable e) {

    }
}
