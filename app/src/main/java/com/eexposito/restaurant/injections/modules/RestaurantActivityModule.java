package com.eexposito.restaurant.injections.modules;

import com.eexposito.restaurant.datasources.CustomerListDataSource;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.presenter.CustomerPresenterImpl;
import com.eexposito.restaurant.presenter.EasyCustomerPresenterImpl;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.RealmService;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;

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
     * @param dataSource
     * @return
     */
//    @Provides
//    public CustomerPresenter provideCustomerListPresenter(RealmService service,
//                                                          CustomerListDataSource dataSource) {
//
//        return new CustomerPresenterImpl(service, dataSource);
//    }

    @Provides
    public CustomerPresenter provideCustomerListPresenter(ReservationsServiceApi api) {

        return new EasyCustomerPresenterImpl(api);
    }
}
