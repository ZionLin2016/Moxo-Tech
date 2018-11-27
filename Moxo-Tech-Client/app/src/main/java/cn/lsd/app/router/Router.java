package cn.lsd.app.router;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import cn.lsd.app.SimpleBackActivity;
import cn.lsd.app.model.response.bk.BKItem;
import cn.lsd.app.service.DownloadService;
import cn.lsd.app.service.ICallbackResult;
import cn.lsd.app.ui.LoginActivity;
import cn.lsd.app.ui.MainActivity;
import cn.lsd.app.ui.RegisterActivity;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;


public class Router {

    public static void showSimpleBack(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        context.startActivity(intent);
    }

    public static void showSimpleBack(Context context, SimpleBackPage page,
                                      Bundle args) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        context.startActivity(intent);
    }

    public static void showSimpleBackForResult(Fragment fragment,
                                               int requestCode, SimpleBackPage page, Bundle args) {
        Intent intent = new Intent(fragment.getActivity(),
                SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void showSimpleBackForResult(Activity context,
                                               int requestCode, SimpleBackPage page, Bundle args) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        context.startActivityForResult(intent, requestCode);
    }

    public static void showSimpleBackForResult(Activity context,
                                               int requestCode, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getId());
        context.startActivityForResult(intent, requestCode);
    }

    public static void showDetail(Activity activity, BKItem data) {
        Bundle bundle = new Bundle();
        bundle.putString("className", data.getClass_name());
        bundle.putString("courseName", data.getCourse_name());
        bundle.putString("picUrl", data.getCover_address());
        bundle.putString("userName", data.getUsername());
        bundle.putString("classType", data.getClass_type());
        bundle.putString("code", data.getInvite_code());
        bundle.putString("aims", data.getStudy_aims());
        bundle.putString("syllabus", data.getSyllabus());
        bundle.putString("schedule", data.getExam_schedule());

        showSimpleBack(activity, SimpleBackPage.DETAIL, bundle);
    }


    public static void startDownloadService(Context context, String url, String tilte) {
        final ICallbackResult callback = new ICallbackResult() {

            @Override
            public void OnBackResult(Object s) {
            }
        };
        ServiceConnection conn = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                binder.addCallback(callback);
                binder.start();

            }
        };
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL, url);
        intent.putExtra(DownloadService.BUNDLE_KEY_TITLE, tilte);
        context.startService(intent);
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);

    }

    public static void showLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void showReg(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void showMain(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void fillBkMsgClass(FragmentActivity activity) {
        showSimpleBack(activity, SimpleBackPage.ADD_CLASS);
    }
    public static void inputCodeClass(FragmentActivity activity) {
        showSimpleBack(activity, SimpleBackPage.INPUT_CODE);
    }

    public static void inputClassName(FragmentActivity activity){
        showSimpleBack(activity, SimpleBackPage.CLASS_NAME);
    }
    public static void setTextBook(FragmentActivity activity) {
        showSimpleBack(activity, SimpleBackPage.TEXTBOOK);
    }

    public static void setClassDetail(FragmentActivity activity) {
        showSimpleBack(activity, SimpleBackPage.SET_CLASS_DETAIL);
    }

    public static void uploadFromPc(FragmentActivity activity){
        showSimpleBack(activity, SimpleBackPage.UPLOAD_FROM_PC);
    }

    public static void uploadUrl(FragmentActivity activity){
        showSimpleBack(activity, SimpleBackPage.UPLOAD_URL);
    }
    public static void uploadDoc(FragmentActivity activity){
        showSimpleBack(activity, SimpleBackPage.UPLOAD_DOC);
    }

    public static void addNotify(FragmentActivity activity){
        showSimpleBack(activity, SimpleBackPage.ADD_NOTIFY);
    }

    public static void setUrlAddress(FragmentActivity activity){
        showSimpleBack(activity, SimpleBackPage.SET_URL_ADDRESS);
    }

    public static void setUrlTitle(FragmentActivity activity){
        showSimpleBack(activity, SimpleBackPage.SET_URL_TITLE);
    }

    public static void setUrlGroup(FragmentActivity activity){
        showSimpleBack(activity, SimpleBackPage.SET_URL_GROUP);
    }

    public static void setUrlAim(FragmentActivity activity){
        showSimpleBack(activity, SimpleBackPage.SET_URL_AIM);
    }

    public static void setUrlDemand(FragmentActivity activity){
        showSimpleBack(activity, SimpleBackPage.SET_URL_DEMAND);
    }
//    public static void showClassDetail(FragmentActivity activity){
//        showSimpleBack(activity, SimpleBackPage.SHOW_CLASS_DETAIL);;
//    }
}
