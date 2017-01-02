package com.example.jiaomin.beautypa.app;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/10.
 * 类描述：Presenter 基类
 */

public class BasePresenter<V> {
    protected V mvpView;
    protected CompositeSubscription mCompositeSubscription;


    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }


    public void detachView() {
        this.mvpView = null;

    }

    /**
     * 取消注册 rxjava 以免内存泄漏
     */
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io()) // 在工作线程中执行回调  Call
                .observeOn(AndroidSchedulers.mainThread()) //  回调处理运行在主线程
                .subscribe(subscriber));
    }
}
