package com.example.jiaomin.beautypa.model;

import android.support.annotation.ArrayRes;

import java.util.ArrayList;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/6.
 * 类描述：知乎日报列表的实体
 */

public class StoriesEntity {
    private String image;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private ArrayList<String> images; // 列表需要用到的图片

    public String getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getImages() {
        if (images == null) {
            // 有可能为null
            images = new ArrayList<>();
            // 手动添加一张图片
            images.add("http://pic4.zhimg.com/08ddf3c3c57518042cbf1f74d64f29cb.jpg");
        }
        return images;
    }
}
