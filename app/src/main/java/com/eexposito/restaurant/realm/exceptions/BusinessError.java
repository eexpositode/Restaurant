package com.eexposito.restaurant.realm.exceptions;

public enum BusinessError {

    //----------------------------------------------------------------------------------------------
    // READ errors
    //----------------------------------------------------------------------------------------------
    WRONG_INIT_DATA(1000),
    RESERVATION_ALREADY_EXISTS(1001),

    DATA_NOT_FOUND(2000);

    public final int errorCode;

    BusinessError(final int errorCode) {

        this.errorCode = errorCode;
    }
}
