package com.eexposito.restaurant.injections;


import com.eexposito.restaurant.activities.ReservationsActivity;
import com.eexposito.restaurant.datasources.CustomerDataSource;
import com.eexposito.restaurant.datasources.ReservationsDataSource;
import com.eexposito.restaurant.datasources.TableDataSource;
import com.eexposito.restaurant.presenter.CustomerPresenterImpl;
import com.eexposito.restaurant.presenter.ReservationsPresenterImpl;
import com.eexposito.restaurant.presenter.TablePresenterImpl;
import com.eexposito.restaurant.presenter.contracts.CustomerListContract;
import com.eexposito.restaurant.presenter.contracts.ReservationsContract;
import com.eexposito.restaurant.presenter.contracts.TableListContract;
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
    public CustomerListContract.CustomerPresenter provideCustomerPresenter(CustomerDataSource dataSource) {

        return new CustomerPresenterImpl(dataSource);
    }

    @Provides
    public TableDataSource provideTableDataSource(ModelManager modelManager,
                                                  ReservationsServiceApi api) {

        return new TableDataSource(modelManager, api);
    }

    @Provides
    public TableListContract.TablePresenter provideTablePresenter(TableDataSource dataSource) {

        return new TablePresenterImpl(dataSource);
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
    public ReservationsContract.ReservationsPresenter provideReservationsPresenter(ReservationsDataSource dataSource) {

        return new ReservationsPresenterImpl(dataSource);
    }
}
