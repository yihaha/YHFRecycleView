package com.yibh.yhfrecycleview.http;

import com.yibh.yhfrecycleview.model.AllData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by y on 2016/5/30.
 */
public interface GankService {
    //图片列表
    @GET("data/福利/{pageCount}/{currPage}")
    Observable<AllData> getMeiziList(@Path("pageCount") int pageCount, @Path("currPage") int currPage);
}
