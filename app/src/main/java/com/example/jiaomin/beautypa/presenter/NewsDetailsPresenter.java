package com.example.jiaomin.beautypa.presenter;

import com.example.jiaomin.beautypa.app.BasePresenter;
import com.example.jiaomin.beautypa.model.NewsDetailsEntity;
import com.example.jiaomin.beautypa.network.ApiCallback;
import com.example.jiaomin.beautypa.network.AppClient;
import com.example.jiaomin.beautypa.network.ZhiHuApiStores;
import com.example.jiaomin.beautypa.view.NewsDetailsView;

import rx.Observable;

/**
 * Created by MrJiaoMin@outlook.com on 2017/1/9.
 * 类描述：
 */

public class NewsDetailsPresenter extends BasePresenter<NewsDetailsView> {

    private ZhiHuApiStores zhiHuApiStores;

    public NewsDetailsPresenter(NewsDetailsView mvpView) {
        attachView(mvpView);

        zhiHuApiStores = AppClient.getZhiHuRetrofit().create(ZhiHuApiStores.class);
    }


    public void getNewsDetailsData(String id) {
        mvpView.showLoading();
        Observable<NewsDetailsEntity> detailNews = zhiHuApiStores.getDetailNews(id);
        addSubscription(detailNews, new ApiCallback<NewsDetailsEntity>() {
            @Override
            public void onSuccess(NewsDetailsEntity model) {
                mvpView.getNewsDetailsSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.getNewsDetailsFail(msg);
            }

            @Override
            public void onFinish() {
                mvpView.hideLoading();
            }
        });

    }

}
