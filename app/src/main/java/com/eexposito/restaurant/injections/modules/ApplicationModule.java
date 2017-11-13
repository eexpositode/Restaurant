package com.eexposito.restaurant.injections.modules;

import com.eexposito.restaurant.activities.ReservationsActivity;
import com.eexposito.restaurant.datasources.ReservationsDataSource;
import com.eexposito.restaurant.presenter.ReservationsPresenter;
import com.eexposito.restaurant.presenter.ReservationsPresenterImpl;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.realm.RealmService;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

//    private static final String BASE_URL = "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/";
    private static final String BASE_URL = "http://demo3083143.mockable.io/";

    @Provides
    Realm provideRealm() {

        return Realm.getDefaultInstance();
    }

    @Provides
    RealmService provideRealmService(final Realm realm) {

        return new RealmService(realm);
    }

    /**
     * RxSchedulerConfiguration
     */
    @Provides
    @Singleton
    public RxSchedulerConfiguration provideRxSchedulerConfiguration() {

        return new RxSchedulerConfiguration();
    }

    /**
     * ModelManager
     */
    @Provides
    public ModelManager provideModelManager() {

        return new ModelManager();
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

    /* OkHttpclient for retrofit2 */
    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {

        return new OkHttpClient.Builder().build();
    }

    /* retrofit2 */
    @Provides
    @Singleton
    public ReservationsServiceApi provideReservationsApiInterface(OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ReservationsServiceApi.class);
    }
}