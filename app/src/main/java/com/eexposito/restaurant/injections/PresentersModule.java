package com.eexposito.restaurant.injections;


import com.eexposito.restaurant.activities.ReservationsActivity;
import com.eexposito.restaurant.datasources.CustomerDataSource;
import com.eexposito.restaurant.datasources.ReservationsDataSource;
import com.eexposito.restaurant.datasources.TableDataSource;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.presenter.ReservationsPresenter;
import com.eexposito.restaurant.presenter.TablePresenter;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentersModule {

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

        return new CustomerPresenter(dataSource);
    }

    @Provides
    public TableDataSource provideTableDataSource(ModelManager modelManager,
                                                  ReservationsServiceApi api) {

        return new TableDataSource(modelManager, api);
    }

    @Provides
    public TablePresenter provideTablePresenter(TableDataSource dataSource) {

        return new TablePresenter(dataSource);
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

        return new ReservationsPresenter(dataSource);
    }
}
