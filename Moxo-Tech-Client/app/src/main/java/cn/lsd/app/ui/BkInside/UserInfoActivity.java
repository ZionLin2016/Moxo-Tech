package cn.lsd.app.ui.BkInside;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import cn.lsd.app.R;
import cn.lsd.app.util.CommonUtil;
import cn.lsd.app.util.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserInfoActivity extends AppCompatActivity {

    @Bind(R.id.actionBar2)
    Toolbar toolbar;
    @Bind(R.id.tv_member_birthday)
    TextView tv_member_birthday;
    @Bind(R.id.tv_member_label)
    TextView tv_member_label;
    @Bind(R.id.tv_member_email)
    TextView tv_member_email;
    @Bind(R.id.tv_native_place)
    TextView tv_native_place;
    @Bind(R.id.bn_contact_bottom)
    BottomNavigationView navigation;
    String phone, email, nativePlace, label, birthday = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        nativePlace = getIntent().getStringExtra("nativePlace");
        label = getIntent().getStringExtra("label");
        birthday = getIntent().getStringExtra("birthday");

        //绑定目标Activity
        ButterKnife.bind(UserInfoActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tv_member_birthday.setText(birthday);
        tv_member_label.setText(label);
        tv_member_email.setText(email);
        tv_native_place.setText(nativePlace);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String phone2 = StringUtils.getCorrectPhone(phone);
            switch (item.getItemId()) {
                case R.id.contact_sms:
                    CommonUtil.toMessageChat(UserInfoActivity.this, phone);
                    return true;
                case R.id.contact_phone:
                    Uri uri = Uri.parse("tel:" + phone.trim());
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    if (ActivityCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(UserInfoActivity.this,"没有权限",Toast.LENGTH_SHORT);
                    }
                    startActivity(intent);
                    return true;
                case R.id.contact_email:
                    CommonUtil.sendEmail(UserInfoActivity.this, phone2 + "@qq.com");
                    return true;

            }
            return false;
        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
