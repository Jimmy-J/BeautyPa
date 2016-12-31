package com.example.jiaomin.beautypa.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.jiaomin.beautypa.utils.LogUtil;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/10.
 * 类描述：  当页面是 Fragment + ViewPager 的时候 ， Fragment 基础此 Fragment  实现懒加载
 */

public class BaseViewPagerFragment extends BaseFragment {
    /**
     * rootView 是否初始化 ， 防止回调函数在 rootView 为空的时候触发
     */
    private boolean hasCreateView;
    /**
     * 当前 Fragment 是否处于可见状态标识， 防止因 ViewPager 的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;
    /**
     * onCreateView() 里返回的 View ， 修饰为 protected， 所以子类继承该类是，在 onCreateView() 里必须对该变量初始化
     */
    protected View rootView;

    /**
     * 当前 Fragemnt 是否可见
     *
     * @param isVisibleToUser true 可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d("setUserVisibleHint ----------- > isVisibleToUser: " + isVisibleToUser);

        if (rootView == null) {
            return;
        }

        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }

        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  可见
     *                  false 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        LogUtil.d("onFragmentVisibleChange ---------- > isVisible: " + isVisible);
    }
}
