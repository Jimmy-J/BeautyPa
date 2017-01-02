package com.example.jiaomin.beautypa.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by JiaoMin on 2017/1/2.
 * 加载图片工具类
 * 对 Picasso  框架的一层简单的封装，方便日后扩展
 * <p>
 * Picasso 原本就很简单方便为什么还要进行封装 ？
 * 看一下这篇文章：
 * http://mp.weixin.qq.com/s?__biz=MzA4NTQwNDcyMA==&mid=2650661623&idx=1&sn=ab28ac6587e8a5ef1241be7870851355#rd
 */

public class ImageLoader {

    public static void picassoWith(Context context, String imageUrl, ImageView imageView) {
        Picasso.with(context).load(imageUrl).into(imageView);
    }
}
