package com.example.jiaomin.beautypa.presenter;

import android.widget.VideoView;

import com.example.jiaomin.beautypa.app.BasePresenter;
import com.example.jiaomin.beautypa.model.VideoEntity;
import com.example.jiaomin.beautypa.network.ApiCallback;
import com.example.jiaomin.beautypa.network.AppClient;
import com.example.jiaomin.beautypa.network.VideoApiStores;
import com.example.jiaomin.beautypa.utils.LogUtil;
import com.example.jiaomin.beautypa.view.VideoListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by JiaoMin on 2017/1/1.
 * 美拍视频列表的 Presenter  MVP 中的 p
 */

public class VideoListPresenter extends BasePresenter<VideoListView> {
    private VideoApiStores videoApiStores;

    public VideoListPresenter(VideoListView view) {
        //
        attachView(view);
        //通过 Retrofit 来创建这个对象，创建成功之后可以通过调用有Retrofit 特定注解的方法来进行网络请求
        videoApiStores = AppClient.getVideoRetrofit().create(VideoApiStores.class);
    }

    public void getVideoList(HashMap<String, Object> parame) {
        mvpView.showLoading();
        // 去请求网络，会返回一个被观察者的对象
        Observable<List<VideoEntity>> observable = videoApiStores.getVideoList(parame);
        // 把被观察者与观察者建立连接，当被观察者调用了Call方法就会调用观察者中的 onNext 、 onCompleted 或者 onError 方法
        // 这里被 ApiCallbackT替换成了 onSuccess 、 onFailure 、onFinish
        addSubscription(observable, new ApiCallback<ArrayList<VideoEntity>>() {
            @Override
            public void onSuccess(ArrayList<VideoEntity> model) {
                    mvpView.getVideoListSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                    mvpView.getVideoListFail(msg);
            }

            @Override
            public void onFinish() {
                    mvpView.hideLoading();
            }
        });


        Observable<String> just = Observable.just("1");

        just.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                /// 会打印出 jsut（）中的 1
                LogUtil.e("tag ---------- " + s);
            }
        });
    }

}
