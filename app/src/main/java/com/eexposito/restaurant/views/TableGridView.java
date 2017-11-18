package com.eexposito.restaurant.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.eexposito.restaurant.R;
import com.eexposito.restaurant.adapters.TableGridAdapter;
import com.eexposito.restaurant.presenter.contracts.TableListContract;
import com.eexposito.restaurant.realm.models.Table;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import io.realm.RealmResults;

@EViewGroup(R.layout.view_table_grid)
public class TableGridView extends FrameLayout implements TableListContract.View {

    public interface OnTableActionCallback {

        void onTableClick(final Table table);
    }

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

    public TableGridView(@NonNull final Context context, @Nullable final AttributeSet attrs,
                         final int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    public TableGridView(@NonNull final Context context, @Nullable final AttributeSet attrs,
                         final int defStyleAttr, final int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @AfterViews
    public void init() {

        mTableGridView.setOnItemClickListener((parent, view, position, id) -> {
            if (mCallback != null) {
                mCallback.onTableClick(mTableGridAdapter.getItem(position));
            }
        });
    }

    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    public void bind(@NonNull final OnTableActionCallback callback) {

        mCallback = callback;
    }

    @Override
    public void onFetchDataStarted() {

        CustomDialog.showProgressDialog(getContext(), getContext().getString(R.string.restaurant_fetching_tables), "");
    }

    @Override
    public void onFetchDataCompleted() {

        CustomDialog.hideProgressDialog();
    }

    @Override
    public void onFetchDataSuccess(final RealmResults<Table> modelList) {

        if (mTableGridAdapter == null) {
            mTableGridAdapter = new TableGridAdapter(modelList);
            mTableGridView.setAdapter(mTableGridAdapter);
        } else {
            mTableGridAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFetchDataError(final Throwable e) {

        CustomDialog.showAlertDialog(getContext(),
                getContext().getString(R.string.fetch_data_err, e.getMessage()),
                (dialog, which) -> dialog.dismiss());
    }
}
