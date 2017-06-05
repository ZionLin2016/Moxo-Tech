package com.closedevice.fastapp.ui.BkInside.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.closedevice.fastapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UploadFromPcFragment extends Fragment {

    @Bind(R.id.tv_upload_code)
    TextView tv_upload_code;
    @Bind(R.id.bt_refresh_code)
    Button bt_refresh_code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_from_pc, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.bt_refresh_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_refresh_code:
                String code = Integer.toString((int) ((Math.random()*9+1)*100000));
                tv_upload_code.setText(code);
                break;

            default:
                break;

        }
    }
}
