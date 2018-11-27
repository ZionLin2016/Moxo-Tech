package cn.lsd.app.ui.groupclass.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import cn.lsd.app.R;
import cn.lsd.app.base.adapter.ListBaseAdapter;
import cn.lsd.app.model.response.bk.BKItem;
import cn.lsd.app.view.SquareImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LSD on 2017/4/23.
 */

public class BKAdapter extends ListBaseAdapter<BKItem>{
    private static final String TAG = "BKAdapter";
        @Override
        protected View getRealView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null || convertView.getTag() == null) {
                convertView = getLayoutInflater(parent.getContext()).inflate(
                        R.layout.item_class_list, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            BKItem item = mDatas.get(position);
            Log.i(TAG,"---------------------"+item.toString());
            Glide.with(parent.getContext()).load(item.getCover_address()).into(vh.image);
            //.load(parent.getContext(), item.getCover_address(), vh.image);
            vh.classname.setText(item.getClass_name());
            vh.coursename.setText(item.getCourse_name());
            vh.classtype.setText(item.getClass_type());
            vh.bkcode.setText(item.getInvite_code());
            vh.username.setText(item.getUsername());

            return convertView;

        }

        static class ViewHolder {
            @Bind(R.id.iv_bk_item_image)
            SquareImageView image;
            @Bind(R.id.tv_bk_item_classname)
            TextView classname;
            @Bind(R.id.tv_bk_item_coursename)
            TextView coursename;
            @Bind(R.id.tv_bk_item_username)
            TextView username;
            @Bind(R.id.tv_bk_item_classtype)
            TextView classtype;
            @Bind(R.id.tv_bk_item_bkcode)
            TextView bkcode;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }


}
