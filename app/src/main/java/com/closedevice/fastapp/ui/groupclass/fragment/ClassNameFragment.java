package com.closedevice.fastapp.ui.groupclass.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.closedevice.fastapp.R;
import com.closedevice.fastapp.ui.groupclass.model.EnumEventType;
import com.closedevice.fastapp.ui.groupclass.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ClassNameFragment extends Fragment {

    @Bind(R.id.et_course_name)
    EditText et_course_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_name, container, false);
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
                me.setType(EnumEventType.COURSE_NAME.getType());
                me.setData(et_course_name.getText().toString());
                EventBus.getDefault().post(me);
                getActivity().finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}