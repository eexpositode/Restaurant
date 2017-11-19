package com.eexposito.restaurant.injections;

import com.eexposito.restaurant.MainApplication;
import com.eexposito.restaurant.realm.ModelManager;
import com.eexposito.restaurant.retrofit.ReservationsServiceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {PresentersModule.class, ServiceModule.class})
public class ApplicationModule {

    private static final String BASE_URL = "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/";
//    private static final String BASE_URL = "http://demo3083143.mockable.io/";

    private final MainApplication mApplication;

    public ApplicationModule(MainApplication app) {

        mApplication = app;
    }

    @Provides
    MainApplication provideApplication() {

        return mApplication;
    }

    /**
     * ModelManager
     */
    @Provides
    @Singleton
    public ModelManager provideModelManager() {

        return new ModelManager();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {

        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    public ReservationsServiceApi provideRetrofit(OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ReservationsServiceApi.class);
    }
}