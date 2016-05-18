package com.meshine.letsstudyclient.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.meshine.letsstudyclient.LoginActivity_;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Ming on 2016/5/8.
 */
public class BaseFragment extends Fragment {

    /**
     * 检查是否已经登录
     * @param trans 如果没有登录，是否跳转
     * @return
     */
    public boolean checkSignIn(boolean trans){
        UserInfo info = JMessageClient.getMyInfo();
        if (info == null) {
            if (trans){
                Intent intent = new Intent(getContext(), LoginActivity_.class);
                startActivity(intent);
            }

            return false;
        }
        return true;
    }



    public void logIn(){
        Intent intent = new Intent(getContext(), LoginActivity_.class);
        startActivity(intent);
    }

}
