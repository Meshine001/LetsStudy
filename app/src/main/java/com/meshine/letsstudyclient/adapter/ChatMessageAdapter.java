package com.meshine.letsstudyclient.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.bean.ChatMessage;
import com.meshine.letsstudyclient.tools.HandleResponseCode;
import com.meshine.letsstudyclient.tools.TimeFormat;
import com.meshine.letsstudyclient.widget.CircleImageView;
import com.rockerhieu.emojicon.EmojiconTextView;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Meshine on 16/4/28.
 */
public class ChatMessageAdapter extends BaseAdapter{

    public static final int CHAT_TYPE_TEXT = 0;

    public static final int CHAT_POSITION_LEFT = 0;
    public static final int CHAT_POSITION_RIGHT = 1;



    // 9种Item的类型
    // 文本
    private final int TYPE_RECEIVE_TXT = 0;
    private final int TYPE_SEND_TXT = 1;
    // 图片
    private final int TYPE_SEND_IMAGE = 2;
    private final int TYPE_RECEIVER_IMAGE = 3;
    // 位置
    private final int TYPE_SEND_LOCATION = 4;
    private final int TYPE_RECEIVER_LOCATION = 5;
    // 语音
    private final int TYPE_SEND_VOICE = 6;
    private final int TYPE_RECEIVER_VOICE = 7;
    //群成员变动
    private final int TYPE_GROUP_CHANGE = 8;
    //自定义消息
    private final int TYPE_CUSTOM_TXT = 9;


    private List<Message> messages = new ArrayList<>();

    private Context context;
    private LayoutInflater mInflater;

    private int minRecorderItemWidth;
    private int maxRecorderItemWidth;
    private int mOffset;


