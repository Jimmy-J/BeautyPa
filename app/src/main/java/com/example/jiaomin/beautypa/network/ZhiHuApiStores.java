package com.example.jiaomin.beautypa.network;

import com.example.jiaomin.beautypa.model.NewsDetailsEntity;
import com.example.jiaomin.beautypa.model.NewsEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/2.
 * 类描述：知乎日报api管理者
 */

public interface ZhiHuApiStores {
    String API_SERVER_URL = "http://news-at.zhihu.com/api/4/";

    // url： http://news-at.zhihu.com/api/4/news/latest
    // 获取列表数据
    @GET("news/latest")
    Observable<NewsEntity> getLatestNews();

    // url： http://news-at.zhihu.com/api/4/news/before/time
    // 上拉加载更多获取数据
    @GET("news/before/{time}")
    Observable<NewsEntity> getBeforetNews(@Path("time") String time);

    // url: http://news-at.zhihu.com/api/4/id
    @GET("news/{id}")
    Observable<NewsDetailsEntity> getDetailNews(@Path("id") String id);

}
