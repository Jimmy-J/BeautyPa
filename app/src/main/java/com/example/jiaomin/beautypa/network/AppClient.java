package com.example.jiaomin.beautypa.network;

import com.example.jiaomin.beautypa.BuildConfig;
import com.example.jiaomin.beautypa.app.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/6.
 * 类描述：通用的网络处理类
 */

public class AppClient {

    public static Retrofit mVideoRetrofit;
    public static Retrofit mZhiHuRetrofit;
    private static OkHttpClient okHttpClient;

    public static Retrofit getVideoRetrofit() {
        if (mVideoRetrofit == null) {
            mVideoRetrofit = new Retrofit.Builder()
                    .baseUrl(VideoApiStores.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return mVideoRetrofit;
    }


    public static Retrofit getZhiHuRetrofit() {
        if (mZhiHuRetrofit == null) {
            mZhiHuRetrofit = new Retrofit.Builder()
                    .baseUrl(ZhiHuApiStores.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return mZhiHuRetrofit;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                // 设置Debug Log 模式
                builder.addInterceptor(httpLoggingInterceptor);
            }

            File httpCacheDirectory = new File(MyApplication.getInstance().getExternalCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            builder.cache(cache);
            builder.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            okHttpClient = builder.build();
        }

        return okHttpClient;
    }

    /**
     * okHttp的拦截器  无网络时从缓存获取数据
     */
    static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if (!MyApplication.isNetwokrkAvailable(MyApplication.getInstance())) {
                request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
//                没有网络
            }

            Response originalResponse = chain.proceed(request);
            Response response;
            if (MyApplication.isNetwokrkAvailable(MyApplication.getInstance())) {
                int maxAge = 0;
                response = originalResponse.newBuilder()
                        .removeHeader("Pragme")
                        .header("Cache-Control", "public,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                response = originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public,only-if-xcached,max-stale=" + maxStale)
                        .build();
            }

            return response;
        }
    };
}
