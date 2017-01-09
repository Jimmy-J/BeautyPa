package com.example.jiaomin.beautypa.presenter;

import com.example.jiaomin.beautypa.app.BasePresenter;
import com.example.jiaomin.beautypa.model.NewsEntity;
import com.example.jiaomin.beautypa.model.StoriesEntity;
import com.example.jiaomin.beautypa.network.ApiCallback;
import com.example.jiaomin.beautypa.network.AppClient;
import com.example.jiaomin.beautypa.network.ZhiHuApiStores;
import com.example.jiaomin.beautypa.view.NewsView;

import rx.Observable;

/**
 * Created by JiaoMin on 2017/1/6.
 * 知乎日报的 presenter
 */

public class NewsPresenter extends BasePresenter<NewsView> {
    private ZhiHuApiStores zhiHuApiStores;

    public NewsPresenter(NewsView newsView) {
        attachView(newsView);

        zhiHuApiStores = AppClient.getZhiHuRetrofit().create(ZhiHuApiStores.class);
    }

    public void getNewsDatas() {
        mvpView.showLoading();

        addSubscription(zhiHuApiStores.getLatestNews(), new ApiCallback<NewsEntity>() {
            @Override
            public void onSuccess(NewsEntity model) {
                    mvpView.getNewsListSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                    mvpView.getNewsListFail(msg);
            }

            @Override
            public void onFinish() {
                    mvpView.hideLoading();
            }
        });

        //        addSubscription(zhiHuApiStores.getLatestNews); // 优化代码
    }


    public void getBeforetNews(String date) {
        if (mvpView == null) {
            return;
        }

        addSubscription(zhiHuApiStores.getBeforetNews(date), new ApiCallback<NewsEntity>() {
            @Override
            public void onSuccess(NewsEntity model) {
                    mvpView.getNewsListSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                    mvpView.getNewsListFail(msg);
            }

            @Override
            public void onFinish() {
                    mvpView.hideLoading();
            }
        });

        //        addSubscription(zhiHuApiStores.getBeforetNews(date)); // 优化代码

    }

    /**
     * 将上面两个方法的通用代码块抽象
     *
     * @param observable
     */
    private void addSubscription(Observable<NewsEntity> observable) {
        addSubscription(observable, new ApiCallback<NewsEntity>() {
            @Override
            public void onSuccess(NewsEntity model) {
                if (mvpView != null) {
                    mvpView.getNewsListSuccess(model);
                }
            }

            @Override
            public void onFailure(String msg) {
                if (mvpView != null) {
                    mvpView.getNewsListFail(msg);
                }
            }

            @Override
            public void onFinish() {
                if (mvpView != null) {
                    mvpView.hideLoading();
                }
            }
        });
    }

}
