package com.eexposito.restaurant.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.eexposito.restaurant.R;
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

    public interface OnTableActionCallback {

        void onTableClick(final String tableID);
    }

    @Inject
    TablePresenter mPresenter;

    @ViewById(R.id.table_grid_grid_view)
    GridView mTableGridView;

    private TableGridAdapter mTableGridAdapter;
    private OnTableActionCallback mCallback;

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

        mTableGridView.setOnItemClickListener((parent, view, position, id) -> {
            if (mCallback != null)
                mCallback.onTableClick(mTableGridAdapter.getItem(position).getID());
        });
    }

    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    public void bind(@NonNull final TablePresenter presenter, @NonNull final OnTableActionCallback callback) {

        mPresenter = presenter;
        mPresenter.bind(this);
        mCallback = callback;
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

        if (mTableGridAdapter == null) {
            mTableGridAdapter = new TableGridAdapter(modelList);
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
