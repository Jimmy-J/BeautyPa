package com.example.jiaomin.beautypa.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.example.jiaomin.beautypa.utils.LogUtil;

import java.util.Locale;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/1.
 * 类描述：自定义的 AppLication
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;
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
    public float screenDensity;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mInstance = this;

//      初始化日志操作工具类  开启日志打印 ， 关闭日志写入文件功能 ， 日志打印类型为 d ， 日志tag 为 MrJiaoMin
        LogUtil.init(this, true, false, 'd', "MrJiaoMin");
    }


    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        PackageManager packageManager = mInstance.getPackageManager();
        String version;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(mInstance.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version = "";
        }

        LogUtil.e("version" + version);
        return version;
    }

    /**
     * 获取当前系统语言
     *
     * @return
     */
    public static String getLanguage() {
        Locale locale = Locale.getDefault();
        String language = locale.getDefault().toString();
        return language;
    }

    /**
     * 获取当前屏幕的宽高
     */
    public void initScreenSize() {
        DisplayMetrics currMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenHeight = currMetrics.heightPixels;
        screenWidth = currMetrics.widthPixels;
        screenDensity = currMetrics.density;
    }

    /**
     * 判断网络是否有连接
     *
     * @param context
     * @return true 正常连接中
     */
    public static boolean isNetwokrkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
