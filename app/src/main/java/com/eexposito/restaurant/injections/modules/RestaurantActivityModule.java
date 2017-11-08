package com.eexposito.restaurant.injections.modules;

import com.eexposito.restaurant.datasources.CustomerListDataSource;
import com.eexposito.restaurant.presenter.CustomerPresenterImpl;
import com.eexposito.restaurant.realm.RealmService;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import dagger.Module;
import dagger.Provides;

@Module
public class RestaurantActivityModule {

    @Provides
    public CustomerListDataSource provideCustomerListDataSource(ModelManager modelManager,
                                                         ReservationsServiceApi api) {

        return new CustomerListDataSource(modelManager, api);
    }

    /**
     * Presenter for CustomerList
     *
     * @param rxSchedulerConfiguration
     * @param dataSource
     * @return
     */
    @Provides
    public CustomerPresenterImpl provideCustomerListPresenter(RealmService service,
                                                              RxSchedulerConfiguration rxSchedulerConfiguration,
                                                              CustomerListDataSource dataSource) {

        return new CustomerPresenterImpl(service, rxSchedulerConfiguration, dataSource);
    }
}
