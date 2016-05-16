package com.meshine.letsstudyclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.bean.User;
import com.meshine.letsstudyclient.widget.CircleImageView;

import java.util.List;

/**
 * Created by Meshine on 16/5/16.
 */
public class RankAdapter extends BaseAdapter{
    private List<User> users;
    private Context context;
    private int offset = 4;

    public RankAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_rank,null);
            holder = new ViewHolder();
            holder.num = (TextView) convertView.findViewById(R.id.id_item_rank_num);
            holder.avatar = (CircleImageView) convertView.findViewById(R.id.id_item_rank_avatar);
            holder.nick = (TextView) convertView.findViewById(R.id.id_item_rank_nick);
            holder.vitality = (TextView) convertView.findViewById(R.id.id_item_rank_vitality);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if ((position+offset)<10)
        holder.num.setText((position+offset)+".  ");
        else
        holder.num.setText((position+offset)+".");
        Glide.with(context).load(users.get(position).getAvatar())
                .placeholder(R.drawable.ic_logo)
                .dontAnimate()
                .dontTransform()
                .into(holder.avatar);
        holder.nick.setText(users.get(position).getNick());
        holder.vitality.setText("活跃度: "+users.get(position).getVitality());

        return convertView;
    }

    class ViewHolder{
        TextView num;
        CircleImageView avatar;
        TextView nick;
        TextView vitality;
    }
}
