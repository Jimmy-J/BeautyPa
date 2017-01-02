package com.example.jiaomin.beautypa.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by JiaoMin on 2017/1/1.
 * <p>
 * msg: 通用的ViewPager + Fragment 适配器
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragment;
    private String[] mTitles;

    public BaseFragmentAdapter(FragmentManager fm, ArrayList<Fragment> mFragments) {
        this(fm, mFragments, null);
    }

    public BaseFragmentAdapter(FragmentManager fm, ArrayList<Fragment> mFragments, String[] mTitles) {
        super(fm);
        this.mFragment = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    /**
     * 返回Tablayout的标题
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            return mTitles[position];
        }
        return super.getPageTitle(position);
    }
}
