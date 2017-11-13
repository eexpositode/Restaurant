package com.eexposito.restaurant.adapters;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.realm.models.Table;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;


public class TableGridAdapter extends RealmBaseAdapter<Table> {

    public TableGridAdapter(@Nullable final OrderedRealmCollection<Table> data) {

        super(data);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        TableViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.view_table_item, parent, false);
            viewHolder = new TableViewHolder();
            viewHolder.mNameTextView = convertView.findViewById(R.id.table_item_name);
            viewHolder.mCustomerNameTextView = convertView.findViewById(R.id.table_item_customer_name);
            viewHolder.mTimeTextView = convertView.findViewById(R.id.table_item_reservation_time);
            viewHolder.backgroundView = convertView.findViewById(R.id.table_item_bg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TableViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            final Table table = adapterData.get(position);
            setTableName(viewHolder, table);
            setCustomerName(viewHolder, table);
            setTime(viewHolder, table);
            setTableBg(viewHolder, table);
        }
        return convertView;
    }

    private void setTime(final TableViewHolder viewHolder, final Table table) {

        if (table.getReservation() == null) {
            viewHolder.mTimeTextView.setText("");
        } else {
            viewHolder.mTimeTextView.setText(table.getReservation().getDateTime());
        }
    }

    private void setCustomerName(final TableViewHolder viewHolder, final Table table) {

        if (table.getReservation() == null) {
            viewHolder.mCustomerNameTextView.setText("");
        } else {
            Customer customer = table.getReservation().getCustomer();
            viewHolder.mCustomerNameTextView.setText(customer.getLastName() + ", " + customer.getFirstName());
        }
    }

    private void setTableBg(final TableViewHolder viewHolder, final Table table) {

        View bgView = viewHolder.backgroundView;
        TextView nameView = viewHolder.mNameTextView;
        TextView customerNameView = viewHolder.mCustomerNameTextView;
        TextView timeView = viewHolder.mTimeTextView;
        if (table.getReservation() == null) {
            bgView.setBackgroundColor(ContextCompat.getColor(bgView.getContext(), R.color.white));
            nameView.setTextColor(ContextCompat.getColor(nameView.getContext(), R.color.colorAccent));
            customerNameView.setTextColor(ContextCompat.getColor(nameView.getContext(), R.color.colorAccent));
            timeView.setTextColor(ContextCompat.getColor(nameView.getContext(), R.color.colorAccent));
        } else {
            bgView.setBackgroundColor(ContextCompat.getColor(bgView.getContext(), R.color.colorAccent));
            nameView.setTextColor(ContextCompat.getColor(nameView.getContext(), R.color.white));
            customerNameView.setTextColor(ContextCompat.getColor(nameView.getContext(), R.color.white));
            timeView.setTextColor(ContextCompat.getColor(nameView.getContext(), R.color.white));
        }
    }

    private void setTableName(final TableViewHolder viewHolder, final Table table) {

        viewHolder.mNameTextView.setText("Table " + table.getOrder());
    }

    private class TableViewHolder {

        TextView mNameTextView;
        TextView mCustomerNameTextView;
        TextView mTimeTextView;
        View backgroundView;
    }
}
