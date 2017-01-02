package com.example.jiaomin.beautypa.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.jiaomin.beautypa.R;
import com.example.jiaomin.beautypa.utils.LogUtil;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/2.
 * 类描述： 通用的 Fragment
 */

public class BaseFragment extends Fragment {

    protected Activity mActivity;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public Toolbar initToolBar(View view, String title) {
        Toolbar toolbar = getView(view, R.id.toolbar);
        toolbar.setTitle(title);
        return toolbar;
    }


    protected <T extends View> T getView(View view, int resId) {
        return (T) view.findViewById(resId);
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
}
