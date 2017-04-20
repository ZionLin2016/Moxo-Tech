package com.closedevice.fastapp.ui.groupclass.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.closedevice.fastapp.R;
import com.closedevice.fastapp.base.ui.BaseFragment;
import com.closedevice.fastapp.router.Router;


import butterknife.ButterKnife;


//public class BKFragment extends BaseListFragment<WXItem>{
public class BKFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container,false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bk;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bk_menu, menu);//菜单文件
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.create_bk:
                Router.fillBkMsgClass(getActivity());
                break;
            case R.id.join_bk_bycode:
                Router.inputCodeClass(getActivity());
                break;
            case R.id.using_help:
                Router.inputCodeClass(getActivity());
                break;
            case R.id.common_problems:
                Router.inputCodeClass(getActivity());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void sendRequest() {
//        ApiFactory.getWXApi().getWXHot(AppConstant.KEY_WX, getPageSize(), mCurrentPage + 1).compose(this.bindUntilEvent(FragmentEvent.STOP))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mSubscriber);
//    }



//    @Override
//    protected ListBaseAdapter<WXItem> getListAdapter() {
//        WXAdapter adapter = new WXAdapter();
//        return adapter;
//    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        WXItem wxItem = mAdapter.getItem(position);
//        if (wxItem != null) {
//            Router.showDetail(getActivity(), wxItem.getTitle(), wxItem.getUrl(), wxItem.getPicUrl(), wxItem.getDescription(), wxItem.getCtime());
//            RealmHelper realmHelper = new RealmHelper(AppContext.context());
//            if (realmHelper.findReadRecord(wxItem.getTitle()) != null) {
//                return;
//            }
//            ReadRecordRealm recordRealm = new ReadRecordRealm();
//            recordRealm.setId(wxItem.getId());
//            recordRealm.setTitle(wxItem.getTitle());
//            recordRealm.setTime(System.currentTimeMillis());
//            recordRealm.setImage(wxItem.getPicUrl());
//            realmHelper.addReadRecord(recordRealm);
//
//
//        }
//    }






}
