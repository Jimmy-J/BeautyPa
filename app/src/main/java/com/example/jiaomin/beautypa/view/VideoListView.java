package com.example.jiaomin.beautypa.view;

import com.example.jiaomin.beautypa.app.BaseView;
import com.example.jiaomin.beautypa.model.VideoEntity;

import java.util.ArrayList;

/**
 * Created by JiaoMin on 2017/1/1.
 * 用于Activity 或者 Fragment ，与 Present 通信的桥梁、MVP 中的 V
 * 这个接口的作用是在 VideoListPresenter 获取数据成功或者失败后，进行通知到 VideListFragment
 */

public interface VideoListView extends BaseView{
    /**
     * 获取数据成功后的回调
     *
     * @param mDatas 数据源
     */
    void getVideoListSuccess(ArrayList<VideoEntity> mDatas);

    /**
     * 获取数据失败后的回调
     *
     * @param msg 失败的信息
     */
    void getVideoListFail(String msg);
}
