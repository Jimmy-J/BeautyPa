package com.example.jiaomin.beautypa.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiaomin.beautypa.R;
import com.example.jiaomin.beautypa.app.BaseFragment;
import com.example.jiaomin.beautypa.adapter.BaseFragmentAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/12.
 * 类描述： 美拍视频页面
 */

public class VideoFragment extends BaseFragment {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private View rootView;
    private ArrayList<Fragment> mFragments;
    String[] mTitles = new String[]{
            "热门", "搞笑", "明星名人", "男神", "女神",
            "音乐", "舞蹈", "旅行", "美食", "美妆", "宝宝",
            "萌宠", "二次元","手工","穿秀","吃秀"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_video, null);
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误
            parent.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
    }

    private void initView() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            VideoListFragment videoListFragment = VideoListFragment.newInstance(i);
            mFragments.add(videoListFragment);
        }

        BaseFragmentAdapter fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(fragmentAdapter);

        // 预加载1页  默认是3页，当我们ViewPager中的Fragment比较少
        // 例如 4 到 5 个的时候我们可以设置这个值为 fragemnt的个数，当我们的Framgnet个数比较多就设置为1比较好
        viewPager.setOffscreenPageLimit(1);

        //将TabLayout与ViewPager绑定在一起
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
