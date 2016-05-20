package com.meshine.letsstudyclient;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/5/13.
 */
@EActivity(R.layout.activity_lets_run)
public class LetsRunActivity extends BaseActivity{
    private double distance;
    private int second,minute,hour;
    private boolean initPos;
    private Overlay traceOverlay=null;
    public boolean record;//是否记录轨迹

    private List<LatLng> trace = null;//轨迹
    private BDLocationListener traceListener = new TraceLocationListener();
    public LocationClient traceLocationClient = null;

    @ViewById(R.id.id_lets_run_topbar)
    TopBarView topbar;

    @ViewById(R.id.id_lets_run_map)
    MapView baiduMap;

    @ViewById(R.id.lets_run_start)
    Button startTrace;

    @ViewById(R.id.lets_run_end)
    Button endTrace;

    @ViewById(R.id.lets_run_distance)
    TextView distanceTextView;

    @ViewById(R.id.lets_run_time)
    TextView timeTextView;


    @AfterViews
    void init(){
        distance=minute=second=hour=0;
        record = false;
        initPos = false;
        initTopbar();
        traceLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        traceLocationClient.registerLocationListener( traceListener );    //注册监听函数
        initLocation();
        trace = new ArrayList();

        // 开启定位图层
        baiduMap.getMap().setMyLocationEnabled(true);
        Thread getTrace = new Thread(new Runnable() {
            @Override
            public void run() {
                trace.clear();
                while(true) {
                    traceHandler.sendEmptyMessage(1);
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        getTrace.start();
        //traceLocationClient.requestLocation();
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


    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=500;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(false);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        traceLocationClient.setLocOption(option);
    }

    private class TraceLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            traceLocationClient.stop();
            LatLng point = new LatLng(location.getLatitude(),location.getLongitude());
            if(!initPos){
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(point)
                        .zoom(18)
                        .build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                //改变地图状态
                baiduMap.getMap().setMapStatus(mMapStatusUpdate);
                initPos=true;
            }
            if(record){
                if (trace.size()>0)
                    distance += DistanceUtil.getDistance(point,trace.get(trace.size()-1));
                else
                    distance = 0;
                String strdis = String.format("%.2f公里",distance);
                distanceTextView.setText(strdis);
                trace.add(point);
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(point)
                        .zoom(18)
                        .build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                //改变地图状态
                baiduMap.getMap().setMapStatus(mMapStatusUpdate);

                if(trace.size()>=2) {
                    if(traceOverlay!=null)
                        traceOverlay.remove();
                    OverlayOptions ooPolyline = new PolylineOptions().width(15).points(trace).visible(true);
                    traceOverlay = baiduMap.getMap().addOverlay(ooPolyline);
                }
            }

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            baiduMap.getMap().setMyLocationData(locData);
        }
    }

    private Handler traceHandler = new  Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:{
                    traceLocationClient.start();
                    break;
                }
                case 2:{
                    if(record){
                        second++;
                        if (second == 60){
                            second = 0;
                            minute++;
                            if (minute == 60){
                                minute=0;
                                hour++;
                            }
                        }
                        String timestr = "" + hour + ":";
                        if (minute<10)
                            timestr += "0" + minute + ":";
                        else
                            timestr += minute + ":";
                        if (second<10)
                            timestr += "0" + second;
                        else
                            timestr += second;
                        timeTextView.setText(timestr);
                    }
                    break;
                }
            }
        }
    };

    @Click({R.id.lets_run_start,R.id.lets_run_end})
    void onClick(View view){
        switch (view.getId()){
            case R.id.lets_run_start:{
                if (record)
                    break;
                record = true;
                distance=minute=second=hour=0;
                timeTextView.setText("00:00:00");
                distanceTextView.setText("0公里");
                Thread statics = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(record) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            traceHandler.sendEmptyMessage(2);
                        }
                    }

                });
                statics.start();
                break;
            }
            case R.id.lets_run_end:{
                record = false;
                break;
            }
        }
    }

}
