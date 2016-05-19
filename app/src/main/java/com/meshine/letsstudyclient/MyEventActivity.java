package com.meshine.letsstudyclient;

import android.view.View;

import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Meshine on 16/5/19.
 */
@EActivity(R.layout.activity_my_event)
public class MyEventActivity extends BaseActivity {
    @ViewById(R.id.id_my_event_topbar)
    TopBarView topBarView;


    @AfterViews
    void init(){
        initTopbar();
    }

    @Override
    public void initTopbar() {
        topBarView.setOnTopBarClickListener(new TopBarView.OnTopBarClickListener() {
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
