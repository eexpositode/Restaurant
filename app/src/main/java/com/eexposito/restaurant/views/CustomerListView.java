package com.eexposito.restaurant.views;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.adapters.CustomerListAdapter;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.presenter.DataCallback;
import com.eexposito.restaurant.realm.models.Customer;

import javax.inject.Inject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import io.realm.RealmResults;

@EViewGroup(R.layout.view_customer_list)
public class CustomerListView extends FrameLayout implements DataCallback<Customer> {

    public interface OnCustomerActionCallback {

        void onCustomerClick(final String customerID);
    }

    @ViewById(R.id.customer_list_list_view)
    ListView mCustomerListView;

    private CustomerListAdapter mCustomerListAdapter;
    private OnCustomerActionCallback mCallback;

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

    @AfterViews
    public void init() {

        mCustomerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                if (mCallback != null)
                    mCallback.onCustomerClick(mCustomerListAdapter.getItem(position).getID());
            }
        });
    }
    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    public void bind(@NonNull final OnCustomerActionCallback callback) {

        mCallback = callback;
    }

    @Override
    protected void onDetachedFromWindow() {

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
