package com.yogadimas.githubuser.network;

import com.yogadimas.githubuser.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static Retrofit sRetrofit;

    public static Retrofit getClient() {
        HttpLoggingInterceptor mHttpLoggingInterception = new HttpLoggingInterceptor();
        mHttpLoggingInterception.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mOkHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(mHttpLoggingInterception)
                .build();

        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }
        return sRetrofit;
    }
}
