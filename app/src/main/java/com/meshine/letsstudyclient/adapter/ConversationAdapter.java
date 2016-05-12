package com.meshine.letsstudyclient.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.widget.CircleImageView;
import com.rockerhieu.emojicon.EmojiconTextView;

import java.util.List;

import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by Meshine on 16/5/11.
 */
public class ConversationAdapter extends BaseAdapter {
    List<Conversation> conversations;
    Context context;

    public ConversationAdapter(List<Conversation> conversations, Context context) {
        this.conversations = conversations;
        this.context = context;
    }

    @Override
    public int getCount() {
        return conversations.size();
    }

    @Override
    public Object getItem(int position) {
        return conversations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder holder = null;
        if (convertView == null){
            holder = new Viewholder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_conversation,null);
            holder.avatar = (CircleImageView) convertView.findViewById(R.id.id_item_conv_avatar);
            holder.nick = (EmojiconTextView) convertView.findViewById(R.id.id_item_conv_nick);
            holder.content = (EmojiconTextView) convertView.findViewById(R.id.id_item_conv_content);
            convertView.setTag(holder);
        }else {
            holder = (Viewholder) convertView.getTag();
        }

        holder.avatar.setImageURI(Uri.fromFile(conversations.get(position).getAvatarFile()));
        holder.nick.setText(conversations.get(position).getTitle());
        Message msg = conversations.get(position).getLatestMessage();
        if (msg != null){
            ContentType type = msg.getContentType();
            switch (type){
                case text:
                    holder.content.setText(((TextContent)msg.getContent()).getText());
                    break ;
                case image:
                    holder.content.setText("[图片]");
                    break ;
                case voice:
                    holder.content.setText("[语音]");
                    break ;
                case custom:
                    holder.content.setText("[自定义消息]");
                    break;

            }
        }else {
            holder.content.setText("");
        }

        return convertView;
    }

    class Viewholder{
        CircleImageView avatar;
        EmojiconTextView nick;
        EmojiconTextView content;
    }
}
