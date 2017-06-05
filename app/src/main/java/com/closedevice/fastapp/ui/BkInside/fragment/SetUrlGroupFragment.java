package com.closedevice.fastapp.ui.BkInside.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.closedevice.fastapp.R;
import com.closedevice.fastapp.ui.groupclass.model.EnumEventType;
import com.closedevice.fastapp.ui.groupclass.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SetUrlGroupFragment extends Fragment {

    @Bind(R.id.et_url_group)
    EditText et_url_group;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_url_group, container, false);
        ButterKnife.bind(this, view);
        return view;
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
                me.setType(EnumEventType.URL_GROUP.getType());
                me.setData(et_url_group.getText().toString());
                EventBus.getDefault().post(me);
                getActivity().finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
