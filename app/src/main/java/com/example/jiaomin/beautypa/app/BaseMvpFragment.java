package com.example.jiaomin.beautypa.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/10.
 * 类描述： Mvp Fragemnt 基类
 */

public abstract  class BaseMvpFragment<P extends BasePresenter> extends BaseFragment{
    protected P mvpPressenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mvpPressenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mvpPressenter != null){
            mvpPressenter.detachView();
        }
    }
}
