package com.example.jiaomin.beautypa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiaomin.beautypa.R;
import com.example.jiaomin.beautypa.config.StaticData;
import com.example.jiaomin.beautypa.model.NewsEntity;
import com.example.jiaomin.beautypa.model.StoriesEntity;
import com.example.jiaomin.beautypa.model.VideoEntity;
import com.example.jiaomin.beautypa.ui.activity.WebViewActivity;
import com.example.jiaomin.beautypa.utils.ImageLoader;

import java.util.ArrayList;

/**
 * Created by JiaoMin on 2017/1/2.
 * msg ： 知乎新闻列表的 adapter
 */

public class NewsAdapter extends RecyclerView.Adapter {
    private ArrayList<StoriesEntity> mDatas;
    private Context mContext;
    private final int ITEM_TYPE_CONTENT = 0; // 内容布局
    private final int ITEM_TYPE_LOAD_MORE = 1;  // 上拉加载更多布局
    private LoadMoreViewHolder loadMoreViewHolder; // 加载更多布局的 ViewHolder ， 用来控制加载更多的progressBar的显示

    public static final int STATUS_START_LOAD_MORE = 1; // 加载更多开始
    public static final int STATUS_STOP_LOAD_MORE = 2; // 加载更多结束

    public NewsAdapter(Context mContext, ArrayList<StoriesEntity> mDatas) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mDatas.size() - 1) {
            // 是最后一条数据了、应该显示的是上拉加载更多的Item
            return ITEM_TYPE_LOAD_MORE;
        }

        return ITEM_TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ITEM_TYPE_CONTENT) {
            // 正常的ViewHolder
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
            viewHolder = new ContentViewHolder(contentView);
        } else {
            // 上拉加载更多的ViewHolder
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_load_more, parent, false);
            loadMoreViewHolder = new LoadMoreViewHolder(contentView);
            viewHolder = loadMoreViewHolder;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        if (itemViewType == ITEM_TYPE_CONTENT) {
            // 正常布局的Item
            StoriesEntity storiesEntity = mDatas.get(position);
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            contentViewHolder.tvNewsTitle.setText(storiesEntity.getTitle());
            ImageLoader.picassoWith(mContext, storiesEntity.getImage(), contentViewHolder.ivNewsImg);

            final int p = position;
            contentViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String url = mDatas.get(p).getUrl();
//                    Intent intent = new Intent(mContext, WebViewActivity.class);
//                    intent.putExtra(StaticData.URL,url);
//                    mContext.startActivity(intent);
                }
            });
        } else if (itemViewType == ITEM_TYPE_LOAD_MORE) {
            // 上拉加载更多的 Item
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 更新加载更多的状态
     *
     * @param loadMoreStatus
     */
    public void updateLoadMoreStatus(int loadMoreStatus) {
        if (loadMoreViewHolder != null) {
            switch (loadMoreStatus) {
                case STATUS_START_LOAD_MORE: // 加载更多开始、显示progressBar
                    loadMoreViewHolder.progressBar.setVisibility(View.VISIBLE);
                    loadMoreViewHolder.tvLoadMoreHint.setVisibility(View.GONE);
                    break;
                case STATUS_STOP_LOAD_MORE: // 加载更多结束
                    loadMoreViewHolder.progressBar.setVisibility(View.GONE);
                    loadMoreViewHolder.tvLoadMoreHint.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * 显示正常内容的ViewHolder
     */
    private class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivNewsImg; // 新闻图片
        private TextView tvNewsTitle; // 发布人的昵称

        private CardView cardView;

        public ContentViewHolder(View itemView) {
            super(itemView);

            ivNewsImg = (ImageView) itemView.findViewById(R.id.item_news_img);
            tvNewsTitle = (TextView) itemView.findViewById(R.id.item_news_title);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    /**
     * 上拉加载更多的ViewHolder
     */
    private class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLoadMoreHint;
        private ContentLoadingProgressBar progressBar;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);

            tvLoadMoreHint = (TextView) itemView.findViewById(R.id.tv_load_hint);
            progressBar = (ContentLoadingProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }
}
