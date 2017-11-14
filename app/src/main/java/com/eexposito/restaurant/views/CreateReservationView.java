package com.eexposito.restaurant.views;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eexposito.restaurant.R;

import java.util.Calendar;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_create_reservation)
public class CreateReservationView extends FrameLayout {

    public interface OnCreateReservationActionCallback {

        void onSelectCustomerClicked();

        void onTimePicked(final String time);
    }

    @ViewById(R.id.selected_table_name)
    TextView mSelectedTable;

    @ViewById(R.id.selected_customer_name)
    TextView mSelectedCustomer;

    @ViewById(R.id.selected_time)
    TextView mSelectedTime;

    private OnCreateReservationActionCallback mCallback;

    public CreateReservationView(@NonNull final Context context) {

        super(context);
    }

    public CreateReservationView(@NonNull final Context context, @Nullable final AttributeSet attrs) {

        super(context, attrs);
    }

    public CreateReservationView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    public CreateReservationView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(@NonNull OnCreateReservationActionCallback callback) {

        mCallback = callback;
    }

    public void setSelectedTable(@NonNull String selectedTable) {

        mSelectedTable.setText(selectedTable);
    }

    public void setSelectedCustomer(@NonNull String selectedCustomer) {

        mSelectedCustomer.setText(selectedCustomer);
    }

    public void setSelectedTime(@Nullable final String selectedDateTime) {

        mSelectedTime.setText(selectedDateTime);
    }

    @Click(R.id.create_reservation_select_customer)
    public void onSelectCustomerClicked() {

        if (mCallback != null) {
            mCallback.onSelectCustomerClicked();
        }
    }

    @Click(R.id.create_reservation_select_time)
    public void onSelectTimeClicked() {

        showTimePickerDialog();
    }

    private void showTimePickerDialog() {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view, hourOfDay, minute1) -> {
                    if (mCallback != null) {
                        String reservationTime = String.format("%s : %s", hourOfDay, minute1);
                        mCallback.onTimePicked(reservationTime);
                    }
                }, hour, minute, false);

        timePickerDialog.show();
    }
}
