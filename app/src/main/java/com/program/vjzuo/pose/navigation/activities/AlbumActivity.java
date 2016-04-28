package com.program.vjzuo.pose.navigation.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.program.vjzuo.pose.R;
import com.umeng.comm.core.beans.ImageItem;
import com.umeng.comm.core.imageloader.cache.ImageCache;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.adapters.FeedImageAdapter;
import com.umeng.comm.ui.imagepicker.adapters.UserAlbumAdapter;
import com.umeng.comm.ui.imagepicker.dialogs.ImageBrowser;
import com.umeng.comm.ui.imagepicker.mvpview.MvpAlbumView;
import com.umeng.comm.ui.imagepicker.presenter.impl.AlbumPresenter;
import com.umeng.comm.ui.imagepicker.util.ViewFinder;
import com.umeng.comm.ui.imagepicker.widgets.RefreshGvLayout;
import com.umeng.comm.ui.imagepicker.widgets.RefreshLayout;

import java.util.List;

/**
 * Created by vj.zuo on 2016/4/25.
 */
public class AlbumActivity extends AppCompatActivity implements MvpAlbumView {


    RefreshGvLayout mRefreshGvLayout;
    FeedImageAdapter mImageAdapter;
    GridView mAlbumGridView;
    AlbumPresenter mPresenter;
    ImageBrowser mImageBrowser;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(ResFinder.getLayout("umeng_commm_album_layout"));
        this.initTitleLayout();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.mImageBrowser = new ImageBrowser(this);
        this.mPresenter = new AlbumPresenter(this.getIntent().getStringExtra("user_id"), this);
        this.mPresenter.attach(this.getApplicationContext());
        findViewById(R.id.umeng_comm_title_bar_root).setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initTitleLayout() {
        ViewFinder viewFinder = new ViewFinder(this.getWindow().getDecorView());
        Button settingButton = (Button) viewFinder.findViewById(ResFinder.getId("umeng_comm_save_bt"));
        settingButton.setVisibility(4);
        this.findViewById(ResFinder.getId("umeng_comm_setting_back")).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlbumActivity.this.finish();
            }
        });
        TextView titleTextView = (TextView) this.findViewById(ResFinder.getId("umeng_comm_setting_title"));
        titleTextView.setText("我的图册");
        this.mRefreshGvLayout = (RefreshGvLayout) viewFinder.findViewById(ResFinder.getId("umeng_comm_album_swipe_layout"));
        this.mRefreshGvLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                AlbumActivity.this.mPresenter.loadDataFromServer();
            }
        });
        this.mRefreshGvLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            public void onLoad() {
                AlbumActivity.this.mPresenter.loadMore();
            }
        });
        this.mAlbumGridView = (GridView) viewFinder.findViewById(ResFinder.getId("umeng_comm_user_albun_gv"));
        this.mAlbumGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlbumActivity.this.jumpToImageBrowser(AlbumActivity.this.mImageAdapter.getDataSource(), position);
            }
        });
        this.mImageAdapter = new UserAlbumAdapter(this.getApplicationContext());
        this.mAlbumGridView.setAdapter(this.mImageAdapter);
    }

    private void jumpToImageBrowser(List<ImageItem> images, int position) {
        this.mImageBrowser.setImageList(images, position);
        this.mImageBrowser.show();
    }

    public void fetchedAlbums(List<ImageItem> imageItems) {
        this.mImageAdapter.addData(imageItems);
        this.mRefreshGvLayout.setRefreshing(false);
        this.mRefreshGvLayout.setLoading(false);
    }

    public List<ImageItem> getBindDataSource() {
        return this.mImageAdapter.getDataSource();
    }

    protected void onResume() {
        super.onResume();
        this.mRefreshGvLayout.setRefreshing(true);
    }

    protected void onDestroy() {
        super.onDestroy();
        ImageCache.getInstance().clearLruCache();
        this.mPresenter.onDestroy();
    }
}
