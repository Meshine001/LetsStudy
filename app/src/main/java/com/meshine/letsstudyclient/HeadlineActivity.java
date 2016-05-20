package com.meshine.letsstudyclient;

import android.view.View;

import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Meshine on 16/5/20.
 */
@EActivity(R.layout.activity_headline)
public class HeadlineActivity extends BaseActivity {

    @ViewById(R.id.id_headline_topbar)
    TopBarView topbar;

    @AfterViews
    void init(){
        initTopbar();
    }

    @Override
    public void initTopbar() {
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
