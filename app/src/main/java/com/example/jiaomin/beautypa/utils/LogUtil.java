package com.example.jiaomin.beautypa.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/1.
 * 类描述：log 日志管理类
 */

public class LogUtil {

    private LogUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 日志开关
     */
    private static boolean logSwitch = true;
    /**
     * 日志写入文件开关
     */
    private static boolean log2FileSwitch = false;
    /**
     * 日志输出的类型  {@code v,d,i,w,e} v 代表输出所有信息，w则只输出警告
     */
    private static char logFilter = 'v';
    /**
     * 标签
     */
    private static String tag = "TAG";
    /**
     * 保存的日志路径
     */
    private static String dir = null;

    /**
     * 初始化函数
     * @param context           上下文
     * @param logSwith          日志总开关
     * @param log2FileSwitch    日志写入文件夹开关
     * @param logFilter         输入日志类型有{@code v,d,i,w,e} v 代表输出所有信息，w则只输出警告
     * @param tag               标签
     */
    public static void init(Context context,boolean logSwith,boolean log2FileSwitch,char logFilter,String tag){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = context.getExternalCacheDir().getPath() + File.separator;
        }else {
            dir = context.getCacheDir().getPath() + File.separator;
        }

        LogUtil.logSwitch = logSwith;
        LogUtil.log2FileSwitch = log2FileSwitch;
        LogUtil.logFilter = logFilter;
        LogUtil.tag = tag;
    }


    public static Builder getBuidler(Context context){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = context.getExternalCacheDir().getPath() + File.separator + "log" + File.separator;
        }else {
            dir = context.getCacheDir().getPath() + File.separator + "log" + File.separator;
        }
        return new Builder();
    }

    public static class Builder {
        private boolean logSwith        = true;
        private boolean log2FileSwitch  = false;
        private char logFileter         = 'v';
        private String tag              = "TAG";


        public Builder setLogSwitch(boolean logSwitch){
            this.logSwith = logSwitch;
            return this;
        }


        public Builder setLog2FileSwitch(boolean log2FileSwitch){
            this.log2FileSwitch = log2FileSwitch;
            return this;
        }

        public Builder setLogFileter(char logFileter){
            this.logFileter = logFileter;
            return this;
        }

        public Builder setTag(String tag){
            this.tag = tag;
            return this;
        }

        public void create(){
            LogUtil.logSwitch = logSwitch;
            LogUtil.log2FileSwitch = log2FileSwitch;
            LogUtil.logFilter = logFilter;
            LogUtil.tag = tag;
        }
    }



    private static void log(String tag,String msg,Throwable tr,char type){
        if (logSwitch){
            if ('e' == type && ('e' == logFilter || 'v' == logFilter)) {
                Log.e(tag, msg, tr);
            } else if ('w' == type && ('w' == logFilter || 'v' == logFilter)) {
                Log.w(tag, msg, tr);
            } else if ('d' == type && ('d' == logFilter || 'v' == logFilter)) {
                Log.d(tag, msg, tr);
            } else if ('i' == type && ('d' == logFilter || 'v' == logFilter)) {
                Log.i(tag, msg, tr);
            }

            if (log2FileSwitch){

            }
        }
    }



    private synchronized static void log2File(char type,String tag,String content){
        if (content == null) {
            return;
        }



    }

}
