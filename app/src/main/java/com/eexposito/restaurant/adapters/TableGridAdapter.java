package com.eexposito.restaurant.adapters;

import android.support.annotation.Nullable;
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TableViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            final Table table = adapterData.get(position);
            viewHolder.mNameTextView.setText("Table");
        }
        return convertView;
    }

    private class TableViewHolder {

        TextView mNameTextView;
    }
}
