package cn.lsd.app.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.lsd.app.AppManager;
import cn.lsd.app.R;
import cn.lsd.app.router.Router;
import cn.lsd.app.util.SystemBarTintManager;
import cn.lsd.app.view.TypeTextView;

import java.util.Random;

public class AppStart extends Activity {

    private static final long TIME_DELAY = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Activity activity = AppManager.getActivity(LoginActivity.class);
        if (activity != null && !activity.isFinishing()) {
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        View root = findViewById(R.id.activity_app_start);
        if (root != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.transparent);
        }


        TypeTextView ttvSay = (TypeTextView) findViewById(R.id.ttv_say);
        ttvSay.start(getRandomSentence());
        ttvSay.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {

            }

            @Override
            public void onTypeOver() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Router.showMain(AppStart.this);
                        Router.showLogin(AppStart.this);
                    }
                }, TIME_DELAY);
            }
        });


    }


    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    private String getRandomSentence() {
        String[] array = getResources().getStringArray(R.array.sentence);
        int index = new Random().nextInt(array.length);
        return array[index];
    }


}
