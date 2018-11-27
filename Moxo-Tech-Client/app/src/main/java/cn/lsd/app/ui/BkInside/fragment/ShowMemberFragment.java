package cn.lsd.app.ui.BkInside.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import cn.lsd.app.api.remote.ApiFactory;
import cn.lsd.app.base.adapter.ListBaseAdapter;
import cn.lsd.app.base.ui.BaseListFragment;
import cn.lsd.app.model.response.member.MemberItem;
import cn.lsd.app.ui.BkInside.UserInfoActivity;
import cn.lsd.app.ui.BkInside.adapter.MemberAdapter;
import com.trello.rxlifecycle.FragmentEvent;

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