    public ChatMessageAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
        mInflater = LayoutInflater.from(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        maxRecorderItemWidth = (int)(outMetrics.widthPixels*0.7f);
        minRecorderItemWidth = (int)(outMetrics.widthPixels*0.15f);

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

        final Message msg = messages.get(position);
        final UserInfo userInfo = msg.getFromUser();
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = createViewByType(msg, position);
            switch (msg.getContentType()) {
                case text:
                    holder.avatar = (CircleImageView) convertView.findViewById(R.id.id_item_chat_avatar);
                    holder.displayName = (TextView) convertView.findViewById(R.id.id_chat_display_name_tv);
                    holder.txtContent = (EmojiconTextView) convertView.findViewById(R.id.id_chat_msg_content);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //显示时间
        TextView msgTime = (TextView) convertView
                .findViewById(R.id.id_chat_send_time_txt);
        long nowDate = msg.getCreateTime();
        if (mOffset == 18) {
            if (position == 0 || position % 18 == 0) {
                TimeFormat timeFormat = new TimeFormat(context, nowDate);
                msgTime.setText(timeFormat.getDetailTime());
                msgTime.setVisibility(View.VISIBLE);
            } else {
                long lastDate = messages.get(position - 1).getCreateTime();
                // 如果两条消息之间的间隔超过十分钟则显示时间
                if (nowDate - lastDate > 600000) {
                    TimeFormat timeFormat = new TimeFormat(context, nowDate);
                    msgTime.setText(timeFormat.getDetailTime());
                    msgTime.setVisibility(View.VISIBLE);
                } else {
                    msgTime.setVisibility(View.GONE);
                }
            }
        } else {
            if (position == 0 || position == mOffset
                    || (position - mOffset) % 18 == 0) {
                TimeFormat timeFormat = new TimeFormat(context, nowDate);

                msgTime.setText(timeFormat.getDetailTime());
                msgTime.setVisibility(View.VISIBLE);
            } else {
                long lastDate = messages.get(position - 1).getCreateTime();
                // 如果两条消息之间的间隔超过十分钟则显示时间
                if (nowDate - lastDate > 600000) {
                    TimeFormat timeFormat = new TimeFormat(context, nowDate);
                    msgTime.setText(timeFormat.getDetailTime());
                    msgTime.setVisibility(View.VISIBLE);
                } else {
                    msgTime.setVisibility(View.GONE);
                }
            }
        }

        //显示头像
        if (holder.avatar != null){
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getAvatar())){
                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                    @Override
                    public void gotResult(int status, String desc, Bitmap bitmap) {
                        if (status == 0) {
                            holder.avatar.setImageBitmap(bitmap);
                        } else {
                            holder.avatar.setImageResource(R.drawable.ic_test_avater);
                            HandleResponseCode.onHandle(context, status, false);
                        }
                    }
                });
            }else {
                holder.avatar.setImageResource(R.drawable.ic_test_avater);
            }
        }

        // 点击头像跳转到个人信息界面
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        switch (msg.getContentType()) {
            case text:
                handleTextMsg(msg, holder, position);
                break;
            case image:
                //handleImgMsg(msg, holder, position);
                break;
            case voice:
//                handleVoiceMsg(msg, holder, position);
                break;
            case location:
//                handleLocationMsg(msg, holder, position);
                break;
            case eventNotification:
//                handleGroupChangeMsg(msg, holder, msgTime);
                break;
            default:
               // handleCustomMsg(msg, holder);
        }

        return convertView;

    }

    /**
     * 处理文字消息
     * @param msg
     * @param holder
     * @param position
     */
    private void handleTextMsg(Message msg, ViewHolder holder, int position) {
        final String content = ((TextContent) msg.getContent()).getText();
        holder.txtContent.setText(content);
        holder.txtContent.setTag(position);
        //长按文本跳出Dialog
//        holder.txtContent.setOnLongClickListener();


    }

    private View createViewByType(Message msg, int position) {
        switch (msg.getContentType()){
//            case image:
//                return getItemViewType(position) == TYPE_SEND_IMAGE ? mInflater
//                        .inflate(R.layout.chat_item_send_image, null) : mInflater
//                        .inflate(R.layout.chat_item_receive_image, null);
//            case voice:
//                return getItemViewType(position) == TYPE_SEND_VOICE ? mInflater
//                        .inflate(R.layout.chat_item_send_voice, null) : mInflater
//                        .inflate(R.layout.chat_item_receive_voice, null);
//            case location:
//                return getItemViewType(position) == TYPE_SEND_LOCATION ? mInflater
//                        .inflate(R.layout.chat_item_send_location, null) : mInflater
//                        .inflate(R.layout.chat_item_receive_location, null);
//            case eventNotification:
//                if (getItemViewType(position) == TYPE_GROUP_CHANGE)
//                    return mInflater.inflate(R.layout.chat_item_group_change, null);
            case text:
                return getItemViewType(position) == TYPE_SEND_TXT ? mInflater
                        .inflate(R.layout.chat_item_send_text, null) : mInflater
                        .inflate(R.layout.chat_item_receive_text, null);
            default:
                return mInflater.inflate(R.layout.chat_item_group_change, null);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = messages.get(position);
        //是文字类型或者自定义类型（用来显示群成员变化消息）
        switch (msg.getContentType()) {
            case text:
                return msg.getDirect() == MessageDirect.send ? TYPE_SEND_TXT
                        : TYPE_RECEIVE_TXT;
            case image:
                return msg.getDirect() == MessageDirect.send ? TYPE_SEND_IMAGE
                        : TYPE_RECEIVER_IMAGE;
            case voice:
                return msg.getDirect() == MessageDirect.send ? TYPE_SEND_VOICE
                        : TYPE_RECEIVER_VOICE;
            case location:
                return msg.getDirect() == MessageDirect.send ? TYPE_SEND_LOCATION
                        : TYPE_RECEIVER_LOCATION;
            case eventNotification:
                return TYPE_GROUP_CHANGE;
            default:
                return TYPE_CUSTOM_TXT;
        }
    }

    public static class ViewHolder {
        CircleImageView avatar;
        TextView displayName;
        EmojiconTextView txtContent;

        TextView recorderTime;
        View recorderLen;
    }
}
