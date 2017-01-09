package com.example.jiaomin.beautypa.view;

import com.example.jiaomin.beautypa.app.BaseView;
import com.example.jiaomin.beautypa.model.NewsDetailsEntity;

/**
 * Created by MrJiaoMin@outlook.com on 2017/1/9.
 * 类描述： MVP 中的 V
 */

public interface NewsDetailsView extends BaseView {

    void getNewsDetailsSuccess(NewsDetailsEntity data);

    void getNewsDetailsFail(String msg);
}
