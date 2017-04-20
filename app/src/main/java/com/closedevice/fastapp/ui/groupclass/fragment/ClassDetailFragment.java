package com.closedevice.fastapp.ui.groupclass.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.closedevice.fastapp.R;
import butterknife.Bind;
import butterknife.ButterKnife;


public class ClassDetailFragment extends Fragment {

    @Bind(R.id.et_study_aims)
    EditText et_study_aims;
    @Bind(R.id.et_syllabus)
    EditText et_syllabus;
    @Bind(R.id.et_exam_schedule)
    EditText et_exam_schedule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initData();//操作要做bind之后，否则空指针
        return view;
    }


    private void initData() {
        et_study_aims.setFilters(new InputFilter[]{new InputFilter.LengthFilter(300)});
        et_syllabus.setFilters(new InputFilter[]{new InputFilter.LengthFilter(300)});
        et_exam_schedule.setFilters(new InputFilter[]{new InputFilter.LengthFilter(300)});
    }

    private int getLayoutId() {
        return R.layout.fragment_class_detail;
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
                Toast.makeText(getActivity(),"完成", Toast.LENGTH_SHORT);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
