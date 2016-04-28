package com.program.vjzuo.pose.main.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.program.vjzuo.pose.R;
import com.program.vjzuo.pose.main.MainContract;
import com.program.vjzuo.pose.main.activities.PostFeedActivity;
import com.program.vjzuo.pose.navigation.activities.AboutActivity;
import com.program.vjzuo.pose.navigation.activities.AlbumActivity;
import com.program.vjzuo.pose.navigation.activities.StoreActivity;
import com.program.vjzuo.pose.utils.ToastUtils;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.ui.imagepicker.activities.SettingActivity;
import com.umeng.socialize.sso.UMQQSsoHandler;

/**
 * Created by vj.zuo on 2016/4/25.
 */
public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;
    private final Context mContext;
    private boolean isUserInfoLoaded;
    private CommunitySDK mCommSdk;

    public MainPresenter(MainContract.View mView, Context mContext) {

        this.mContext = mContext;
        this.mView = mView;
        start();
    }


    @Override
    public boolean checkLogin() {
        return CommonUtils.isLogin(mContext);
    }

    @Override
    public void login() {
        if (!checkLogin()) {

            mCommSdk.login(mContext, new LoginListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onComplete(int i, CommUser commUser) {
                    if (commUser != null) {
                        ToastUtils.showShort(mContext, R.string.login_success);
                    } else {
                        ToastUtils.showShort(mContext, R.string.login_failed);
                    }
                }
            });
        }
    }

    @Override
    public void start() {
        mCommSdk = CommunityFactory.getCommSDK(mContext);
    }

    @Override
    public void configLogin() {
        configQQLogin();
    }

    @Override
    public void initUserInfo() {
        if (!isUserInfoLoaded) {
            try {

                if (checkLogin()) {
                    CommUser loginUser = CommConfig.getConfig().loginedUser;
                    mView.setUserImgUrl(loginUser.iconUrl);
                    mView.setUserName(loginUser.name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            isUserInfoLoaded = true;
        }
    }

    @Override
    public void onNavStoreClick() {
        Intent intent = new Intent(mContext, StoreActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void onNavGallaryClick() {
        Intent intent = new Intent(mContext, AlbumActivity.class);
        intent.putExtra("user_id", mCommSdk.getConfig().loginedUser.id);
        mContext.startActivity(intent);
    }

    @Override
    public void onNavUpdateClick() {
        ToastUtils.showShort(mContext, "onNavUpdateClick");
    }

    @Override
    public void onNavShareClick() {
        ToastUtils.showShort(mContext, "onNavUpdateClick");
    }

    @Override
    public void onNavAboutClick() {
        Intent intent = new Intent(mContext, AboutActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void onNavSettingClick() {
        Intent setting1 = new Intent(mContext, SettingActivity.class);
//        setting1.putExtra("type_class", this.mContainerClass);
        mContext.startActivity(setting1);
        ToastUtils.showShort(mContext, "onNavSettingClick");
    }

    @Override
    public void onNavLogoutClick() {
        mCommSdk.logout(mContext, new LoginListener() {
            @Override
            public void onStart() {

                ToastUtils.showShort(mContext, "正在调整优美的登出姿势");
            }

            @Override
            public void onComplete(int i, CommUser commUser) {
                ToastUtils.showShort(mContext, "登出的姿势完美");

                login();
                ((Activity) mContext).finish();
            }
        });
    }

    @Override
    public void postFeed() {
        Intent intent = new Intent(mContext, PostFeedActivity.class);
        mContext.startActivity(intent);
    }

    private void configQQLogin() {
        String appId = "1105266757";
        String appKey = "GK5y88UGsfICqk9M";
        new UMQQSsoHandler((Activity) mContext, appId, appKey).addToSocialSDK();
    }
}
