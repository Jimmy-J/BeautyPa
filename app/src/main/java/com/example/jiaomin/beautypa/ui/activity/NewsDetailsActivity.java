package com.example.jiaomin.beautypa.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.jiaomin.beautypa.R;
import com.example.jiaomin.beautypa.app.BaseMvpActivity;
import com.example.jiaomin.beautypa.config.StaticData;
import com.example.jiaomin.beautypa.model.NewsDetailsEntity;
import com.example.jiaomin.beautypa.presenter.NewsDetailsPresenter;
import com.example.jiaomin.beautypa.view.NewsDetailsView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MrJiaoMin@outlook.com on 2017/1/9.
 * 类描述：
 */

public class NewsDetailsActivity extends BaseMvpActivity<NewsDetailsPresenter> implements NewsDetailsView {
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingLayout;
//    @Bind(R.id.tv_img_title)
//    AppCompatTextView tvImgTitle;
//    @Bind(R.id.tv_source)
//    AppCompatTextView tvSource;
    @Bind(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    @Override
    protected NewsDetailsPresenter createPresenter() {
        return new NewsDetailsPresenter(this);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_news_details;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        collapsingLayout.setTitle("CollapsingToolbarLayout");
        //设置还没收缩时状态下字体颜色
        collapsingLayout.setExpandedTitleColor(ContextCompat.getColor(mActivity, R.color.black));
        //设置收缩后Toolbar上字体的颜色
        collapsingLayout.setCollapsedTitleTextColor(ContextCompat.getColor(mActivity, R.color.white));


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
    }

    @Override
    public void initData() {
        String id = getIntent().getStringExtra(StaticData.ID);
        mvpPersenter.getNewsDetailsData(id);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getNewsDetailsSuccess(NewsDetailsEntity newsEntity) {
        String head = "<head>\n" +
                "\t<link rel=\"stylesheet\" href=\"" + newsEntity.getCss()[0] + "\"/>\n" +
                "</head>";
        String img = "<div class=\"headline\">";
        String html = head + newsEntity.getBody().replace(img, " ");
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        Picasso.with(this).load(newsEntity.getImage()).into(ivHead);

//        tvImgTitle.setText(newsEntity.getTitle());
//        tvSource.setText("来自:" + newsEntity.getImage_source());
    }

    @Override
    public void getNewsDetailsFail(String msg) {
        toastShow(msg);
    }

    @OnClick(R.id.floating_action_button)
    public void onClick() {

    }
}
