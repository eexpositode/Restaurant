package com.eexposito.restaurant.realm.exceptions;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BusinessException extends Exception {

    /**
     * The underlying error code for this exception
     */
    @Nonnull
    public final BusinessError businessError;

    @Nullable
    private final Throwable reason;
    /**
     * Display args for this exception
     */
    @Nullable
    public final String[] params;

    public BusinessException(@Nonnull final BusinessError businessError) {

        this(businessError, (String[]) null);
    }

    public BusinessException(@Nonnull final BusinessError businessError, Throwable reason) {

        this(businessError, reason, (String[]) null);
    }

    public BusinessException(@Nonnull final BusinessError businessError, @Nullable final String... params) {

        this(businessError, null, params);
    }

    public BusinessException(@Nonnull final BusinessError businessError, Throwable reason, @Nullable final String... params) {

        this.businessError = businessError;
        this.reason = reason;
        this.params = params;
    }

    @Override
    public String toString() {

        return "BusinessException{" +
                "businessError=" + businessError +
                ", reason=" + reason +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
