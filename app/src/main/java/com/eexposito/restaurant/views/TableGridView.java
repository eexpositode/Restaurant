package com.eexposito.restaurant.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.realm.models.Reservation;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import io.realm.RealmResults;

@EViewGroup(R.layout.view_table_grid)
public class TableGridView extends FrameLayout implements Bindable<Reservation> {

    @ViewById(R.id.table_grid_grid_view)
    GridView mTableGridView;

    public TableGridView(@NonNull final Context context) {

        super(context);
    }

    public TableGridView(@NonNull final Context context, @Nullable final AttributeSet attrs) {

        super(context, attrs);
    }

    public TableGridView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    public TableGridView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
    }

    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////
    @Override
    public void onFetchDataStarted() {

    }

    @Override
    public void onFetchDataCompleted() {

    }

    @Override
    public void onFetchDataSuccess(final RealmResults<Reservation> modelList) {

    }

    @Override
    public void onFetchDataError(final Throwable e) {

    }
}
