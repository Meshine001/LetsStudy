package com.meshine.letsstudyclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.meshine.letsstudyclient.application.MyApplication;
import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.tools.HandleResponseCode;
import com.meshine.letsstudyclient.tools.MyPrefs_;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Ming on 2016/4/24.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    @ViewById(R.id.id_login_username)
    EditText etUserName;
    @ViewById(R.id.id_login_password)
    EditText etPassword;
    @ViewById(R.id.id_login_sign_in)
    Button btnSignIn;
    @ViewById(R.id.id_login_sign_up)
    TextView btnSignUp;

    @ViewById(R.id.id_sign_in_topbar)
    TopBarView topbar;

    @Pref
    MyPrefs_ myPrefs;

    @AfterViews
    void init(){
        initTopbar();
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

    @Click({R.id.id_login_sign_in,R.id.id_login_sign_up,R.id.id_login_wechat,R.id.id_login_qq,R.id.id_login_xin_lang})
    void onClick(View view){
        switch (view.getId()){
            case R.id.id_login_sign_in:
//                Toast.makeText(this,"Sign in!",Toast.LENGTH_SHORT).show();
                setProgressDialogTile("提示").setMessage("努力登入中...");
                progressDialogShow();
                login();
                break;
            case R.id.id_login_sign_up:
//                Toast.makeText(this,"Sign up!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,SignUpActivity_.class);
                startActivity(intent);
                break;
            case R.id.id_login_wechat:
                Toast.makeText(this,"WeChat!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_login_qq:
                Toast.makeText(this,"QQ!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_login_xin_lang:
                Toast.makeText(this,"Xin Lang!",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    void login(){

        final String username = etUserName.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        JMessageClient.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int status, String s) {
                if (status == 0) {
                    progressDialogDismiss();
                    Log.i(TAG,"登录成功");

                    myPrefs.edit()
                            .username()
                            .put(username)
                            .password()
                            .put(password)
                            .apply();

                    goMain();

                } else {
                    progressDialogDismiss();
                    Log.i("LoginController", "status = " + status);
                    HandleResponseCode.onHandle(getApplicationContext(), status, false);
                }
            }
        });
    }

    void goMain(){
//        Intent intent = new Intent(LoginActivity.this,MainActivity_.class);
//        startActivity(intent);
        MyApplication.isLogin = true;
        finish();
    }
}
