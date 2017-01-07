package com.example.jiaomin.beautypa.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jiaomin.beautypa.R;
import com.example.jiaomin.beautypa.app.BaseActivity;
import com.example.jiaomin.beautypa.config.StaticData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MrJiaoMin@outlook.com on 2017/1/6.
 * 类描述： 显示美拍视频详情的Activity
 */

public class WebViewActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webView)
    WebView webView;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        toolbar.setTitle(R.string.video_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData() {
        final String urlPath = getIntent().getStringExtra(StaticData.URL);
        setSettings(webView.getSettings());

        webView.loadUrl(urlPath);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                showProgressDialog();
                view.loadUrl(urlPath);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissProgressDialog();
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 对返回做处理
            if (webView.canGoBack()) {
                webView.goBack(); // 返回webView的上一个页面
                return true;
            } else {
                onBackPressed();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setSettings(WebSettings setting) {
        // 设置WebView支持JavaScript
        setting.setJavaScriptEnabled(true);
        // 设置使用默认的缩放控制器,默认是false
        setting.setBuiltInZoomControls(true);
        // 不显示默认的+/-缩放控制View, 默认是true
        setting.setDisplayZoomControls(false);
        // 设置支持缩放
        setting.setSupportZoom(true);
        // 支持 H5 的session storage和local storage
        setting.setDomStorageEnabled(true);
        // 支持javascript读,写db
        setting.setDatabaseEnabled(true);
        //设置使用 宽 的Viewpoint,默认是false
        //Android browser以及chrome for Android的设置是`true`
        //而WebView的默认设置是`false`
        //如果设置为`true`,那么网页的可用宽度为`980px`,并且可以通过 meta data来设置
        //如果设置为`false`,那么可用区域和WebView的显示区域有关.
        setting.setLoadWithOverviewMode(true);
        //如果webview内容宽度大于显示区域的宽度,那么将内容缩小,以适应显示区域的宽度, 默认是false
        setting.setUseWideViewPort(true);
    }

}
