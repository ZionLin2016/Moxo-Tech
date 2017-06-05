package com.closedevice.fastapp.ui.BkInside.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.closedevice.fastapp.api.remote.ApiFactory;
import com.closedevice.fastapp.base.adapter.ListBaseAdapter;
import com.closedevice.fastapp.base.ui.BaseListFragment;
import com.closedevice.fastapp.model.response.member.MemberItem;
import com.closedevice.fastapp.ui.BkInside.UserInfoActivity;
import com.closedevice.fastapp.ui.BkInside.adapter.MemberAdapter;
import com.closedevice.fastapp.ui.BkInsideActivity;
import com.trello.rxlifecycle.FragmentEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ShowMemberFragment extends BaseListFragment<MemberItem> {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    protected void sendRequest() {
        ApiFactory.getUserApi().getAllMember("lsd").compose(this.bindUntilEvent(FragmentEvent.STOP))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    @Override
    protected ListBaseAdapter getListAdapter() {
        MemberAdapter adapter = new MemberAdapter();
        return adapter;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_show_member, container, false);
//        ButterKnife.bind(this, view);
//        return view;
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MemberItem mbItem = mAdapter.getItem(position);
        if (mbItem != null) {
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            intent.putExtra("nativePlace", mbItem.getNative_place());
            intent.putExtra("label", mbItem.getLabel());
            intent.putExtra("birthday", mbItem.getBirthday());
            intent.putExtra("phone", mbItem.getTelephone());
            intent.putExtra("email", mbItem.getEmail());
            startActivity(intent);
            //Router.showClassDetail(getActivity());
            //Router.showDetail(getActivity(), mbItem);
        }
    }
//    @OnClick({R.id.bt_refresh_code})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bt_refresh_code:
//                break;
//
//            default:
//                break;
//
//        }
//    }


}
