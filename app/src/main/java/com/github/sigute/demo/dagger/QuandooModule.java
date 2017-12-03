package com.github.sigute.demo.dagger;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.github.sigute.demo.api.QuandooService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class QuandooModule {
    private Application application;

    public QuandooModule(@NonNull Application application) {
        this.application = application;
    }

    @Provides
    Context providesContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    QuandooService providesQuandooService(Retrofit retrofit) {
        return retrofit.create(QuandooService.class);
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://s3-eu-west-1.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
