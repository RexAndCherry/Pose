package com.program.vjzuo.pose.main.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.program.vjzuo.pose.R;
import com.program.vjzuo.pose.main.MainContract;
import com.program.vjzuo.pose.main.fragments.HomeFragment;
import com.program.vjzuo.pose.main.presenter.HomePresenter;
import com.program.vjzuo.pose.main.presenter.MainPresenter;
import com.program.vjzuo.pose.utils.ImageUtils;
import com.program.vjzuo.pose.utils.LogUtils;
import com.program.vjzuo.pose.utils.ToastUtils;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.socialize.sso.UMQQSsoHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    private NavigationView mNavView;
    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFab();
        initDrawer(toolbar);

        mMainPresenter = new MainPresenter(this, this);
        mMainPresenter.configLogin();
        mMainPresenter.login();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment fragment = new HomeFragment();
        HomePresenter homePresenter = new HomePresenter(fragment, this);
        fragment.setPresenter(homePresenter);
        ft.add(R.id.frag_container, fragment);
        ft.commit();

        homePresenter.loadLastestFeeds();
    }


    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPresenter.postFeed();
            }
        });
    }


    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mMainPresenter.initUserInfo();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            closeMenu();
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_store:
                mMainPresenter.onNavStoreClick();
                break;
            case R.id.nav_gallery:
                mMainPresenter.onNavGallaryClick();
                break;
            case R.id.nav_check_update:
                mMainPresenter.onNavUpdateClick();
                break;
            case R.id.nav_share:
                mMainPresenter.onNavShareClick();
                break;
            case R.id.nav_about:
                mMainPresenter.onNavAboutClick();
                break;
            case R.id.nav_setting:
                mMainPresenter.onNavSettingClick();
                break;
            case R.id.nav_logout:
                mMainPresenter.onNavLogoutClick();
                break;
        }
        closeMenu();
        return true;
    }

    @Override
    public void closeMenu() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setUserImgUrl(String imgUrl) {
        View headerView = mNavView.getHeaderView(0);
        ImageView imgUser = (ImageView) headerView.findViewById(R.id.image_view_nav_header);
        ImageUtils.displayImgByUrl(imgUrl, imgUser);
    }

    @Override
    public void setUserName(String userName) {
        View headerView = mNavView.getHeaderView(0);
        TextView tvName = (TextView) headerView.findViewById(R.id.tv_name_nav_header);
        tvName.setText(userName);
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }
}
