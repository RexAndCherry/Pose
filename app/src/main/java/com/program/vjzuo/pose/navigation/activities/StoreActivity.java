package com.program.vjzuo.pose.navigation.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.program.vjzuo.pose.R;
import com.program.vjzuo.pose.navigation.fragments.StoreFragment;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.ui.fragments.FavoritesFragment;

public class StoreActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FavoritesFragment fragment = new StoreFragment();
        fragment.setOnResultListener(new Listeners.OnResultListener() {
            @Override
            public void onResult(int i) {

            }
        });

        ft.add(R.id.frag_container,fragment);
        ft.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }
}
