package com.meshine.letsstudyclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.meshine.letsstudyclient.application.MyApplication;
import com.meshine.letsstudyclient.application.MyApplication_;
import com.meshine.letsstudyclient.tools.AppManager;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;


/**
 * Created by Ming on 2016/5/5.
 */
public class BaseActivity extends Activity {

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        AppManager.getAppManager().addActivity(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishActivity();
    }

    public ProgressDialog setProgressDialogTile(String title){
        progressDialog.setTitle(title);
        return progressDialog;
    }

    public ProgressDialog setProgressDialogMessage(String message){
        progressDialog.setMessage(message);
        return progressDialog;
    }


    public void initTopbar(){

    }

    public boolean checkSignIn(){
        UserInfo info = JMessageClient.getMyInfo();
        if (info == null) {
            Intent intent = new Intent(this, LoginActivity_.class);
            startActivity(intent);
            return false;
        }

        return true;
    }


    public void progressDialogShow(){
        progressDialog.show();
    }

    public void progressDialogDismiss(){
        progressDialog.dismiss();
    }

}
