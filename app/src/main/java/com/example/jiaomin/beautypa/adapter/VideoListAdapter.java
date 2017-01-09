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
import com.example.jiaomin.beautypa.model.VideoEntity;
import com.example.jiaomin.beautypa.ui.activity.WebViewActivity;
import com.example.jiaomin.beautypa.utils.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JiaoMin on 2017/1/2.
 * msg ： 美拍列表的 adapter
 */

public class VideoListAdapter extends RecyclerView.Adapter {
    private ArrayList<VideoEntity> mDatas;
    private Context mContext;
    private final int ITEM_TYPE_CONTENT = 0; // 内容布局
    private final int ITEM_TYPE_LOAD_MORE = 1;  // 上拉加载更多布局
    private LoadMoreViewHolder loadMoreViewHolder; // 加载更多布局的 ViewHolder ， 用来控制加载更多的progressBar的显示

    public static final int STATUS_START_LOAD_MORE = 1; // 加载更多开始
    public static final int STATUS_STOP_LOAD_MORE = 2; // 加载更多结束
    private int currLoadMoreStatus;

    public VideoListAdapter(Context mContext, ArrayList<VideoEntity> mDatas) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mDatas.size()) {
            return ITEM_TYPE_CONTENT;
        }else {
            // position == mDatas.size()
            // 是最后一条数据了、应该显示的是上拉加载更多的Item
            return ITEM_TYPE_LOAD_MORE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ITEM_TYPE_CONTENT) {
            // 正常的ViewHolder
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
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
            VideoEntity videoEntity = mDatas.get(position);
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            ImageLoader.picassoWith(mContext, videoEntity.getAvatar(), contentViewHolder.ivHead);
            ImageLoader.picassoWith(mContext, videoEntity.getCover_pic(), contentViewHolder.ivCover);
            contentViewHolder.tvName.setText(videoEntity.getScreen_name());
            contentViewHolder.tvTitle.setText(videoEntity.getCaption());
            contentViewHolder.tvPlayNum.setText(videoEntity.getPlays_count() + "");
            contentViewHolder.tvLikeNum.setText(videoEntity.getLikes_count() + "");
            contentViewHolder.tvCommentNum.setText(videoEntity.getComments_count() + "");

            final int p = position;
            contentViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = mDatas.get(p).getUrl();
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(StaticData.URL, url);
                    mContext.startActivity(intent);
                }
            });
        } else if (itemViewType == ITEM_TYPE_LOAD_MORE) {
            // 上拉加载更多的 Item
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    /**
     * 更新加载更多的状态
     *
     * @param loadMoreStatus
     */
    public void updateLoadMoreStatus(int loadMoreStatus) {
        if (loadMoreViewHolder != null) {
            currLoadMoreStatus = loadMoreStatus;
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
     * 是否可以上拉加载更多
     * @return
     */
    public boolean canLoadMore() {
        return currLoadMoreStatus != STATUS_START_LOAD_MORE;
    }

    /**
     * 显示正常内容的ViewHolder
     */
    private class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHead; // 发布人的头像
        private TextView tvName; // 发布人的昵称
        private ImageView ivCover; // 发布视频图片截图
        private TextView tvTitle; // 发布的视频的 标题
        private TextView tvPlayNum; // 播放数
        private TextView tvLikeNum; // 点赞数
        private TextView tvCommentNum; // 评论数
        private CardView cardView;

        public ContentViewHolder(View itemView) {
            super(itemView);

            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvPlayNum = (TextView) itemView.findViewById(R.id.tv_play_num);
            tvLikeNum = (TextView) itemView.findViewById(R.id.tv_like_num);
            tvCommentNum = (TextView) itemView.findViewById(R.id.tv_comment_num);
            ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
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
