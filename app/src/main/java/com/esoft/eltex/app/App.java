package com.esoft.eltex.app;

import android.app.Application;

import com.esoft.eltex.data.EltexApi;
import com.esoft.eltex.data.LoginRepositoryImp;
import com.esoft.eltex.data.PreferenceDataSource;
import com.esoft.eltex.domain.LoginRepository;
import com.google.gson.GsonBuilder;


import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static final String url = "http://smart.eltex-co.ru:8271/api/v1/";
    public LoginRepository loginRepository;
    public PreferenceDataSource preferenceDataSource;
    public Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationInterceptor(this))
                .addInterceptor(interceptor);


        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .client(client.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        loginRepository = new LoginRepositoryImp(retrofit.create(EltexApi.class));
        preferenceDataSource = new PreferenceDataSource(this);


    }
}
