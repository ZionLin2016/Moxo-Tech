package cn.lsd.app.ui.CourseTable.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.lsd.app.R;
import cn.lsd.app.base.adapter.ListBaseAdapter;
import cn.lsd.app.model.response.gan.GanItem;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GanAdapter extends ListBaseAdapter<GanItem> {

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(
                    R.layout.item_gan, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        GanItem item = mDatas.get(position);
        //Log.i("TAG","---------------------"+item.toString());
        vh.title.setText(item.getDesc());
        vh.author.setText(item.getWho());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_tech_title)
        TextView title;
        @Bind(R.id.tv_tech_author)
        TextView author;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
