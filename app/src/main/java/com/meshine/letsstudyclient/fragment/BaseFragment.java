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
    public boolean isLogIn(){

        UserInfo userInfo = JMessageClient.getMyInfo();

        if (userInfo == null)return false;

        return true;
    }

    public void logIn(){
        Intent intent = new Intent(getContext(), LoginActivity_.class);
        startActivity(intent);
    }

}
