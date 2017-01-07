package com.example.jiaomin.beautypa.view;

import com.example.jiaomin.beautypa.app.BaseView;
import com.example.jiaomin.beautypa.model.NewsEntity;
import com.example.jiaomin.beautypa.model.VideoEntity;

import java.util.ArrayList;

/**
 * Created by JiaoMin on 2017/1/6.
 */

public interface NewsView extends BaseView{

    /**
     * 获取数据成功后的回调
     *
     * @param datas 数据源
     */
    void getNewsListSuccess(NewsEntity datas);

    /**
     * 获取数据失败后的回调
     *
     * @param msg 失败的信息
     */
    void getNewsListFail(String msg);
}
