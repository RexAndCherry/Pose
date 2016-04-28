package com.program.vjzuo.pose.navigation.fragments;

import android.view.View;

import com.program.vjzuo.pose.R;
import com.umeng.comm.ui.fragments.FavoritesFragment;

/**
 * Created by vj.zuo on 2016/4/25.
 */
public class StoreFragment extends FavoritesFragment{

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mRootView.findViewById(R.id.umeng_comm_friend_id).setVisibility(View.GONE);
    }
}
