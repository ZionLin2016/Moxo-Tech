package com.closedevice.fastapp.api.remote;


import com.closedevice.fastapp.AppConstant;
import com.closedevice.fastapp.api.ClientFactory;
import com.closedevice.fastapp.api.convert.gan.GanGsonConverterFactory;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public enum ApiFactory {
    INSTANCE;

    private static FirApi firApi;
    private static BKApi bkApi;
    private static UserApi userApi;

    ApiFactory() {
    }

    public static FirApi getFirApi() {
        if (firApi == null) {
            ApiFactory.firApi = createApi(AppConstant.API_FIR_URL, FirApi.class, GsonConverterFactory.create());
        }
        return firApi;
    }

    public static BKApi getsBKApi() {
        if (bkApi == null) {
            ApiFactory.bkApi = createApi(AppConstant.API_BK_URL, BKApi.class, GsonConverterFactory.create());
        }
        return bkApi;
    }

    public static UserApi getUserApi() {
        if (userApi == null) {
            ApiFactory.userApi = createApi(AppConstant.API_BK_URL, UserApi.class, GanGsonConverterFactory.create());
        }
        return userApi;
    }

    private static <T> T createApi(String baseUrl, Class<T> t, Converter.Factory factory) {
        Retrofit.Builder mBuilder = new Retrofit.Builder()
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl);


        return mBuilder.client(ClientFactory.INSTANCE.getHttpClient()).build().create(t);
    }


}
