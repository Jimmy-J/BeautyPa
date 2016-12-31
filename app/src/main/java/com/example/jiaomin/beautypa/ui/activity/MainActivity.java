package com.example.jiaomin.beautypa.ui.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.jiaomin.beautypa.R;
import com.example.jiaomin.beautypa.app.BaseActivity;
import com.example.jiaomin.beautypa.ui.fragment.NewsFragment;
import com.example.jiaomin.beautypa.ui.fragment.VideoFragment;
import com.example.jiaomin.beautypa.utils.statusbarutils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    private Fragment fragment;

    private List<Fragment> fragments;


    @Override
    public int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initToolBar(getString(R.string.video), false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawaer_open, R.string.drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        StatusBarUtil.setColorForDrawerLayout(this,drawerLayout,ContextCompat.getColor(mActivity,R.color.colorPrimary),23);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);



        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragments.size() == 2){
                    fragment = fragments.get(1);
                }else {
                    fragment = new NewsFragment();
                    fragments.add(fragment);
                }
                toolbar.setTitle(R.string.zhihu_new);
                toolbar.setBackgroundColor(ContextCompat.getColor(mActivity,R.color.redColorPrimary));
                StatusBarUtil.setColorForDrawerLayout(mActivity,drawerLayout, ContextCompat.getColor(mActivity,R.color.redColorPrimary),23);

//                Window windowOne = getWindow();
//                windowOne.setStatusBarColor(ContextCompat.getColor(mActivity,R.color.orangeColorPrimaryDark));
//                windowOne.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        });
    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
        fragment = new VideoFragment();
        fragments.add(fragment);

        replaceFragemnt();



    }

    /**
     * 切换 Fragment 显示
     */
    private void replaceFragemnt() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.replace(R.id.main_content, fragment);
        }
//      现在就提交事物 ， 不需要在 Activity 被进程杀死的时候保存状态
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//           如果 DrawerLayout 是 打开状态则先关闭
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Nacigation Item Selected 监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setCheckable(true);
        switch (item.getItemId()) {
            case R.id.nav_video:
                fragment = fragments.get(0);
                toolbar.setTitle(R.string.video);
                toolbar.setBackgroundColor(ContextCompat.getColor(mActivity,R.color.colorPrimary));
//                Window window = getWindow();
//                window.setStatusBarColor(ContextCompat.getColor(mActivity,R.color.colorPrimaryDark));
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//                StatusBarUtil.setColor(this,ContextCompat.getColor(mActivity,R.color.colorPrimaryDark));
                StatusBarUtil.setColorForDrawerLayout(this,drawerLayout,ContextCompat.getColor(mActivity,R.color.colorPrimary),23);

                replaceFragemnt();
                break;
            case R.id.nav_new:
                if (fragments.size() == 2){
                    fragment = fragments.get(1);
                }else {
                    fragment = new NewsFragment();
                    fragments.add(fragment);
                }
                toolbar.setTitle(R.string.zhihu_new);
                toolbar.setBackgroundColor(ContextCompat.getColor(mActivity,R.color.redColorPrimary));
//                Window windowOne = getWindow();
//                windowOne.setStatusBarColor(ContextCompat.getColor(mActivity,R.color.orangeColorPrimaryDark));
//                windowOne.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                windowOne.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//                StatusBarUtil.setColor(this,ContextCompat.getColor(mActivity,R.color.orangeColorPrimary));
                StatusBarUtil.setColorForDrawerLayout(this,drawerLayout, ContextCompat.getColor(mActivity,R.color.redColorPrimary),23);


                replaceFragemnt();
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_share:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
