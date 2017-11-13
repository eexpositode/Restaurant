package com.eexposito.restaurant.injections.modules;

import com.eexposito.restaurant.activities.ReservationsActivity;
import com.eexposito.restaurant.datasources.CustomerDataSource;
import com.eexposito.restaurant.datasources.ReservationsDataSource;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.presenter.CustomerPresenterImpl;
import com.eexposito.restaurant.presenter.ReservationsPresenter;
import com.eexposito.restaurant.presenter.ReservationsPresenterImpl;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;

import dagger.Module;
import dagger.Provides;

@Module
public class ReservationsActivityModule {

    @Provides
    public CustomerDataSource provideCustomerDataSource(ModelManager modelManager,
                                                        ReservationsServiceApi api) {

        return new CustomerDataSource(modelManager, api);
    }

    /**
     * Presenter for CustomerList
     *
     * @param dataSource
     * @return
     */
    @Provides
    public CustomerPresenter provideCustomerPresenter(CustomerDataSource dataSource) {

        return new CustomerPresenterImpl(dataSource);
    }

    @Provides
    public ReservationsDataSource provideReservationsDataSource(ModelManager modelManager) {

        return new ReservationsDataSource(modelManager);
    }

    /**
     * Presenter for {@link ReservationsActivity}
     *
     * @param dataSource
     * @return
     */
    @Provides
    public ReservationsPresenter provideReservationsPresenter(ReservationsDataSource dataSource) {

        return new ReservationsPresenterImpl(dataSource);
    }
}
