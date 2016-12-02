package com.example.jiaomin.beautypa.utils;

import android.text.TextUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/2.
 * 类描述：File 操作工具类
 */

public class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 关闭 IO
     *
     * @param closeables closeable
     */
    public static void closeIo(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 判断文件是否存在， 不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true} 文件存在或者创建成功
     * {@code false} 文件不存在或者创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return TextUtils.isEmpty(filePath) ? null : new File(filePath);
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功  {@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
//        如果存在，是文件则返回 true ，是目录则返回 false
        if (file.exists()) {
            return file.isFile();
        }
//        目录是否存在  不存在 或者 创建失败则返回 false
        if (createOrExistsDir(file.getParentFile())) {
            return false;
        }
//        目录存在创建文件
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    /**
     * 判断目录是否存在 ， 不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}： 目录存在或者创建成功
     * {@code false}: 目录不存在或者创建失败
     */
    public static boolean createOrExistsDir(File file) {
//        如果存在而且是目录则返回 true ，是文件则返回 false ，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdir());
    }


}
