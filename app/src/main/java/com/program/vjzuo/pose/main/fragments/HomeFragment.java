package com.program.vjzuo.pose.main.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.program.vjzuo.pose.R;
import com.program.vjzuo.pose.main.HomeContract;
import com.program.vjzuo.pose.main.adapter.FeedListAdapter;
import com.program.vjzuo.pose.utils.ToastUtils;
import com.umeng.comm.core.beans.FeedItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeContract.View {


    private HomeContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedListAdapter mFeedListAdapter;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.fragment_home, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view_home);

        setUpSwiperLayout();
        setupRecyclerView();

        return contentView;
    }

    private void setUpSwiperLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadLastestFeeds();
            }
        });
    }

    private void setupRecyclerView() {
        final StaggeredGridLayoutManager layoutManager
                = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(getOnBottomListener(layoutManager));
    }

    RecyclerView.OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                int[] lastPos = layoutManager.findLastCompletelyVisibleItemPositions(
                        new int[2]);
                boolean isBottom =
                        lastPos[1] >=
                                mFeedListAdapter.getItemCount()-1 || lastPos[0] >= mFeedListAdapter.getItemCount()-1;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
                    mPresenter.loadMore();
                }
            }
        };
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateList(List<FeedItem> items) {
        if (mFeedListAdapter == null) {
            mFeedListAdapter = new FeedListAdapter(getActivity(), items);
            mFeedListAdapter.setOnItemClickListener(new FeedListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(FeedItem feedItem) {
                    mPresenter.onItemClick(feedItem);
                }
            });
            mRecyclerView.setAdapter(mFeedListAdapter);
        } else {
            mFeedListAdapter.setDatas(items);
            mFeedListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void indicateLoading(boolean active) {
        if (mSwipeRefreshLayout.isRefreshing() != active) {
            mSwipeRefreshLayout.setRefreshing(active);
        }
    }

    @Override
    public void onLoadMore(List<FeedItem> items) {
        mFeedListAdapter.addTooEnd(items);
        mFeedListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoMore() {
        ToastUtils.showShort(getActivity(), R.string.load_all_pose);
    }
}
