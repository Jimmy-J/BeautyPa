package com.example.jiaomin.beautypa.network;

import com.example.jiaomin.beautypa.model.VideoEntity;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/6.
 * 类描述： 美拍api Stores
 */

public interface VideoApiStores {

    String API_SERVER_URL = "http://newapi.meipai.com/output/";

    /**
     * 获取视频列表
     * @param map
     * @return
     */
    @GET("channels_topics_timeline.json")
    Observable<List<VideoEntity>> getVideoList(@QueryMap HashMap<String, Object> map);

}
