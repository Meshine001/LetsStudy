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
import com.meshine.letsstudyclient.tools.HandleResponseCode;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Ming on 2016/5/4.
 */
@EActivity(R.layout.activity_sign_up)
public class SignUpActivity extends Activity {

    private static final String TAG = "SignUpActivity";

    //TopBar
    @ViewById(R.id.id_topbar_back)
    ImageView topbarBack;
    @ViewById(R.id.id_topbar_title)
    TextView topbarTitle;
    @ViewById(R.id.id_topbar_right_iv)
    ImageView topbarRightIV;

    @ViewById(R.id.id_sign_up_username)
    EditText etUserName;
    @ViewById(R.id.id_sign_up_password)
    EditText etPassword;
    @ViewById(R.id.id_sign_up_submit)
    Button btnSignUp;

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
            JMessageClient.register(username,password , new BasicCallback() {
                @Override
                public void gotResult(int status, String s) {
                    if (status == 0){
                        Log.i(TAG,"注册新用户成功");
                        login(username,password);
                    }else {
                        Log.i(TAG,"注册新用户失败");
                        HandleResponseCode.onHandle(SignUpActivity.this, status, false);
                    }
                }
            });
        }
    }

    void login(String username,String password){
        Log.i(TAG,"开始登录");
        JMessageClient.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int status, String s) {
                if (status == 0) {
                    Log.i(TAG,"登录成功");
                    Intent intent = new Intent(SignUpActivity.this,MainActivity_.class);
                    startActivity(intent);
                } else {
                    Log.i(TAG,"登录失败");
                    HandleResponseCode.onHandle(SignUpActivity.this, status, false);
                }
            }
        });
    }

    boolean validateInput(){

        return true;
    }


    void initTopbar(){
        topbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topbarTitle.setText("注册");
        topbarRightIV.setVisibility(View.GONE);
    }


}
