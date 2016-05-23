package com.meshine.letsstudyclient.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.bean.Event;
import com.meshine.letsstudyclient.tools.MapUtil;
import com.meshine.letsstudyclient.tools.TimeFormat;
import com.meshine.letsstudyclient.widget.CircleImageView;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Ming on 2016/5/7.
 */
public class EventAdapter extends BaseAdapter{
    private static String COUNT = "人数：";
    private static String LIMIT = "限制：";

    List<Event> events;
    Context context;

    public EventAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_event,null);
            holder.avatar = (CircleImageView) convertView.findViewById(R.id.id_item_event_avatar);
            holder.nick = (TextView) convertView.findViewById(R.id.id_item_event_nick);
            holder.title = (TextView) convertView.findViewById(R.id.id_item_event_title);
            holder.count = (TextView) convertView.findViewById(R.id.id_item_event_number);
            holder.limit = (TextView) convertView.findViewById(R.id.id_item_event_limit);
            holder.date = (TextView) convertView.findViewById(R.id.id_item_event_date);
            holder.summary = (TextView) convertView.findViewById(R.id.id_item_event_summary);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        JMessageClient.getUserInfo(events.get(position).getUserName(), new GetUserInfoCallback() {
            @Override
            public void gotResult(int status, String s, UserInfo userInfo) {
                if (status == 0){
                    holder.avatar.setImageURI(Uri.fromFile(userInfo.getAvatarFile()));
                    holder.nick.setText(userInfo.getNickname());
                }else {
                    holder.avatar.setImageResource(R.drawable.ic_avatar_default);
                    holder.nick.setText("Unknown");
                }
            }
        });
        holder.title.setText(events.get(position).getTitle());
        holder.count.setText(COUNT+events.get(position).getCount()+"人");
        holder.limit.setText(LIMIT+ MapUtil.mapLimitType(events.get(position).getLimit()));
        holder.date.setText("发布: "+ TimeFormat.unixTime2Local(events.get(position).getCreateTime(),"yyyy-MM-dd"));
        holder.summary.setText(events.get(position).getDetails());


        return convertView;
    }

    class ViewHolder{
        CircleImageView avatar;
        TextView nick;
        TextView title;
        TextView count;
        TextView limit;
        TextView date;
        TextView summary;
    }
}
