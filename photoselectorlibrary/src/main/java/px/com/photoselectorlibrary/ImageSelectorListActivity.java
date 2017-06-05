package px.com.photoselectorlibrary;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by admin on 2016/9/12.
 */
public class ImageSelectorListActivity extends Activity {
    TextView _title_text,_r_text;
    RelativeLayout _title_bar;
    RecyclerView _rec_image_list;
    List<ImageBean> list;
    public static  final int RESULT_OK=10001;
    /**
     * 用来判断图片是否支持多选 默认 1 为单选 2多选
     */
    public int type=1;
    MyAdpter adpter;

    static final String[] PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,// 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,  //读取权限
    };
    private static final int REQUEST_CODE = 0;//请求码

    private CheckPermission checkPermission;//检测权限器
    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_selector);
        checkPermission = new CheckPermission(this);
        type=getIntent().getIntExtra("type",1);
        initview();
        if (checkPermission.permissionSet(PERMISSION)) {
            startPermissionActivity();
        }
        else
        {
            initData();
        }

    }

    private void initData() {

         _rec_image_list.setLayoutManager(new LinearLayoutManager(ImageSelectorListActivity.this));

        scanPhoto();

    }


    //进入权限设置页面
    private void startPermissionActivity() {
        PermissionActivity.startActivityForResult(this, REQUEST_CODE, PERMISSION);
    }

    //返回结果回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拒绝时，没有获取到主要权限，无法运行，关闭页面
        if (requestCode == REQUEST_CODE && resultCode == PermissionActivity.PERMISSION_DENIEG) {
            finish();
        }
        else if (requestCode==RESULT_OK)
        {    if (data==null)return;
            if (data.getStringArrayListExtra("filePath")!=null)
            {
                ArrayList<String> filePath=data.getStringArrayListExtra("filePath");
                if (filePath.size()>0)
                {
                    Intent it=new Intent();
                    it.putStringArrayListExtra("filePath",filePath);
                    setResult(RESULT_OK,it);
                    finish();
                }
            }
        }
        else{ initData();}
    }
    private void initview() {
        _title_text= (TextView) findViewById(R.id.title_text);
        _r_text= (TextView) findViewById(R.id.r_text);
        _title_bar= (RelativeLayout) findViewById(R.id.title_bar);
        _rec_image_list= (RecyclerView) findViewById(R.id.rec_image_list);
        _title_text.setText("选择照片");
        _r_text.setText("取消");
        _r_text.setVisibility(View.VISIBLE);
        _r_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public  void  scanPhoto()
    {

        new Thread()
        {
            @Override
            public void run() {
                Uri imageUri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mresolver=ImageSelectorListActivity.this.getContentResolver();
                Cursor mCursor = mresolver.query(imageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);

                if(mCursor == null){
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();


                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }

                //通知Handler扫描图片完成
                handler.sendEmptyMessage(10010);
                mCursor.close();
            }
        }.start();

    }
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
           if (msg.what==10010)
           {
            if (mGruopMap!=null) {
                list = subGroupOfImage(mGruopMap);
                adpter=new MyAdpter();
                _rec_image_list.setAdapter(adpter);
                adpter.setOnItemClickLitener(new OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("fileName",list.get(position).getFolderName());
                        map.put("filelist",mGruopMap.get(list.get(position).getFolderName()));
                        Intent it=new Intent(ImageSelectorListActivity.this,ImageSelectorDetailsActivity.class);
                        final SerializableMap myMap=new SerializableMap();
                        myMap.setMap(map);//将map数据添加到封装的myMap中
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("map", myMap);
                        it.putExtra("type",type);
                        it.putExtras(bundle);
                        startActivityForResult(it,RESULT_OK);

                    }
                });

            }
           }
        }
    };



    /**
     * 组装分组界面的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap){
        if(mGruopMap.size() == 0){
            return null;
        }
        List<ImageBean> list = new ArrayList<ImageBean>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            ImageBean mImageBean = new ImageBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();

            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片

            list.add(mImageBean);

        }

        return list;

    }
    public interface  OnItemClickListener
    {
        void OnItemClick(View view,int position);

    }
    class  MyAdpter extends  RecyclerView.Adapter<MyAdpter.MyViewHolder>
  {

      private  OnItemClickListener mOnItemClickListener;

      public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener)
      {
          this.mOnItemClickListener = mOnItemClickLitener;
      }

      @Override
      public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          View  view= LayoutInflater.from(ImageSelectorListActivity.this).inflate(R.layout.image_selector_item,null);
          MyViewHolder viewholder=new MyViewHolder(view);
          return viewholder;
      }

      @Override
      public void onBindViewHolder(MyViewHolder holder, int position) {
          Glide.with(ImageSelectorListActivity.this).load(list.get(position).getTopImagePath()).fitCenter()
                  .centerCrop().into(holder.image);
          holder.title.setText(list.get(position).getFolderName()+"("+list.get(position).getImageCounts()+")");
          final int pos=position;
         holder.rv_selector_list.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 mOnItemClickListener.OnItemClick(view,pos);
             }
         });

      }

      @Override
      public int getItemCount() {
          return list.size();
      }

      class  MyViewHolder extends  RecyclerView.ViewHolder
      {
          public  TextView title;
          public ImageView image;
          public RelativeLayout  rv_selector_list;

          public MyViewHolder(View itemView) {
              super(itemView);
              title= (TextView) itemView.findViewById(R.id.tv_selector_list_text);
              image= (ImageView) itemView.findViewById(R.id.iv_selector_list_image);
              rv_selector_list= (RelativeLayout) itemView.findViewById(R.id.rv_selector_list);
          }
      }
  }
}
