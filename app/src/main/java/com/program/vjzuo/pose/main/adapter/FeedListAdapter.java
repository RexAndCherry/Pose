package com.program.vjzuo.pose.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.program.vjzuo.pose.R;
import com.program.vjzuo.pose.utils.ImageUtils;
import com.program.vjzuo.pose.widget.RatioImageView;
import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.beans.ImageItem;

import java.util.List;

/**
 * Created by vj.zuo on 2016/4/21.
 */
public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.Holder> {

    private final Context mContext;
    private List<FeedItem> mFeedItems;
    private OnItemClickListener mOnItemClickListener;

    public FeedListAdapter(Context context, List<FeedItem> feedItems) {

        mContext = context;
        mFeedItems = feedItems;

    }

    public void setDatas(List<FeedItem> mFeedItems) {
        this.mFeedItems = mFeedItems;
    }

    public List<FeedItem> getDatas() {
        return mFeedItems;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new Holder(v);

    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final FeedItem feedItem = mFeedItems.get(position);
        holder.feedItem = feedItem;

        holder.titleView.setText(feedItem.creator.name + " : " + feedItem.text);
        holder.card.setTag(feedItem.text);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(feedItem);
                }
            }
        });
        List<ImageItem> images = feedItem.getImages();
        if (images != null && images.size() > 0) {
            holder.feedImage.setVisibility(View.VISIBLE);
            ImageUtils.displayImgByUrl(images.get(0).middleImageUrl, holder.feedImage);
        } else {
            holder.feedImage.setVisibility(View.GONE);
        }

    }

    public void addTooEnd(List<FeedItem> feedItems) {
        mFeedItems.addAll(feedItems);
    }


    @Override
    public int getItemCount() {
        return mFeedItems == null ? 0 : mFeedItems.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        View card;
        FeedItem feedItem;
        RatioImageView feedImage;
        TextView titleView;

        public Holder(View itemView) {
            super(itemView);
            card = itemView;
            feedImage = (RatioImageView) itemView.findViewById(R.id.iv_feed);
            titleView = (TextView) itemView.findViewById(R.id.tv_title);

            feedImage.setOriginalSize(50, 50);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(FeedItem feedItem);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}

