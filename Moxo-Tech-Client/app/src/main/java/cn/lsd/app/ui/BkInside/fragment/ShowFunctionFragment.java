package cn.lsd.app.ui.BkInside.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.lsd.app.IM.messages.MessageListActivity;
import cn.lsd.app.R;
import cn.lsd.app.base.ui.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowFunctionFragment extends BaseFragment {

    @Bind(R.id.bt_begin_chat)
    Button bt_begin_chat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_function, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.bt_begin_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_begin_chat:
                Intent intent = new Intent(getActivity(), MessageListActivity.class);
                startActivity(intent);
            break;
            default:
                break;

        }
    }
}
