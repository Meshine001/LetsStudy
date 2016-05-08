package com.meshine.letsstudyclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.bean.Event;
import com.meshine.letsstudyclient.widget.CircleImageView;

import java.util.List;

/**
 * Created by Ming on 2016/5/7.
 */
public class EventAdapter extends BaseAdapter{
    private static String NUMBER = "人数：";
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
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_event,null);
            holder.avatar = (CircleImageView) convertView.findViewById(R.id.id_item_event_avatar);
            holder.nick = (TextView) convertView.findViewById(R.id.id_item_event_nick);
            holder.title = (TextView) convertView.findViewById(R.id.id_item_event_title);
            holder.number = (TextView) convertView.findViewById(R.id.id_item_event_number);
            holder.limit = (TextView) convertView.findViewById(R.id.id_item_event_limit);
            holder.date = (TextView) convertView.findViewById(R.id.id_item_event_date);
            holder.summary = (TextView) convertView.findViewById(R.id.id_item_event_summary);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(events.get(position).getAvatar())
                .placeholder(R.drawable.ic_tab_square_normal)
                .dontAnimate()
                .dontTransform()
                .into(holder.avatar);
        holder.nick.setText(events.get(position).getNick());
        holder.title.setText(events.get(position).getTitle());
        holder.number.setText(NUMBER+events.get(position).getNumber()+"人");
        holder.limit.setText(LIMIT+events.get(position).getLimit());
        holder.date.setText(events.get(position).getDate());
        holder.summary.setText(events.get(position).getSummary());


        return convertView;
    }

    class ViewHolder{
        CircleImageView avatar;
        TextView nick;
        TextView title;
        TextView number;
        TextView limit;
        TextView date;
        TextView summary;
    }
}
