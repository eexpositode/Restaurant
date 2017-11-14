package com.eexposito.restaurant.retrofit;


import com.eexposito.restaurant.realm.models.Customer;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ReservationsServiceApi {

    String CUSTOMER_JSON_URL = "customer-list.json";
    String TABLE_JSON_URL = "table-map.json";

    @GET(CUSTOMER_JSON_URL)
    Observable<List<Customer>> getCustomers();

    @GET(TABLE_JSON_URL)
    Observable<List<Boolean>> getTableAvailability();
}