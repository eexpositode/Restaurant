package com.eexposito.restaurant.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.activities.ReservationsActivity_;
import com.eexposito.restaurant.adapters.TableGridAdapter;
import com.eexposito.restaurant.presenter.DataCallback;
import com.eexposito.restaurant.presenter.TablePresenter;
import com.eexposito.restaurant.realm.models.Table;

import javax.inject.Inject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import io.realm.RealmResults;

@EViewGroup(R.layout.view_table_grid)
public class TableGridView extends FrameLayout implements DataCallback<Table> {

    public static final String TABLE_ID = "TABLE_ID";

    @Inject
    TablePresenter mPresenter;

    @ViewById(R.id.table_grid_grid_view)
    GridView mTableGridView;

    private RealmResults<Table> mCurrentModelList;
    private TableGridAdapter mTableGridAdapter;

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

    @AfterViews
    public void init() {

        mTableGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {

                Intent intent = new Intent(getContext(), ReservationsActivity_.class);
                //                mTableGridAdapter.getItem(position).getID()
                String tableId = mCurrentModelList.get(position).getID();
                intent.putExtra(TABLE_ID, tableId);
                getContext().startActivity(intent);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    public void bind(@NonNull final TablePresenter presenter) {

        mPresenter = presenter;
        mPresenter.bind(this);
    }


    @Override
    protected void onDetachedFromWindow() {

        mPresenter.unBind();
        mPresenter = null;
        super.onDetachedFromWindow();
    }

    @Override
    public void onFetchDataStarted() {
        // Show progress dialog
        Toast.makeText(getContext(), "Fetch data started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchDataCompleted() {
        // Hide progress dialog
        Toast.makeText(getContext(), "Fetch data completed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFetchDataSuccess(final RealmResults<Table> modelList) {

        Toast.makeText(getContext(), "List found with size " + modelList.size(), Toast.LENGTH_LONG).show();

        mCurrentModelList = modelList;
        if (mTableGridAdapter == null) {
            mTableGridAdapter = new TableGridAdapter(mCurrentModelList);
            mTableGridView.setAdapter(mTableGridAdapter);
        } else {
            mTableGridAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFetchDataError(final Throwable e) {
        // Show error dialog
        Toast.makeText(getContext(), "Fetch data error: " + e, Toast.LENGTH_LONG).show();
    }
}
