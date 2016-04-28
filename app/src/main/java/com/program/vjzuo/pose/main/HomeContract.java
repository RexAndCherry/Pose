package com.program.vjzuo.pose.main;

import com.program.vjzuo.pose.BasePresenter;
import com.program.vjzuo.pose.BaseView;
import com.umeng.comm.core.beans.FeedItem;

import java.util.List;

/**
 * Created by vj.zuo on 2016/4/21.
 */
public class HomeContract {

    public interface Presenter extends BasePresenter{

        void loadLastestFeeds();

        void loadMore();

        void onItemClick(FeedItem feedItem);
    }

    public interface View extends BaseView<Presenter>{

        void updateList(List<FeedItem> items);

        void indicateLoading(boolean active);

        void onLoadMore(List<FeedItem> items);

        void onNoMore();
    }
}
