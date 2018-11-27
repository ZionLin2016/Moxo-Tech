package cn.lsd.app.ui.BkInside.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lsd.app.AppConstant;
import cn.lsd.app.R;
import cn.lsd.app.base.ui.BaseFragment;
import cn.lsd.app.model.uploadFile.UploadList;
import cn.lsd.app.model.uploadFile.UploadMsg;
import cn.lsd.app.router.Router;
import cn.lsd.app.ui.common.CustomPopWindow;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import px.com.photoselectorlibrary.ImageSelectorListActivity;


public class ShowResourcesFragment extends BaseFragment {

    @Bind(R.id.ly_root_main)
    LinearLayout ly_root_main;
    @Bind(R.id.ly_upload_pic)
    LinearLayout ly_upload_pic;
    @Bind(R.id.ly_upload_pc)
    LinearLayout ly_upload_pc;
    @Bind(R.id.ly_upload_url)
    LinearLayout ly_upload_url;
    @Bind(R.id.ly_upload_doc)
    LinearLayout getLy_upload_doc;
    @Bind(R.id.tv_pic_address)
    TextView tv_pic_address;
    @Bind(R.id.iv_image1)
    ImageView iv_image1;

    CustomPopWindow popWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initData();
        initView(view);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_show_resources;
    }

    @Override
    public void initView(View view) {

        ButterKnife.bind(this, view);
    }


    @OnClick({R.id.ly_upload_pic, R.id.ly_upload_pc, R.id.ly_upload_url, R.id.ly_upload_doc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_upload_pic:
                showPopMenu();
                break;
            case R.id.ly_upload_pc:
                Router.uploadFromPc(getActivity());
                break;
            case R.id.ly_upload_url:
                Router.uploadUrl(getActivity());
                break;
            case R.id.ly_upload_doc:
                Router.uploadDoc(getActivity());
                break;
        }
    }

    private void showPopMenu() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_bottom_select_pic,null);
        handleLogic(contentView);
        popWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)//显示的布局，还可以通过设置一个View
                //.size(800,1600) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .create()//创建PopupWindow
                .showAtLocation(ly_root_main, Gravity.BOTTOM,0,0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popWindow!=null){
                    popWindow.dissmiss();
                }
                switch (v.getId()){
                    case R.id.tv_single_photo:
                        Intent it = new Intent(getActivity(), ImageSelectorListActivity.class);
                        it.putExtra("type", 1);
                        startActivityForResult(it, ImageSelectorListActivity.RESULT_OK);

                        break;
                    case R.id.tv_multiple_photo:
                        Intent it1 = new Intent(getActivity(), ImageSelectorListActivity.class);
                        it1.putExtra("type", 2);
                        startActivityForResult(it1, ImageSelectorListActivity.RESULT_OK);
                        break;
                    case R.id.tv_cancel:
                        popWindow.dissmiss();
                        break;

                }
            }
        };
        contentView.findViewById(R.id.tv_single_photo).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_multiple_photo).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(listener);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //接收返回的图片路径


        if (requestCode == 10001) {
            if (data == null) return;
            ArrayList<String> filePath = data.getStringArrayListExtra("filePath");
            try {
                //测试只显示一张
                Bitmap bitmap = BitmapFactory.decodeFile(filePath.get(0));
                //图片显示在iv_image
                iv_image1.setImageBitmap(bitmap);
                //List<UploadMsg> files = new UpTaskUtils().upTask(filePath);
                upTask(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String str1 = "";
            for (String str : filePath) {
                str1 = str1 + str + "/n";
            }
            tv_pic_address.setText(str1 + "0");
        }
    }

    private void upTask(ArrayList<String> filePath) throws Exception {
        ArrayList<String> arrayList = filePath;

        //异步的客户端对象
        AsyncHttpClient client = new AsyncHttpClient();
        //指定url路径
        String url = AppConstant.UPLOAD_URL;
        //封装文件上传的参数
        RequestParams params = new RequestParams();

        for (int i = 0; i < arrayList.size(); i++) {
            File file = new File(arrayList.get(0).toString());
            try {
                params.put("profile_picture", file);
                //执行post请求
                executePost(client, url, params);

            } catch (Exception e) {
                System.out.println("文件不存在----------");
            }

        }
    }

    private void executePost(AsyncHttpClient client, String url, RequestParams params) {
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    //获取结果
                    try {
                        String result = new String(responseBody, "utf-8").toString();
                        Log.i("TAG", "------------------" + result);
                        Gson gson = new Gson();
                        UploadList ul = gson.fromJson(result, UploadList.class);
                        List<UploadMsg> msgList = ul.getContent();
                       Log.i("ShowResources","--------------"+msgList.toString());

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }

        });
    }
}
