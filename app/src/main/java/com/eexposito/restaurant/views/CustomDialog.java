package com.eexposito.restaurant.views;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

public class CustomDialog {

    public static final String YES_BTN = "Ok";
    public static final String NO_BTN = "Cancel";
    private static ProgressDialog mProgressDialog;

    public static void showAlertDialog(@NonNull final Context context,
                                       @NonNull final String msg,
                                       @NonNull final DialogInterface.OnClickListener
                                               onOkClickListener) {

        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(
                        YES_BTN,
                        onOkClickListener)
                .create()
                .show();
    }

    public static void showAlertDialog(@NonNull final Context context,
                                       @NonNull final String msg,
                                       @NonNull final DialogInterface.OnClickListener
                                               onOkClickListener,
                                       @NonNull final DialogInterface.OnClickListener
                                               onCancelListener) {

        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(YES_BTN, onOkClickListener)
                .setNegativeButton(NO_BTN, onCancelListener)
                .create()
                .show();
    }

    public static void showProgressDialog(@NonNull final Context context,
                                          @NonNull String title,
                                          @NonNull String msg) {


        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
            }
            mProgressDialog = null;
        }

        mProgressDialog = ProgressDialog.show(context, title, msg,
                true, true,
                dialog -> showProgressDialog(context, title, msg));
        mProgressDialog.show();
    }

    public static void hideProgressDialog() {

        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = null;
        }

    }
}
