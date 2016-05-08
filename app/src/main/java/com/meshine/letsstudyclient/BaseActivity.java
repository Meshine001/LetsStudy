package com.meshine.letsstudyclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.meshine.letsstudyclient.tools.AppManager;


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

    public void initTopbar(){

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

    public void progressDialogShow(){
        progressDialog.show();
    }

    public void progressDialogDismiss(){
        progressDialog.dismiss();
    }

}
