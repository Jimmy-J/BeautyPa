package com.example.jiaomin.beautypa.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/10.
 * 类描述：
 */

public abstract class BaseMvpViewPagerFragment<P extends BasePresenter> extends BaseViewPagerFragment {
    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
    }


    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
