package com.eexposito.restaurant.injections;

import com.eexposito.restaurant.MainApplication;
import com.eexposito.restaurant.datasources.CustomerDataService;
import com.eexposito.restaurant.datasources.ReservationsDataService;
import com.eexposito.restaurant.datasources.TableDataService;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.RealmFactory;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    RealmFactory provideRealmFactory() {

        return new RealmFactory();
    }

    @Provides
    @Singleton
    public CustomerDataService provideCustomerDataService(MainApplication application,
                                                          ModelManager modelManager,
                                                          ReservationsServiceApi api) {

        return new CustomerDataService(application, modelManager, api);
    }


    @Provides
    @Singleton
    public TableDataService provideTableDataService(MainApplication application,
                                                    ModelManager modelManager,
                                                    ReservationsServiceApi api) {

        return new TableDataService(application, modelManager, api);
    }

    @Provides
    @Singleton
    public ReservationsDataService provideReservationsDataService(MainApplication application,
                                                                  ModelManager modelManager) {

        return new ReservationsDataService(application, modelManager);
    }

    @Component(modules = ServiceModule.class)
    interface ServiceComponent {

        void inject(CustomerDataService service);

        void inject(TableDataService service);

        void inject(ReservationsDataService service);
    }

}
