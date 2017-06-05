package com.closedevice.fastapp.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.closedevice.fastapp.R;

import com.closedevice.fastapp.ui.BkInside.fragment.ShowDetailFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.ShowFunctionFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.ShowMemberFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.ShowNotifyFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.ShowResourcesFragment;
import com.closedevice.fastapp.ui.groupclass.model.DetailListBean;
import com.closedevice.fastapp.ui.groupclass.model.EnumEventType;
import com.closedevice.fastapp.ui.groupclass.model.MessageEvent;
import com.google.gson.Gson;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BkInsideActivity extends AppCompatActivity {

    @Bind(R.id.navigation)
    BottomNavigationView navigation;
    @Bind(R.id.actionBar2)
    Toolbar toolbar;


    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_file:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.bk_inside_container, new ShowResourcesFragment())
                            .commit();
                    return true;
                case R.id.navigation_member:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.bk_inside_container, new ShowMemberFragment())
                            .commit();
                    return true;
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.bk_inside_container, new ShowFunctionFragment())
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.bk_inside_container, new ShowNotifyFragment())
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.bk_inside_container, new ShowDetailFragment())
                            .commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bk_inside);
        //绑定目标Activity
        ButterKnife.bind(BkInsideActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bk_inside_container, new ShowResourcesFragment())
                .commit();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void replaceFragment(Fragment newFragment) {

        FragmentTransaction trasection = getFragmentManager().beginTransaction();
        try {
            getFragmentManager().beginTransaction();
            trasection.replace(R.id.bk_inside_container, newFragment);
            trasection.addToBackStack(null);
            trasection.commit();

        } catch (Exception e) {
            // TODO: handle exception

        }
    }

//    private void changeTab(int index) {
//
//
//        //判断此Fragment是否已经添加到FragmentTransaction事物中
//        if (!fragment.isAdded()) {
//            ft.add(R.id.fragment, fragment, fragment.getClass().getName());
//        } else {
//            ft.show(fragment);
//        }
//        ft.commit();
//    }

}
