package com.example.jiaomin.beautypa.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/10.
 * 类描述：
 */

public abstract class BaseMvpViewPagerFragment<P extends BasePresenter> extends BaseViewPagerFragment {
    protected P mvpPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
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
