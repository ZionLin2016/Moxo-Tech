package cn.lsd.app.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.lsd.app.AppContext;
import cn.lsd.app.R;
import cn.lsd.app.api.remote.ApiFactory;
import cn.lsd.app.base.BaseObserver;
import cn.lsd.app.model.response.bk.BKResult;
import cn.lsd.app.router.Router;
import cn.lsd.app.ui.groupclass.model.UserInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends Activity {
    static final String TAG = "LoginActivity";

    @Bind(R.id.login_edt_username)
     EditText mEditUserName;
    @Bind(R.id.login_edt_pwd)
     EditText mEditPwd;
    @Bind(R.id.login_btn_login)
     Button mBtnLogin;
    @Bind(R.id.linear_layout_btn_register)
     LinearLayout mLinearRegister;
    @Bind(R.id.login_find_pwd)
     TextView mTextFindPwd;
     boolean autoLogin = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
    }

    protected void sendRequest() {
        //获取数据
        final String username = mEditUserName.getText().toString();
        final String password = mEditPwd.getText().toString();
        //进行客户验证
        if (TextUtils.isEmpty(username)) {
            mEditUserName.setError("用户名不能为空！");
            mEditUserName.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            mEditPwd.setError("密码不能为空！");
            mEditPwd.requestFocus();
        } else {//将用户和密码提交到服务进行验证
            ApiFactory.getUserApi().getLogin(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);

            SharedPreferences sp =  AppContext.getInstance().getSharedPreferences("info", MODE_PRIVATE);
            //拿到SharedPreference的编辑器
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("username", username);
            //editor.putString("passwd", passwd);

            //提交
            editor.commit();
        }

    }

    @OnClick({R.id.login_btn_login, R.id.linear_layout_btn_register, R.id.login_find_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                sendRequest();
                break;
            case R.id.linear_layout_btn_register:
                Router.showReg(LoginActivity.this);
                break;
            case R.id.login_find_pwd:
                break;
        }
    }



    protected BaseObserver subscriber = new BaseObserver<BKResult<UserInfo>>(){
        @Override
        public void onCompleted() {
            Log.v(TAG, "delay onCompleted");
        }

        @Override
        public void onNext(BKResult<UserInfo> bkResult) {
            String jsonData = bkResult.getResults().toString();
            Log.v(TAG, "登录成功，返回：" +jsonData);

//            Gson gson = new Gson();
//            UserInfo userInfo = gson.fromJson(jsonData, UserInfo.class);
            //存入数据库，这里可以用线程异步，以后再说

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Router.showMain(LoginActivity.this);
                }
            }, 200);


        }

        @Override
        public void onError(Throwable error) {
            Log.v(TAG, "delay onError"+error.getMessage());
        }
    };
}
