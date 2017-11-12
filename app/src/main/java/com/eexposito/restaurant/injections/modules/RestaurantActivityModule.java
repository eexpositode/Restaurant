package com.eexposito.restaurant.injections.modules;

import com.eexposito.restaurant.datasources.CustomerDataSource;
import com.eexposito.restaurant.datasources.TableDataSource;
import com.eexposito.restaurant.presenter.CustomerPresenter;
import com.eexposito.restaurant.presenter.CustomerPresenterImpl;
import com.eexposito.restaurant.presenter.TablePresenter;
import com.eexposito.restaurant.presenter.TablePresenterImpl;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;

import dagger.Module;
import dagger.Provides;

@Module
public class RestaurantActivityModule {

    @Provides
    public TableDataSource provideTableDataSource(ModelManager modelManager,
                                                  ReservationsServiceApi api) {

        return new TableDataSource(modelManager, api);
    }

    @Provides
    public TablePresenter provideTablePresenter(TableDataSource dataSource) {

        return new TablePresenterImpl(dataSource);
    }
}
