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

import com.meshine.letsstudyclient.tools.HandleResponseCode;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Ming on 2016/4/24.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";

    @ViewById(R.id.id_login_username)
    EditText etUserName;
    @ViewById(R.id.id_login_password)
    EditText etPassword;
    @ViewById(R.id.id_login_sign_in)
    Button btnSignIn;
    @ViewById(R.id.id_login_sign_up)
    TextView btnSignUp;

    private Context mContext;

    @AfterViews
    void init(){
        mContext = this;
    }


    @Click({R.id.id_login_sign_in,R.id.id_login_sign_up,R.id.id_login_wechat,R.id.id_login_qq,R.id.id_login_xin_lang})
    void onClick(View view){
        switch (view.getId()){
            case R.id.id_login_sign_in:
                Toast.makeText(this,"Sign in!",Toast.LENGTH_SHORT).show();
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
        String username = "danami";
        String password = "shyboy123";
        JMessageClient.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int status, String s) {
                if (status == 0) {
                    Log.i(TAG,"登录成功");
                    Intent intent = new Intent(LoginActivity.this,MainActivity_.class);
                    startActivity(intent);
                } else {
                    Log.i("LoginController", "status = " + status);
                    HandleResponseCode.onHandle(mContext, status, false);
                }
            }
        });
    }
}
