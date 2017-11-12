package com.eexposito.restaurant.adapters;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eexposito.restaurant.R;
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
            viewHolder.backgroundView = convertView.findViewById(R.id.table_item_bg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TableViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            final Table table = adapterData.get(position);
            setTableName(viewHolder, table);
            setTableBg(viewHolder, table);
        }
        return convertView;
    }

    private void setTableBg(final TableViewHolder viewHolder, final Table table) {

        View bgView = viewHolder.backgroundView;
        TextView textView = viewHolder.mNameTextView;
        if (table.getReservation() == null) {
            bgView.setBackgroundColor(ContextCompat.getColor(bgView.getContext(), R.color.white));
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.colorAccent));
        } else {
            bgView.setBackgroundColor(ContextCompat.getColor(bgView.getContext(), R.color.colorAccent));
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.white));
        }
    }

    private void setTableName(final TableViewHolder viewHolder, final Table table) {

        viewHolder.mNameTextView.setText("Table " + table.getOrder());
    }

    private class TableViewHolder {

        TextView mNameTextView;
        View backgroundView;
    }
}
