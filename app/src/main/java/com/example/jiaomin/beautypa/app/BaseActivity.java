package com.example.jiaomin.beautypa.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jiaomin.beautypa.R;
import com.example.jiaomin.beautypa.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/2.
 * 类描述：通用的BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Activity mActivity;
    private CompositeSubscription mCompositeSubscription;
    private List<Call> mCalls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int contentLayoutId = getContentLayoutId();
        if (contentLayoutId != 0) {
            setContentView(contentLayoutId);
            ButterKnife.bind(this);
        }

        mActivity = this;
    }

    @Override
    protected void onDestroy() {
        callCancel();
        onUnsubscribe();
        super.onDestroy();
    }

    protected void addCalls(Call call) {
        if (mCalls == null) {
            mCalls = new ArrayList<>();
        }
        mCalls.add(call);

        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka",
                "David Ferrer","Roger Federer",
                "Andy Murray","Tomas Berdych",
                "Juan Martin Del Potro"};
        List<String> players =  Arrays.asList(atp);
    }

    protected void addSubscription(Observable observable, Subscriber subscriber) {
        Subscription subscribe = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        addSubscription(subscribe);
    }

    protected void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);

    }

    public void onUnsubscribe() {
        LogUtil.e("onUnsubscribe -------------- ");
//      取消注册 以避免内存泄漏
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    private void callCancel() {
        if (mCalls != null && mCalls.size() > 0) {
            for (Call call : mCalls) {
                if (!call.isCanceled()) {
                    call.cancel();
                }
            }
            mCalls.clear();
        }
    }

    /**
     * 初始化ToolBar
     *
     * @param title        标题
     * @param showHomeAsUp 左边的按钮是否可以点击
     * @return
     */
    protected Toolbar initToolBar(String title, boolean showHomeAsUp) {
        Toolbar toolbar = getView(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(title);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        return toolbar;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected <T extends View> T getView(int resId) {
        return (T) findViewById(resId);
    }

    // ------------------------------------- 通用的 Toast -------------------------------------
    protected void toastShow(int resId) {
        toastShow(getString(resId));
    }

    protected void toastShow(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }


    // ----------------------------------- 通用的加载中 Dialog ---------------------------
    protected ProgressDialog progressDialog;

    protected ProgressDialog showProgressDialog() {
        return showProgressDialog(getString(R.string.loading));
    }

    protected ProgressDialog showProgressDialog(CharSequence message) {
        if (mActivity != null && !mActivity.isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(mActivity);
                progressDialog.setMessage(message);
            }
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
            return progressDialog;
        }

        return null;
    }


    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }
    /**
     * 获得Activity要显示的布局Id
     *
     * @return contentId
     */
    public abstract int getContentLayoutId();

    /**
     * View的初始化操作放在这个方法里面
     */
    public abstract void initView();

    /**
     * 数据的初始化放在这个方法里面
     */
    public abstract void initData();


}
