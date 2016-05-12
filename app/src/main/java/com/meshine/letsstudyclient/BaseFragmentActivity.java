package com.meshine.letsstudyclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;

/**
 * Created by Meshine on 16/5/11.
 */
public class BaseFragmentActivity extends FragmentActivity {
   // protected BaseHandler handler;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
       // handler = new BaseHandler();
        JMessageClient.registerEventReceiver(this);
    }

    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

//    /**
//     * 主线程监听
//     * @param event
//     */
//    public void onEventMainThread(LoginStateChangeEvent event) {
//
//    }
//
//    public class BaseHandler extends Handler {
//
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            handleMsg(msg);
//        }
//    }
//
//    /**
//     * 传递主线程消息
//     * @param message
//     */
//    public void handleMsg(Message message) {
//
//    }


}
