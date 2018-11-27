package cn.lsd.app.ui.BkInside.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lsd.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadDocFragment extends Fragment {


    public UploadDocFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_doc, container, false);
    }

}
