package com.program.vjzuo.pose.main;

import com.program.vjzuo.pose.BasePresenter;
import com.program.vjzuo.pose.BaseView;

/**
 * Created by vj.zuo on 2016/4/25.
 * MainActivity总控全局
 */
public class MainContract {
    public interface Presenter extends BasePresenter {
        boolean checkLogin();

        void login();

        void configLogin();

        void initUserInfo();

        void onNavStoreClick();

        void onNavGallaryClick();

        void onNavUpdateClick();

        void onNavShareClick();

        void onNavAboutClick();

        void onNavSettingClick();

        void onNavLogoutClick();

        void postFeed();
    }

    public interface View extends BaseView<Presenter> {
        void closeMenu();

        void setUserImgUrl(String imgUrl);

        void setUserName(String userName);
    }

}
