package com.yibh.yhfrecycleview.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by y on 2016/5/30.
 */
public class GankApi {
    private final static String GANK_URL = "http://gank.io/api/";
    static final Object obj = new Object();
    private static GankService service;

    public static GankService getSingleService() {

        synchronized (obj) {
            if (null == service) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(GANK_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();

                service = retrofit.create(GankService.class);
            }
        }
        return service;
    }

}
