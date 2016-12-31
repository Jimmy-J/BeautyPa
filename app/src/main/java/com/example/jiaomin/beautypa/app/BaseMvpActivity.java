package com.example.jiaomin.beautypa.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/10.
 * 类描述： MvpActivity 基类
 */

public abstract  class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPersenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mvpPersenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mvpPersenter != null){
            mvpPersenter.detachView();
        }
    }
}
