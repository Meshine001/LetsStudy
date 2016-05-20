package com.meshine.letsstudyclient;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meshine.letsstudyclient.application.MyApplication_;
import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.tools.HandleResponseCode;
import com.meshine.letsstudyclient.tools.MyPrefs_;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Ming on 2016/5/4.
 */
@EActivity(R.layout.activity_sign_up)
public class SignUpActivity extends BaseActivity {

    private static final String TAG = "SignUpActivity";

    //TopBar
    @ViewById(R.id.id_sign_up_topbar)
    TopBarView topbar;

    @ViewById(R.id.id_sign_up_username)
    EditText etUserName;
    @ViewById(R.id.id_sign_up_password)
    EditText etPassword;
    @ViewById(R.id.id_sign_up_submit)
    Button btnSignUp;

    @Pref
    MyPrefs_ myPrefs;

    @AfterViews
    void init(){
        initTopbar();
    }

    @Click({R.id.id_sign_up_submit})
    void onClick(View view){
        if (validateInput()){
            Log.i(TAG,"开始注册新用户");
            final String username = etUserName.getText().toString();
            final String password = etPassword.getText().toString();
            setProgressDialogTile("提示").setMessage("新用户注册中...");
            progressDialogShow();
            JMessageClient.register(username,password , new BasicCallback() {
                @Override
                public void gotResult(int status, String s) {
                    if (status == 0){
                        Log.i(TAG,"注册新用户成功");
                        Log.i(TAG,"同步数据到云端");
                        AsyncToCloud(username,password);
                        login(username,password);
                    }else {
                        Log.i(TAG,"注册新用户失败");
                        progressDialogDismiss();
                        HandleResponseCode.onHandle(SignUpActivity.this, status, false);
                    }
                }
            });
        }
    }

    void AsyncToCloud(String username,String password) {
      
    }



    void login(final String username, final String password){
        Log.i(TAG,"开始登录");
        setProgressDialogTile("提示").setMessage("正在登入...");
        progressDialogShow();
        JMessageClient.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int status, String s) {
                if (status == 0) {
                    Log.i(TAG,"登录成功");
                    UserInfo userInfo = JMessageClient.getMyInfo();
                    if (userInfo.getNickname().equals("")){
                        userInfo.setNickname("u_"+userInfo.getUserID());
                        JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, new BasicCallback() {
                            @Override
                            public void gotResult(int status, String s) {
                                progressDialogDismiss();
                                myPrefs.edit()
                                        .username()
                                        .put(username)
                                        .password()
                                        .put(password)
                                        .apply();
                                AppManager.getAppManager().finishActivity(SignUpActivity_.class);
                                AppManager.getAppManager().finishActivity(LoginActivity_.class);

                            }
                        });
                    }else {
                        progressDialogDismiss();
                        myPrefs.edit()
                                .username()
                                .put(username)
                                .password()
                                .put(password)
                                .apply();
                        AppManager.getAppManager().finishActivity(SignUpActivity_.class);
                        AppManager.getAppManager().finishActivity(LoginActivity_.class);
                    }


                } else {
                    Log.i(TAG,"登录失败");
                    progressDialogDismiss();
                    HandleResponseCode.onHandle(SignUpActivity.this, status, false);
                }
            }
        });
    }

    boolean validateInput(){

        return true;
    }


    @Override
    public void initTopbar() {
        super.initTopbar();
        topbar.setOnTopBarClickListener(new TopBarView.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }
}
