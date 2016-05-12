package com.meshine.letsstudyclient;

import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Ming on 2016/5/13.
 */
@EActivity(R.layout.activity_lets_run)
public class LetsRunActivity extends BaseActivity{
    @ViewById(R.id.id_lets_run_topbar)
    TopBarView topbar;

    @ViewById(R.id.id_lets_run_map)
    MapView baiduMap;


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baiduMap.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        baiduMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        baiduMap.onPause();
    }
}
