package com.meshine.letsstudyclient;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.meshine.letsstudyclient.adapter.EventAdapter;
import com.meshine.letsstudyclient.bean.Event;
import com.meshine.letsstudyclient.net.MyRestClient;
import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.tools.CommonUtil;
import com.meshine.letsstudyclient.tools.JSONUtil;
import com.meshine.letsstudyclient.tools.MapUtil;
import com.meshine.letsstudyclient.widget.ExpandListView;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by Meshine on 16/5/12.
 */
@EActivity(R.layout.activity_events)
public class EventsActivity extends BaseActivity {

    @ViewById(R.id.id_events_topbar)
    TopBarView topbar;

    @ViewById(R.id.id_events_list)
    ExpandListView eventsList;

    List<Event> events = new ArrayList<>();

    EventAdapter listAdapter;

    @Extra
    Integer type;

    @AfterViews
    void init(){
        initTopbar();
        initEventsList();
        initData();
    }

    @RestService
    MyRestClient httpClient;

    private void initData() {
     switch (type){
         case Event.NEWEST:
             getNewestEvents();
             break;
         case Event.HOTEAST:
             break;
     }
    }

    @Background
    void getNewestEvents(){
        String response = httpClient.getNewestEvents();
    }

    @UiThread
    void updateList(String response){
        JSONObject jo = new JSONObject(response);
        if (0== JSONUtil.getInt(jo,"code")){
            JSONArray ja = JSONUtil.getJSONArray(jo,"data");
            for (int i=0;i<ja.length();i++){
                JSONObject eventJo = ja.getJSONObject(i);
                Event event = new Event();
                JMessageClient.getMyInfo().getAvatarBitmap();
            }
        }else {
            CommonUtil.showToast(EventsActivity.this,JSONUtil.getString(jo,"message"));
        }
    }


    void initEventsList(){
        listAdapter = new EventAdapter(events,EventsActivity.this);
        eventsList.setAdapter(listAdapter);
    }





    @Override
    public void initTopbar() {
        topbar.setTilte(MapUtil.mapEventType(type));
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
