package com.meshine.letsstudyclient.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.bean.SquareInfo;

import java.util.List;

/**
 * Created by Ming on 2016/4/24.
 */
public class SquareInfoAdapter extends BaseAdapter {
    private List<SquareInfo> infos;
    private Context context;

    public SquareInfoAdapter(List<SquareInfo> infos, Context context) {
        this.infos = infos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_info,null);
            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.id_item_info_avatar);
            holder.title = (TextView) convertView.findViewById(R.id.id_item_info_title);
            holder.details = (TextView) convertView.findViewById(R.id.id_item_info_details);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

//        Bitmap avatar = Bitmap.createBitmap(60,60);
        holder.avatar.setImageResource(R.drawable.ic_tab_square);
        holder.title.setText(infos.get(position).getTitle());
        holder.details.setText(infos.get(position).getDetails());
        return convertView;
    }

    class ViewHolder{
        ImageView avatar;
        TextView title;
        TextView details;
    }
}
