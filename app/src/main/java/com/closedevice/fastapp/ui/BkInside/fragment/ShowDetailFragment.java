package com.closedevice.fastapp.ui.BkInside.fragment;


import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.closedevice.fastapp.AppContext;
import com.closedevice.fastapp.R;
import com.closedevice.fastapp.api.remote.ApiFactory;
import com.closedevice.fastapp.base.BaseObserver;
import com.closedevice.fastapp.gen.BkRecordDao;
import com.closedevice.fastapp.gen.DaoMaster;
import com.closedevice.fastapp.gen.DaoSession;
import com.closedevice.fastapp.model.record.BkRecord;
import com.closedevice.fastapp.model.response.bk.BKItem;
import com.closedevice.fastapp.model.response.bk.BKResult;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class ShowDetailFragment extends Fragment {
    static final String TAG = "ShowDetailFragment";

    private BkRecordDao bkDao;
    String invite_code = null;
    BkRecord br = null;
    List<BkRecord> list = null;

    @Bind(R.id.tv_code_set)
    TextView tv_code_set;
    @Bind(R.id.tv_book_set)
    TextView tv_book_set;
    @Bind(R.id.tv_aim_set)
    TextView tv_aim_set;
    @Bind(R.id.tv_syllabus_set)
    TextView tv_syllabus_set;
    @Bind(R.id.tv_exam_set)
    TextView tv_exam_set;
    @Bind(R.id.bt_delete_bk)
    Button bt_delete_bk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDbHelp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_detail, container, false);
        ButterKnife.bind(this, view);

        //获取info文件中的内容
        SharedPreferences sp =  AppContext.getInstance().getSharedPreferences("info", MODE_PRIVATE);
        invite_code = sp.getString("code","数据不存在");
        Log.d(TAG,"--------------"+invite_code);
        br = new BkRecord();

        Query query = bkDao.queryBuilder().where(BkRecordDao.Properties.Invite_code.eq(invite_code))
                .build();
        list = query.list();


        br = list.get(0);

        tv_book_set.setText(br.getClass_book());
        tv_code_set.setText(br.getInvite_code());
        tv_aim_set.setText(br.getStudy_aims());
        tv_syllabus_set.setText(br.getSyllabus());
        tv_exam_set.setText(br.getExam_schedule());


        return view;
    }


    @OnClick({R.id.bt_delete_bk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_delete_bk:
                for(BkRecord br : list){
                    bkDao.delete(br);
                    ApiFactory.getsBKApi().delBk(invite_code)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(subscriber);
                }
                break;

            default:
                break;

        }
    }

    protected BaseObserver subscriber = new BaseObserver<BKResult<BKItem>>(){
        @Override
        public void onCompleted() {
            Log.v(TAG, "delay onCompleted");
        }

        @Override
        public void onNext(BKResult<BKItem> bkResult) {
            String jsonData = bkResult.getMsg();
            Log.v(TAG, "删除成功，返回：" +jsonData);
            getActivity().finish();
        }

        @Override
        public void onError(Throwable error) {
            Log.v(TAG, "delay onError"+error.getMessage());
        }
    };


    /*初始化数据库相关*/
    private void initDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "bk-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        bkDao = daoSession.getBkRecordDao();
    }
}
