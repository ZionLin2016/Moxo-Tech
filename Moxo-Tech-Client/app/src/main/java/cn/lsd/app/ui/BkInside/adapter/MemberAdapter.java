package cn.lsd.app.ui.BkInside.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import cn.lsd.app.R;
import cn.lsd.app.base.adapter.ListBaseAdapter;
import cn.lsd.app.model.response.member.MemberItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MemberAdapter extends ListBaseAdapter<MemberItem> {

	private static final String TAG = "MemberAdapter";
	@Override
	protected View getRealView(int position, View convertView, ViewGroup parent) {
		MemberAdapter.ViewHolder vh;
		if (convertView == null || convertView.getTag() == null) {
			convertView = getLayoutInflater(parent.getContext()).inflate(
					R.layout.item_member, null);
			vh = new MemberAdapter.ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (MemberAdapter.ViewHolder) convertView.getTag();
		}

		MemberItem item = mDatas.get(position);
		Log.v(TAG,"---------------------"+item.toString());
		Glide.with(parent.getContext()).load(item.getPhoto()).into(vh.ivItemMemberHead);
		//.load(parent.getContext(), item.getCover_address(), vh.image);
		vh.tvItemMemberPhone.setText(item.getTelephone());
		vh.tvItemMemberName.setText(item.getName());

		return convertView;

	}

	static class ViewHolder {
		@Bind(R.id.ivItemMemberHead)
		ImageView ivItemMemberHead;
		@Bind(R.id.tvItemMemberPhone)
		TextView tvItemMemberPhone;
		@Bind(R.id.tvItemMemberName)
		TextView tvItemMemberName;


		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}



}
