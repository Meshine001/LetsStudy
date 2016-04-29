package com.meshine.letsstudyclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.bean.ChatMessage;
import com.meshine.letsstudyclient.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meshine on 16/4/28.
 */
public class ChatMessageAdapter extends BaseAdapter {
    public static final int CHAT_TYPE_TEXT = 0;

    public static final int CHAT_POSITION_LEFT = 0;
    public static final int CHAT_POSITION_RIGHT = 1;

    private  int[] text_msg_layout = {R.layout.item_chat_left, R.layout.item_chat_right};
    private  int[] avatar_layout = {R.id.id_item_chat_left_avatar, R.id.id_item_chat_right_avatar};
    private  int[] text_layout = {R.id.id_item_chat_left_text, R.id.id_item_chat_right_text};


    private List<ChatMessage> messages = new ArrayList<>();

    private Context context;

    public ChatMessageAdapter(List<ChatMessage> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        int displayPos = messages.get(position).getDisplayPos();


        ViewHolder holder = null;

//        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(text_msg_layout[displayPos], null);
            holder = new ViewHolder();
            holder.avatar = (CircleImageView) convertView.findViewById(avatar_layout[displayPos]);
            holder.text = (TextView) convertView.findViewById(text_layout[displayPos]);
            convertView.setTag(holder);

//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }

        holder.avatar.setImageResource(R.drawable.ic_test_avater);
        holder.text.setText(messages.get(position).getText());

        return convertView;

    }

    class ViewHolder {
        CircleImageView avatar;
        TextView text;
    }
}
