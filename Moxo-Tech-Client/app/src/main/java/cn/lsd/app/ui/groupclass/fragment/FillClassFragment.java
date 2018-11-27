package cn.lsd.app.ui.groupclass.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.lsd.app.AppConstant;
import cn.lsd.app.AppContext;
import cn.lsd.app.R;
import cn.lsd.app.api.remote.ApiFactory;
import cn.lsd.app.base.BaseObserver;
import cn.lsd.app.base.ui.BaseFragment;
import cn.lsd.app.gen.BkRecordDao;
import cn.lsd.app.gen.DaoMaster;
import cn.lsd.app.gen.DaoSession;
import cn.lsd.app.model.record.BkRecord;
import cn.lsd.app.model.response.bk.BKItem;
import cn.lsd.app.model.response.bk.BKResult;
import cn.lsd.app.model.uploadFile.UploadList;
import cn.lsd.app.model.uploadFile.UploadMsg;
import cn.lsd.app.router.Router;
import cn.lsd.app.ui.common.CustomPopWindow;
import cn.lsd.app.ui.groupclass.model.ClassList;
import cn.lsd.app.ui.groupclass.model.DetailListBean;
import cn.lsd.app.ui.groupclass.model.EnumEventType;
import cn.lsd.app.ui.groupclass.model.MessageEvent;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;


public class FillClassFragment extends BaseFragment implements View.OnClickListener {
    static final String TAG = "FillClassFragment";
    private static final int PHOTO_REQUEST_GALLERY = 1;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 2;// 结果
    public static String picturePath,picUrl;
    ArrayList<String> filePaths = new ArrayList<String>();
    private BkRecordDao bkDao;

    CustomPopWindow popWindow;
    ClassList classList = new ClassList();

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
    @Bind(R.id.tv_classdetail_set)
    TextView tv_classdetail_set;
    @Bind(R.id.bt_create_class)
    Button bt_create_class;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fill_class;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);//eventbus绑定
        initDbHelp();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.iv_class_cover, R.id.ly_course_name, R.id.rb_bk, R.id.rb_other_bk, R.id.ly_class_textbook, R.id.ly_class_detail, R.id.bt_create_class})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_class_cover:
                showPopMenu();
                break;
            case R.id.ly_course_name:
                Router.inputClassName(getActivity());
                break;
            case R.id.rb_bk:
                classList.setClass_type("班级课表班课");
                break;
            case R.id.rb_other_bk:
                classList.setClass_type("其他班课");
                break;
            case R.id.ly_class_textbook:
                Router.setTextBook(getActivity());
                break;
            case R.id.ly_class_detail:
                Router.setClassDetail(getActivity());
                break;
            case R.id.bt_create_class:
                Gson gson = new Gson();
                String data = gson.toJson(classList);
                saveAndSend();
                EventBus.getDefault().post(data);
                getActivity().finish();
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
                        //调用系统图库
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                if (data != null){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    Log.v(TAG,"00000000000"+picturePath);
                    filePaths.clear();
                    filePaths.add(picturePath);
                    cursor.close();
                    startPhotoZoom(selectedImage, 150);
                }
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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
            Drawable drawable = new BitmapDrawable(photo);
            iv_calss_cover.setImageDrawable(drawable);

            Log.v(TAG,">>>>>>>>>>>>>>"+filePaths.toString());
            try {
                upTask(filePaths);
                filePaths.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void setDefaultPic() {
        iv_calss_cover.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.default_cover));
        classList.setCover_address(AppConstant.API_BK_URL+"default_cover.png");
    }




    /**
     * 事件订阅者自定义的接收方法
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if(event.getType() == EnumEventType.COURSE_NAME.getType()){
            tv_course_name.setText(event.getData());
            classList.setCourse_name(event.getData());
        }else if(event.getType() == EnumEventType.BK_TYPE.getType()){
            Toast.makeText(getActivity(),"待处理", Toast.LENGTH_SHORT);
        }else if(event.getType() == EnumEventType.BOOK_NAME.getType()){
            tv_textbook_set.setText(event.getData());
            classList.setClass_book(event.getData());
        }else if (event.getType() == EnumEventType.CLASS_DETAIL.getType()) {
            Gson gson = new Gson();
            DetailListBean dlb = gson.fromJson(event.getData(), DetailListBean.class);

            classList.setStudy_aims(dlb.getStudy_aims());
            classList.setSyllabus(dlb.getSyllabus());
            classList.setExam_schedule(dlb.getExam_schedule());

            tv_classdetail_set.setText(dlb.getStatus());
        }
    }

    private void saveAndSend() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    //List<UploadMsg> files = new UpTaskUtils().upTask(filePaths);

                    BkRecord br = new BkRecord();
                    String code = Integer.toString((int) ((Math.random()*9+1)*100000));
                    classList.setInvite_code(code);
                    classList.setClass_type("班级课表班课");
                    classList.setClass_name(et_class_name.getText().toString());

                    //获取info文件中的内容
                    SharedPreferences sp =  AppContext.getInstance().getSharedPreferences("info", MODE_PRIVATE);
                    String username = sp.getString("username","");
                    classList.setUsername(username);

                    if(classList.getCover_address()==null){
                        br.setCover_address(picUrl);
                    }else{
                        br.setCover_address(classList.getCover_address());
                    }

                    br.setUsername(classList.getUsername());
                    br.setStudy_aims(classList.getStudy_aims());
                    br.setClass_type(classList.getClass_type());
                    br.setClass_book(classList.getClass_book());
                    br.setClass_name(classList.getClass_name());
                    br.setCourse_name(classList.getCourse_name());
                    br.setExam_schedule(classList.getExam_schedule());
                    br.setInvite_code(classList.getInvite_code());
                    br.setSyllabus(classList.getSyllabus());
                    br.setUsername(classList.getUsername());


                    bkDao.insert(br);
                    ApiFactory.getsBKApi().getBKAdd(br)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(subscriber);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected BaseObserver subscriber = new BaseObserver<BKResult<BKItem>>(){
        @Override
        public void onCompleted() {
            Log.v(TAG, "delay onCompleted");
        }

        @Override
        public void onNext(BKResult<BKItem> bkResult) {
            String jsonData = bkResult.getMsg();
            Log.v(TAG, "添加成功，返回：" +jsonData);
            getActivity().finish();
        }

        @Override
        public void onError(Throwable error) {
            Log.v(TAG, "delay onError"+error.getMessage());
        }
    };

    private void initDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "bk-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        bkDao = daoSession.getBkRecordDao();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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

    public void executePost(AsyncHttpClient client, String url, RequestParams params) {
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
                        UploadMsg um = msgList.get(0);
                        picUrl = um.getResource_address();
                        Log.v(TAG,"1111111111"+picUrl);
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
