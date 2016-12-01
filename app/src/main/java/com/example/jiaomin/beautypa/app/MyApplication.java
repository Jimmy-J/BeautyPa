package com.example.jiaomin.beautypa.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/1.
 * 类描述：自定义的 AppLication
 */

public class MyApplication extends Application{
    private static MyApplication mInstance ;
    private static Context mContext;
    /**
     * 屏幕宽度
     */
    public int screenWidth;
    /**
     * 屏幕高度
     */
    public int screenHeight;
    /**
     * 屏幕密度
     */
    public int screenDensity;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mInstance = this;


    }
}
