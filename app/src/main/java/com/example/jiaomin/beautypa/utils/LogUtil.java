package com.example.jiaomin.beautypa.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
     *
     * @param context        上下文
     * @param logSwith       日志总开关
     * @param log2FileSwitch 日志写入文件夹开关
     * @param logFilter      输入日志类型有{@code v,d,i,w,e} v 代表输出所有信息，w则只输出警告
     * @param tag            标签
     */
    public static void init(Context context, boolean logSwith, boolean log2FileSwitch, char logFilter, String tag) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = context.getExternalCacheDir().getPath() + File.separator;
        } else {
            dir = context.getCacheDir().getPath() + File.separator;
        }

        LogUtil.logSwitch = logSwith;
        LogUtil.log2FileSwitch = log2FileSwitch;
        LogUtil.logFilter = logFilter;
        LogUtil.tag = tag;
    }

    /**
     * 获取LogUtils 建造器
     * 与{@link #init(Context, boolean, boolean, char, String)} 两者选其一
     *
     * @param context 上下文
     * @return Builder 对象
     */
    public static Builder getBuidler(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = context.getExternalCacheDir().getPath() + File.separator + "log" + File.separator;
        } else {
            dir = context.getCacheDir().getPath() + File.separator + "log" + File.separator;
        }
        return new Builder();
    }

    public static class Builder {
        private boolean logSwith = true;
        private boolean log2FileSwitch = false;
        private char logFileter = 'v';
        private String tag = "TAG";


        public Builder setLogSwitch(boolean logSwitch) {
            this.logSwith = logSwitch;
            return this;
        }


        public Builder setLog2FileSwitch(boolean log2FileSwitch) {
            this.log2FileSwitch = log2FileSwitch;
            return this;
        }

        public Builder setLogFileter(char logFileter) {
            this.logFileter = logFileter;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public void create() {
            LogUtil.logSwitch = logSwitch;
            LogUtil.log2FileSwitch = log2FileSwitch;
            LogUtil.logFilter = logFilter;
            LogUtil.tag = tag;
        }
    }

    /**
     * Verbose日志
     *
     * @param msg 消息
     */
    public static void v(Object msg) {
        v(tag, msg);
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void v(String tag, Object msg) {
        v(tag, msg, null);
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'v');
    }

    /**
     * Debug日志
     *
     * @param msg 消息
     */
    public static void d(Object msg) {
        d(tag, msg);
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void d(String tag, Object msg) {// 调试信息
        d(tag, msg, null);
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'd');
    }

    /**
     * Info日志
     *
     * @param msg 消息
     */
    public static void i(Object msg) {
        i(tag, msg);
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void i(String tag, Object msg) {
        i(tag, msg, null);
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'i');
    }

    /**
     * Warn日志
     *
     * @param msg 消息
     */
    public static void w(Object msg) {
        w(tag, msg);
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void w(String tag, Object msg) {
        w(tag, msg, null);
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'w');
    }

    /**
     * Error日志
     *
     * @param msg 消息
     */
    public static void e(Object msg) {
        e(tag, msg);
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void e(String tag, Object msg) {
        e(tag, msg, null);
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }

    /**
     * 根据 tag，msg和等级 输出日志
     *
     * @param tag  标签
     * @param msg  消息
     * @param tr   异常
     * @param type 日志类型
     */
    private static void log(String tag, String msg, Throwable tr, char type) {
        if (logSwitch) {
            if ('e' == type && ('e' == logFilter || 'v' == logFilter)) {
                Log.e(tag, msg, tr);
            } else if ('w' == type && ('w' == logFilter || 'v' == logFilter)) {
                Log.w(tag, msg, tr);
            } else if ('d' == type && ('d' == logFilter || 'v' == logFilter)) {
                Log.d(tag, msg, tr);
            } else if ('i' == type && ('d' == logFilter || 'v' == logFilter)) {
                Log.i(tag, msg, tr);
            }

            if (log2FileSwitch) {
                log2File(type, tag, msg + '\n' + Log.getStackTraceString(tr));
            }
        }
    }

    /**
     * 打开日志文件并写入日志 ，文件是每天创建一个 文件名 例如：12-02.text
     *
     * @param type    日志类型
     * @param tag     日志标签
     * @param content 内容
     */
    private synchronized static void log2File(char type, String tag, String content) {
        if (content == null) {
            return;
        }
        Date now = new Date();
        String nowDate = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(now);
//      每天创建一个日志文件  文件名 例如: 12-02.text
        String fullPath = dir + nowDate + ".txt";
        if (FileUtils.createOrExistsFile(fullPath)) {
//            目录不存在 并且 目录创建失败了
            return;
        }
        String time = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(now);
//        一行日志信息 结尾换行
        String dateLogContent = time + ":" + type + ":" + tag + ":" + content + '\n';
        BufferedWriter bw = null;
        try {
//          创建字符输出流对象 如果文件已经存在就关联，如果不存在就创建，  true ： 在文件后面续写 ，不会覆盖文件
            FileWriter fileWriter = new FileWriter(fullPath, true);
//          创建输出缓存流  特点: 不会马上把信息写入到文件,会等到缓冲区写满之后才写入到文件去,性能高
            bw = new BufferedWriter(fileWriter);
            bw.write(dateLogContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeIo(bw);
        }

    }

}
