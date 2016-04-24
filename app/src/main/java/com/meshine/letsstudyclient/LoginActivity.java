package com.meshine.letsstudyclient;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Ming on 2016/4/24.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    @ViewById(R.id.id_login_username)
    EditText etUserName;
    @ViewById(R.id.id_login_password)
    EditText etPassword;
    @ViewById(R.id.id_login_sign_in)
    Button btnSignIn;
    @ViewById(R.id.id_login_sign_up)
    TextView btnSignUp;


    @Click({R.id.id_login_sign_in,R.id.id_login_sign_up,R.id.id_login_wechat,R.id.id_login_qq,R.id.id_login_xin_lang})
    void onClick(View view){
        switch (view.getId()){
            case R.id.id_login_sign_in:
                Toast.makeText(this,"Sign in!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_login_sign_up:
                Toast.makeText(this,"Sign up!",Toast.LENGTH_SHORT).show();
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
}
