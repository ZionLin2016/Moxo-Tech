package com.closedevice.fastapp.ui.groupclass.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.closedevice.fastapp.R;
import com.closedevice.fastapp.base.ui.BaseFragment;
import com.closedevice.fastapp.router.Router;
import com.closedevice.fastapp.ui.common.CustomPopWindow;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FillClassFragment extends BaseFragment implements View.OnClickListener {

    private static final int PHOTO_REQUEST_GALLERY = 1;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 2;// 结果
    CustomPopWindow popWindow;

    @Bind(R.id.ly_root_main)
    LinearLayout ly_root_main;
    @Bind(R.id.iv_class_cover)
    ImageView iv_calss_cover;
    @Bind(R.id.et_class_name)
    EditText et_class_name;
    @Bind(R.id.ly_course_name)
    LinearLayout ly_course_name;
    @Bind(R.id.tv_course_name)
    TextView tv_course_name;
    @Bind(R.id.rg_class_type)
    RadioGroup rg_class_type;
    @Bind(R.id.rb_bk)
    RadioButton rb_bk;
    @Bind(R.id.rb_other_bk)
    RadioButton rb_other_bk;
    @Bind(R.id.ly_class_textbook)
    LinearLayout ly_class_textbook;
    @Bind(R.id.tv_textbook_set)
    TextView tv_textbook_set;
    @Bind(R.id.ly_class_detail)
    LinearLayout ly_class_detail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initData();
        initView(view);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fill_class;
    }

    @Override
    public void initData() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.iv_class_cover, R.id.ly_course_name, R.id.tv_course_name, R.id.ly_class_textbook, R.id.tv_textbook_set, R.id.ly_class_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_class_cover:
                showPopMenu();
                break;
            case R.id.ly_course_name:
                Router.inputClassName(getActivity());
                break;
            case R.id.tv_course_name:
                break;
            case R.id.ly_class_textbook:
                Router.setTextBook(getActivity());
                break;
            case R.id.tv_textbook_set:
                break;
            case R.id.ly_class_detail:
                Router.setClassDetail(getActivity());
            default:
                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopMenu() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_bottom_alpha,null);
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
                    case R.id.tv_photo_album:
                        selectPhotoFromGallery();
                        break;
                    case R.id.tv_photo_default:
                        setDefaultPic();
                        break;
                    case R.id.tv_cancel:
                        popWindow.dissmiss();
                        break;

                }
            }
        };
        contentView.findViewById(R.id.tv_photo_album).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_photo_default).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(listener);

    }

    private void selectPhotoFromGallery() {
        //调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常
                if (data != null)
                    startPhotoZoom(data.getData(), 150);
                break;
            case PHOTO_REQUEST_CUT:// 返回的结果
                if (data != null)
                    saveAndsetPic(data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    private void saveAndsetPic(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            iv_calss_cover.setImageDrawable(drawable);
            //file.delete();//设置成功后清除之前的照片文件
        }
    }

    private void setDefaultPic() {
        iv_calss_cover.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.default_cover));
    }








//    @Override
//    public void initView(View view) {
//        super.initView(view);
//        // 定义拍照后存放图片的文件位置和名称，使用完毕后可以方便删除
//        file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
//        file.delete();// 清空之前的文件
//    }

//    private void saveAndsetPic(Intent picdata) {
//        Bundle bundle = picdata.getExtras();
//        if (bundle != null) {
//            Bitmap photo = bundle.getParcelable("data");
//            //保存到SD卡
//            String imageName = System.currentTimeMillis() + "";
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            byte[] p = baos.toByteArray();
//            new File.writeBytes(SDCardPath + "/imageCache", imageName, p);
//            //将图片名字保存带本地
//            SharedPreferences sp = getActivity().getSharedPreferences("imageName", MODE_PRIVATE);
//            sp.edit().putString("name", imageName).commit();
//
//            //设置到界面
//            Drawable drawable = new BitmapDrawable(photo);
//            iv_calss_cover.setBackgroundDrawable(drawable);
//        }
//    }

}
