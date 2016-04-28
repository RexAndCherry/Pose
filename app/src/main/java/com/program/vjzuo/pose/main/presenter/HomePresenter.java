package com.program.vjzuo.pose.main.presenter;

import android.content.Context;
import android.content.Intent;

import com.program.vjzuo.pose.R;
import com.program.vjzuo.pose.main.HomeContract;
import com.program.vjzuo.pose.utils.LogUtils;
import com.program.vjzuo.pose.utils.ToastUtils;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.nets.responses.FeedsResponse;
import com.umeng.comm.ui.activities.FeedDetailActivity;

/**
 * Created by vj.zuo on 2016/4/21.
 */
public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mView;
    private final Context mContext;
    private CommunitySDK mCommSdk;
    private String mNextPageUrl;

    public HomePresenter(HomeContract.View view, Context context) {
        mView = view;
        mContext = context;

        start();
    }

    @Override
    public void loadLastestFeeds() {
        mCommSdk.fetchLastestFeeds(new Listeners.FetchListener<FeedsResponse>() {
            @Override
            public void onStart() {
                mView.indicateLoading(true);
            }

            @Override
            public void onComplete(FeedsResponse feedsResponse) {

                mView.updateList(feedsResponse.result);
                mView.indicateLoading(false);

                if (mNextPageUrl == null) {
                    mNextPageUrl = feedsResponse.nextPageUrl;
                }
            }
        });
    }

    @Override
    public void loadMore() {
        LogUtils.i("loadMore:"+mNextPageUrl);
        if (mNextPageUrl!=null&&mNextPageUrl.length()>0){

            mCommSdk.fetchNextPageData(mNextPageUrl, FeedsResponse.class,
                    new Listeners.FetchListener<FeedsResponse>() {

                        @Override
                        public void onStart() {
                            mView.indicateLoading(true);
                        }

                        @Override
                        public void onComplete(FeedsResponse response) {
                            if (response.result == null || response.result.size() == 0) {
                                mView.onNoMore();
                            } else {
                                mView.onLoadMore(response.result);
                                mNextPageUrl = response.nextPageUrl;
                            }
                            mView.indicateLoading(false);
                        }
                    });
        }else {
            ToastUtils.showShort(mContext, R.string.load_all_pose);
        }
    }

    @Override
    public void onItemClick(FeedItem feedItem) {
        Intent intent = new Intent(mContext, FeedDetailActivity.class);
        intent.putExtra("feed_id", feedItem.id);
        mContext.startActivity(intent);
    }

    @Override
    public void start() {
        mCommSdk = CommunityFactory.getCommSDK(mContext);
    }
}
