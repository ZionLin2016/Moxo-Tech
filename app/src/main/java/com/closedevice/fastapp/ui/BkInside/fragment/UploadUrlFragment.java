package com.closedevice.fastapp.ui.BkInside.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.closedevice.fastapp.R;
import com.closedevice.fastapp.base.ui.BaseFragment;
import com.closedevice.fastapp.router.Router;
import com.closedevice.fastapp.ui.groupclass.model.DetailListBean;
import com.closedevice.fastapp.ui.groupclass.model.EnumEventType;
import com.closedevice.fastapp.ui.groupclass.model.MessageEvent;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadUrlFragment extends BaseFragment {

    @Bind(R.id.ly_url_address)
    LinearLayout ly_url_address;
    @Bind(R.id.ly_url_title)
    LinearLayout ly_url_title;
    @Bind(R.id.ly_url_group)
    LinearLayout ly_url_group;
    @Bind(R.id.ly_url_type)
    LinearLayout ly_url_type;
    @Bind(R.id.ly_url_aim)
    LinearLayout ly_url_aim;
    @Bind(R.id.ly_url_demand)
    LinearLayout ly_url_demand;

    @Bind(R.id.tv_url_address_set)
    TextView tv_url_address_set;
    @Bind(R.id.tv_url_title_set)
    TextView tv_url_title_set;
    @Bind(R.id.tv_url_group_set)
    TextView tv_url_group_set;
    @Bind(R.id.tv_url_type_set)
    TextView tv_url_type_set;
    @Bind(R.id.tv_url_aim_set)
    TextView tv_url_aim_set;
    @Bind(R.id.tv_url_demand_set)
    TextView tv_url_demand_set;

    @Bind(R.id.bt_upload_url)
    Button bt_upload_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload_url;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);//eventbus绑定
    }


    @OnClick({R.id.ly_url_address,R.id.ly_url_title,R.id.ly_url_group,R.id.ly_url_type,R.id.ly_url_aim,R.id.ly_url_demand,R.id.bt_upload_url})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_url_address:
                Router.setUrlAddress(getActivity());
                break;
            case R.id.ly_url_title:
                Router.setUrlTitle(getActivity());
                break;
            case R.id.ly_url_group:
                Router.setUrlGroup(getActivity());
                break;
            case R.id.ly_url_type:
                break;
            case R.id.ly_url_aim:
                Router.setUrlAim(getActivity());
                break;
            case R.id.ly_url_demand:
                Router.setUrlDemand(getActivity());
                break;
            case R.id.bt_upload_url:
                break;
            default:
                break;

        }
    }

    /**
     * 事件订阅者自定义的接收方法
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if(event.getType() == EnumEventType.URL_ADR.getType()){
            tv_url_address_set.setText(event.getData());
            //classList.setCourse_name(event.getData());
        }else if(event.getType() == EnumEventType.URL_AIM.getType()){
            tv_url_aim_set.setText(event.getData());
            //Toast.makeText(getActivity(),"待处理", Toast.LENGTH_SHORT);
        }else if(event.getType() == EnumEventType.URL_DEMAND.getType()){
            tv_url_demand_set.setText(event.getData());
            //classList.setClass_book(event.getData());
        }else if (event.getType() == EnumEventType.URL_GROUP.getType()) {
            tv_url_group_set.setText(event.getData());
        }else if (event.getType() == EnumEventType.URL_TITLE.getType()) {
            tv_url_title_set.setText(event.getData());
        }
    }

}
