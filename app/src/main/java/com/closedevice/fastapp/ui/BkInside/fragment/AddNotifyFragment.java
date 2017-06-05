package com.closedevice.fastapp.ui.BkInside.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.closedevice.fastapp.R;
import com.closedevice.fastapp.router.Router;
import com.closedevice.fastapp.ui.groupclass.model.EnumEventType;
import com.closedevice.fastapp.ui.groupclass.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNotifyFragment extends Fragment {

    @Bind(R.id.et_notify_content)
    EditText et_notify_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_notify, container, false);
        ButterKnife.bind(this, view);
        initData();//操作要做bind之后，否则空指针
        return view;
    }

    private void initData() {
        et_notify_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(300)});
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done, menu);//菜单文件
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_done:
                MessageEvent me = new MessageEvent();
                me.setType(EnumEventType.NOTIFY_CONTENT.getType());
                me.setData(et_notify_content.getText().toString());
                EventBus.getDefault().post(me);
                getActivity().finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
