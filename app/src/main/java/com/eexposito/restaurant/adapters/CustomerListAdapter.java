package com.eexposito.restaurant.adapters;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.realm.models.Customer;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class CustomerListAdapter extends RealmBaseAdapter<Customer> implements ListAdapter {


    public CustomerListAdapter(@Nullable final OrderedRealmCollection<Customer> data) {

        super(data);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        CustomerViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.view_customer_item, parent, false);
            viewHolder = new CustomerViewHolder();
            viewHolder.mNameTextView = convertView.findViewById(R.id.customer_item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomerViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            final Customer customer = adapterData.get(position);
            setCustomerName(viewHolder, customer);
        }
        return convertView;
    }

    private void setCustomerName(final CustomerViewHolder viewHolder, final Customer customer) {

        String formattedName =
                viewHolder.mNameTextView.getContext().getString(R.string.customer_formatted_name, String.valueOf(customer.getOrder() + 1), customer.getLastName(), customer.getFirstName());
        viewHolder.mNameTextView.setText(formattedName);
    }

    private class CustomerViewHolder {

        TextView mNameTextView;
    }
}
