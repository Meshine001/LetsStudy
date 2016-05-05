package com.meshine.letsstudyclient.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.tools.HandleResponseCode;
import com.meshine.letsstudyclient.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Ming on 2016/5/4.
 */
public class ContactsAdapter extends BaseAdapter {
    List<UserInfo> userInfos = new ArrayList<>();
    Context context;

    public ContactsAdapter(List<UserInfo> userInfos, Context context) {
        this.userInfos = userInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return userInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contacts,null);
            holder = new ViewHolder();
            holder.avatar = (CircleImageView) convertView.findViewById(R.id.id_contacts_avatar);
            holder.txtNick = (TextView) convertView.findViewById(R.id.id_contacts_nick);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserInfo userInfo = userInfos.get(position);
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

        //昵称
        holder.txtNick.setText(userInfo.getNickname());

        return convertView;
    }

    class ViewHolder{
        CircleImageView avatar;
        TextView txtNick;
    }
}
