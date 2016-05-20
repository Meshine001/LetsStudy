package com.meshine.letsstudyclient;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.meshine.letsstudyclient.adapter.EventAdapter;
import com.meshine.letsstudyclient.bean.Event;
import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.tools.MapUtil;
import com.meshine.letsstudyclient.widget.ExpandListView;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

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
    }

    void initEventsList(){

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
