package px.com.photoselectorlibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;



/**
 *
 * 用户权限获取页面，权限处理
 */

public class PermissionActivity extends Activity {
    //首先声明权限授权
    public static final int PERMISSION_GRANTED = 0;//标识权限授权
    public static final int PERMISSION_DENIEG = 1;//权限不足，权限被拒绝的时候
    private static final int PERMISSION_REQUEST_CODE = 0;//系统授权管理页面时的结果参数
    private static final String EXTRA_PERMISSION = "com.lai.permissiondemo";//权限参数
    private static final String PACKAGE_URL_SCHEME = "package:";//权限方案
    private CheckPermission checkPermission;//检测权限类的权限检测器
    private boolean isrequestCheck;//判断是否需要系统权限检测。防止和系统提示框重叠

    //启动当前权限页面的公开接口
    public static void startActivityForResult(Activity activity, int requestCode, String... permission) {
        Intent intent = new Intent(activity, PermissionActivity.class);
        intent.putExtra(EXTRA_PERMISSION, permission);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_layout);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSION))//如果参数不等于配置的权限参数时
        {
            throw new RuntimeException("当前Activity需要使用静态的StartActivityForResult方法启动");//异常提示
        }
        checkPermission = new CheckPermission(this);
        isrequestCheck = true;//改变检测状态
    }

    //检测完之后请求用户授权
    @Override
    protected void onResume() {
        super.onResume();
        if (isrequestCheck) {
            String[] permission = getPermissions();
            if (checkPermission.permissionSet(permission)) {
                requestPermissions(permission);     //去请求权限
            } else {
                allPermissionGranted();//获取全部权限
            }
        } else {
            isrequestCheck = true;
        }
    }

    //获取全部权限
    private void allPermissionGranted() {
        setResult(PERMISSION_GRANTED);
        finish();
    }

    //请求权限去兼容版本
    private void requestPermissions(String... permission) {
        ActivityCompat.requestPermissions(this, permission, PERMISSION_REQUEST_CODE);
    }

    //返回传递过来的权限参数
    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSION);
    }


    /**
     * 用于权限管理
     * 如果全部授权的话，则直接通过进入
     * 如果权限拒绝，缺失权限时，则使用dialog提示
     *
     * @param requestCode  请求代码
     * @param permissions  权限参数
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PERMISSION_REQUEST_CODE == requestCode && hasAllPermissionGranted(grantResults)) //判断请求码与请求结果是否一致
        {
            isrequestCheck = true;//需要检测权限，直接进入，否则提示对话框进行设置
            allPermissionGranted(); //进入
        } else {   //提示对话框设置
            isrequestCheck = false;
            showMissingPermissionDialog();//dialog
        }

    }

    //显示对话框提示用户缺少权限
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
        builder.setTitle(R.string.help);//提示帮助
        builder.setMessage(R.string.string_help_text);

        //如果是拒绝授权，则退出应用
        //退出
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(PERMISSION_DENIEG);//权限不足
                finish();
            }
        });
        //打开设置，让用户选择打开权限
        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();//打开设置
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    //获取全部权限
    private boolean hasAllPermissionGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }


    //打开系统应用设置(ACTION_APPLICATION_DETAILS_SETTINGS:系统设置权限)
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }


}
