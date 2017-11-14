package com.eexposito.restaurant.injections;

import com.eexposito.restaurant.realm.ModelManager;
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

    private static final String BASE_URL = "http://demo3083143.mockable.io/";

    @Provides
    Realm provideRealm() {

        return Realm.getDefaultInstance();
    }

    /**
     * ModelManager
     */
    @Provides
    public ModelManager provideModelManager() {

        return new ModelManager();
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