package com.example.jiaomin.beautypa.network;

import android.text.TextUtils;

import com.example.jiaomin.beautypa.R;
import com.example.jiaomin.beautypa.app.MyApplication;
import com.example.jiaomin.beautypa.utils.LogUtil;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/6.
 * 类描述：
 */

public abstract class ApiCallback<M> extends Subscriber<M> {
    public abstract void onSuccess(M model);

    public abstract void onFailure(String msg);

    public abstract void onFinish();


    /**
     * 在订阅事件中  发送任何错误都会执行这个方法
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        String msg;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            msg = httpException.getMessage();
            LogUtil.e("onError ---------- code ----------- " + code);
            if (code == 504) {
                msg = MyApplication.getInstance().getString(R.string.network_is_not_to_force);
            } else if (code == 502 || code == 404) {
                msg = MyApplication.getInstance().getString(R.string.server_exception_please_try_again_later);
            }
        } else {
            msg = e.getMessage();
        }

        if (!MyApplication.isNetwokrkAvailable(MyApplication.getInstance())) {
            msg = MyApplication.getInstance().getString(R.string.check_the_network_connection);
        }

        if (!TextUtils.isEmpty(msg)) {
            onFailure(msg);
        }
        onFinish();
    }


    @Override
    public void onNext(M m) {
        onSuccess(m);
    }

    @Override
    public void onCompleted() {
        onFinish();
    }
}
