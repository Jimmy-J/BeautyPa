package com.example.jiaomin.beautypa.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.example.jiaomin.beautypa.R;
import com.example.jiaomin.beautypa.adapter.NewsAdapter;
import com.example.jiaomin.beautypa.adapter.VideoListAdapter;
import com.example.jiaomin.beautypa.app.BaseMvpFragment;
import com.example.jiaomin.beautypa.app.BasePresenter;
import com.example.jiaomin.beautypa.model.NewsEntity;
import com.example.jiaomin.beautypa.model.StoriesEntity;
import com.example.jiaomin.beautypa.presenter.NewsPresenter;
import com.example.jiaomin.beautypa.utils.LogUtil;
import com.example.jiaomin.beautypa.view.NewsView;
import com.example.jiaomin.beautypa.view.TopViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/12.
 * 类描述： 知乎日报页面
 */

public class NewsFragment extends BaseMvpFragment<NewsPresenter> implements NewsView {
    @Bind(R.id.ry_view)
    RecyclerView ryView;
    @Bind(R.id.vp_top_stories)
    TopViewPager vpTopStories;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private View rootView;

    private boolean isLoad;

    private ArrayList<StoriesEntity> mDatas;
    private boolean isRefresh; // 是否是刷新
    private NewsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private NewsEntity newsEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news, null);
        }
        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(mActivity);
        ryView.setLayoutManager(mLayoutManager);

        // 设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        swipeRefreshLayout.setProgressViewOffset(false, 20, 150);
        // 设定下拉圆圈的背景
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置下拉圆圈上的颜色，绿色、蓝色、橙色、红色
        swipeRefreshLayout.setColorSchemeResources(
                R.color.redColorPrimary,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                R.color.colorPrimary
        );

        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // 判断是否可以下拉刷新
                swipeRefreshLayout.setEnabled(verticalOffset == 0);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                mvpPressenter.getNewsDatas();
            }
        });

        ryView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // recyclerView 滑动状态判断
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 当ReccylerView 停止滑动时 、 获得最后一个可见Item的 position
                    final int lastVisibleItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();

                    final int size = mAdapter.getItemCount();
                    //判断是否是滑动到了最下面
                    if (lastVisibleItemPosition == size - 1 && mAdapter.canLoadMore()) {
                        // 更新加载更多的状态
                        // 加载太快了看不出效果，这里用一个手动 runable 延时 1 秒再去加载数据
                        mAdapter.updateLoadMoreStatus(VideoListAdapter.STATUS_START_LOAD_MORE);
                        ryView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mvpPressenter.getBeforetNews(newsEntity.getDate());
                            }
                        }, 1000);

                        // 我们在项目中应该是像下面这样写，不应该手动去延时
//                        mAdapter.updateLoadMoreStatus(VideoListAdapter.STATUS_START_LOAD_MORE);
//                        getVideoList(String.valueOf(mDatas.get(size - 1).getId()));
                    }


                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected NewsPresenter createPresenter() {
        return new NewsPresenter(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getUserVisibleHint()) {
            requestData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            if (!isLoad) {
                requestData();
            }
        }
    }


    private void requestData() {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            swipeRefreshLayout.setRefreshing(true);
            isLoad = true;
            mvpPressenter.getNewsDatas();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void getNewsListSuccess(NewsEntity datas) {
        newsEntity = datas;
        if (mDatas == null) {
            mDatas = datas.getStories();
            mAdapter = new NewsAdapter(mActivity, mDatas);
            SlideInLeftAnimationAdapter adapter = new SlideInLeftAnimationAdapter(mAdapter);
            adapter.setDuration(300);
            adapter.setInterpolator(new OvershootInterpolator());
            ryView.setAdapter(adapter);
            isLoad = true;
            initBanners(datas.getTop_stories());
        } else {
            if (isRefresh) {
                isRefresh = false;
                // 是下拉刷新
                mDatas.clear();
                mDatas.addAll(datas.getStories());
                mAdapter.notifyDataSetChanged();
                initBanners(datas.getTop_stories());
            } else {
                // 上拉加载更多
                int oldSize = mDatas.size();
                mDatas.addAll(datas.getStories());
                mAdapter.updateLoadMoreStatus(NewsAdapter.STATUS_STOP_LOAD_MORE);
                mAdapter.notifyItemRangeInserted(oldSize + 1, mDatas.size() - oldSize);
            }
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    private void initBanners(ArrayList<StoriesEntity> stories) {
        vpTopStories.init(stories, tvTopTitle, new TopViewPager.ViewPagerClickListenner() {
            @Override
            public void onClick(StoriesEntity item) {
//                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
//                intent.putExtra("id", item.getId() + "");
//                startActivity(intent);
            }
        });
        vpTopStories.startAutoRun();
    }

    @Override
    public void getNewsListFail(String msg) {
        toastShow(msg);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
