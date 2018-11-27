package cn.lsd.app.ui.BkInside.fragment;


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

import cn.lsd.app.R;
import cn.lsd.app.ui.groupclass.model.EnumEventType;
import cn.lsd.app.ui.groupclass.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetUrlDemandFragment extends Fragment {

    @Bind(R.id.et_url_demand)
    EditText et_url_demand;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_url_demand, container, false);
        ButterKnife.bind(this, view);
        initData();//操作要做bind之后，否则空指针
        return view;
    }

    private void initData() {
        et_url_demand.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
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
                me.setType(EnumEventType.URL_DEMAND.getType());
                me.setData(et_url_demand.getText().toString());
                EventBus.getDefault().post(me);
                getActivity().finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
