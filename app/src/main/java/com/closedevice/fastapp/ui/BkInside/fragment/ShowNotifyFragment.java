package com.closedevice.fastapp.ui.BkInside.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.closedevice.fastapp.R;
import android.support.design.widget.FloatingActionButton;

import com.closedevice.fastapp.base.ui.BaseFragment;
import com.closedevice.fastapp.router.Router;
import com.closedevice.fastapp.ui.groupclass.model.EnumEventType;
import com.closedevice.fastapp.ui.groupclass.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShowNotifyFragment extends BaseFragment {


    @Bind(R.id.fabt_add)
    FloatingActionButton bt_fabt_add;
    @Bind(R.id.tv_notify_context)
    TextView tv_notify_context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_show_notify;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);//eventbus绑定
    }


    @OnClick({R.id.fabt_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabt_add:
                Router.addNotify(getActivity());
                break;

            default:
                break;

        }
    }


    /**
     * 事件订阅者自定义的接收方法
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (event.getType() == EnumEventType.NOTIFY_CONTENT.getType()) {
            tv_notify_context.setText(event.getData());
        }

    }
}
