package com.eexposito.restaurant.injections;


import com.eexposito.restaurant.datasources.CustomerDataService;
import com.eexposito.restaurant.datasources.ReservationsDataService;
import com.eexposito.restaurant.datasources.TableDataService;
import com.eexposito.restaurant.presenter.CustomerPresenterImpl;
import com.eexposito.restaurant.presenter.ReservationsPresenterImpl;
import com.eexposito.restaurant.presenter.TablePresenterImpl;
import com.eexposito.restaurant.presenter.contracts.CustomerListContract;
import com.eexposito.restaurant.presenter.contracts.ReservationsContract;
import com.eexposito.restaurant.presenter.contracts.TableListContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentersModule {

    @Provides
    @Singleton
    public CustomerListContract.CustomerPresenter provideCustomerPresenter(CustomerDataService dataSource) {

        return new CustomerPresenterImpl(dataSource);
    }

    @Provides
    @Singleton
    public TableListContract.TablePresenter provideTablePresenter(TableDataService dataSource) {

        return new TablePresenterImpl(dataSource);
    }

    @Provides
    @Singleton
    public ReservationsContract.ReservationsPresenter provideReservationsPresenter(ReservationsDataService dataSource) {

        return new ReservationsPresenterImpl(dataSource);
    }
}
