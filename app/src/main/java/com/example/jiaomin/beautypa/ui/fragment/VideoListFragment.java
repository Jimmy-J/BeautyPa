package com.example.jiaomin.beautypa.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.example.jiaomin.beautypa.R;
import com.example.jiaomin.beautypa.adapter.VideoListAdapter;
import com.example.jiaomin.beautypa.app.BaseMvpViewPagerFragment;
import com.example.jiaomin.beautypa.app.BasePresenter;
import com.example.jiaomin.beautypa.config.StaticData;
import com.example.jiaomin.beautypa.model.VideoEntity;
import com.example.jiaomin.beautypa.presenter.VideoListPresenter;
import com.example.jiaomin.beautypa.utils.LogUtil;
import com.example.jiaomin.beautypa.view.VideoListView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/12.
 * 类描述： 美拍视频列表页面
 */

public class VideoListFragment extends BaseMvpViewPagerFragment<VideoListPresenter> implements VideoListView {
    @Bind(R.id.ry_view)
    RecyclerView ryView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int index; // 当前Fragment的index
    private String[] ids = {"1", "13", "16", "31", "19", "62",
            "63", "59", "27", "18", "6", "450", "460", "423"};
    private boolean isLoad; // 是否已经加载过数据了，用于实现数据只加载一次
    private int page = 1; // 当前的页数
    private int count = 20; // 每一页加载的数据

    private VideoListAdapter mAdapter;
    private ArrayList<VideoEntity> mDatas;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout.OnRefreshListener swipeRefreshListener;

    /**
     * 当需要给Fragment传递参数时一般推荐使用这种写法
     *
     * @param index 当前Fragment的下标 ， 对应ids中的id
     * @return this
     */
    public static VideoListFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(StaticData.INDEX, index);
        VideoListFragment videoListFragment = new VideoListFragment();
        videoListFragment.setArguments(bundle);
        return videoListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null) {
            index = arguments.getInt(StaticData.INDEX);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 加上 super 去调用父类的 onCreateView方法
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_video_list, null);
        }

        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.e("on View Create ----------- ");
        initView();
        initData();
    }

    private void initData() {
    }

    private void initView() {
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        ryView.setLayoutManager(mLinearLayoutManager);

        // 设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        swipeRefreshLayout.setProgressViewOffset(true, 20, 150);
        // 设定下拉圆圈的背景
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置下拉圆圈上的颜色，绿色、蓝色、橙色、红色
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        // 把下拉刷新监听写出来
        swipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉刷新回调
                page = 1;
                getVideoList(null);

            }
        };

        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener);


        ryView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // recyclerView 滑动状态判断
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 当ReccylerView 停止滑动时 、 获得最后一个可见Item的 position
                    int lastVisibleItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

                    final int size = mAdapter.getItemCount(); // 手动添加了一条假数据
                    //判断是否是滑动到了最下面
                    if (lastVisibleItemPosition == size - 1 && mAdapter.canLoadMore()) { // position 是从0开始 、 getItemCount 是数量 从 1 开始计数，所以 - 1
                        // 更新加载更多的状态
                        // 加载太快了看不出效果，这里用一个手动 runable 延时 1 秒再去加载数据
                        mAdapter.updateLoadMoreStatus(VideoListAdapter.STATUS_START_LOAD_MORE);
                        ryView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getVideoList(String.valueOf(mDatas.get(size - 2).getId()));
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getUserVisibleHint()) {
            lazyLoadDatas();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            lazyLoadDatas();
        }
    }


    private void lazyLoadDatas() {
        if (isLoad) {
            return;
        }
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    // 显示出下来刷新， 但是不会回调 OnRefresListener ，需要手动调用 getVideoList
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    swipeRefreshLayout.setRefreshing(true);
                    swipeRefreshListener.onRefresh();
                }
            });
        }

    }

    /**
     * Fragment 下次可见需要刷新时调用这个方法就会去刷新数据了，否则只会在第一次可见时去获取数据，，随后的每一次可见都不会去取获取了
     */
    public void refresh() {
        isLoad = false;
    }

    /**
     * 获取数据
     *
     * @param maxId 当前数据源最后一天数据的id
     */
    public void getVideoList(String maxId) {
        HashMap<String, Object> parame = new HashMap<>();
        parame.put(StaticData.ID, ids[index]);
        parame.put(StaticData.PAGE, page);
        parame.put(StaticData.COUNT, count);
        if (!TextUtils.isEmpty(maxId)) {
            // 不为空说明是第一次获取数据
            parame.put(StaticData.MAX_ID, maxId);
        }
        // 加载数据
        if (mvpPresenter != null) {
            mvpPresenter.getVideoList(parame);
        }
    }

    @Override
    protected VideoListPresenter createPresenter() {
        return new VideoListPresenter(this);
    }


    @Override
    public void getVideoListSuccess(ArrayList<VideoEntity> datas) {
        // 旧的数据源的长度
        int oldDatasSize = -1;
        if (this.mDatas == null) {
            // 首次获取数据
            this.mDatas = new ArrayList<>();
        } else if (page == 1) {
            //下拉刷新，先清除数据
            mDatas.clear();
        } else {
            // 是上拉加载更多 ， 记录上一次数据源的长度
            oldDatasSize = mDatas.size();
        }
        mDatas.addAll(datas);

        if (mAdapter == null) {
            // 首次获取数据
            mAdapter = new VideoListAdapter(mActivity, mDatas);
            ryView.setAdapter(mAdapter);
            // 数据已经加载成功 ， 改变标识
            isLoad = true;
        } else if (oldDatasSize > 0) {
            // 上拉加载、告诉Adapter 需要插入数据 ， 插入数据的position位置 、 插入的数量
            mAdapter.notifyItemRangeInserted(oldDatasSize + 1, mDatas.size() - oldDatasSize);
            mAdapter.updateLoadMoreStatus(VideoListAdapter.STATUS_STOP_LOAD_MORE);
        } else {
            // 下拉刷新
            mAdapter.notifyDataSetChanged();
        }

        page++; // 页码加一
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void getVideoListFail(String msg) {
        toastShow(msg);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
