package com.meshine.letsstudyclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.bumptech.glide.Glide;
import com.meshine.letsstudyclient.net.MyRestClient;
import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.tools.BitmapUtil;
import com.meshine.letsstudyclient.tools.CommonUtil;
import com.meshine.letsstudyclient.tools.JSONUtil;
import com.meshine.letsstudyclient.tools.TimeFormat;
import com.meshine.letsstudyclient.widget.NiceSpinner;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import cn.jpush.im.android.api.JMessageClient;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Meshine on 16/5/9.
 */
@EActivity(R.layout.activity_new_event)
public class NewEventActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = NewEventActivity.class.getName();

    public static final int REQUEST_IMAGE = 0x1000;
    public static final int REQUEST__PRE_IMAGE = 0x1100;

    @ViewById(R.id.id_new_event_topbar)
    TopBarView topbar;

    @ViewById(R.id.id_new_event_pic1)
    ImageView pic1;
    @ViewById(R.id.id_new_event_pic2)
    ImageView pic2;
    @ViewById(R.id.id_new_event_pic3)
    ImageView pic3;
    @ViewById(R.id.id_new_event_pic4)
    ImageView pic4;
    @ViewById(R.id.id_new_event_title)
    EditText etTile;
    @ViewById(R.id.id_new_event_type)
    NiceSpinner nsType;
    @ViewById(R.id.id_new_event_limit)
    NiceSpinner nsLimit;
    @ViewById(R.id.id_new_event_count)
    EditText etCount;
    @ViewById(R.id.id_new_event_date_time)
    TextView tvDateTime;
    @ViewById(R.id.id_new_event_end_time)
    TextView tvEndTime;
    @ViewById(R.id.id_new_event_place)
    EditText etPlace;
    @ViewById(R.id.id_new_event_details)
    EditText etDetails;
    @ViewById(R.id.id_new_event_submit)
    Button btnSubmit;

    ArrayList<String> picPaths = new ArrayList<>();
    List<ImageView> picIvs = new ArrayList<>();

    boolean[] picStates = {false, false, false, false};

    List<String> remotePics = new ArrayList<>();

    @RestService
    MyRestClient httpClient;


    private static final String TIME_PATTERN = "HH:mm";
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    String dateTimeStr = "";
    String endTimeStr = "";
    boolean isDateTime = true;

    @AfterViews
    void init() {
        initTopbar();
        initPicsLayout();
        initSpinner();
        initCalendar();
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

    void initSpinner() {
        List<String> dataset = new LinkedList<>(Arrays.asList("约饭", "约跑", "约xx", "其他"));
        nsType.attachDataSource(dataset);
        List<String> dataset1 = new LinkedList<>(Arrays.asList("男女不限", "男", "女", "其他"));
        nsLimit.attachDataSource(dataset1);
    }

    void initCalendar() {
        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.CHINA);
    }

    @Click({R.id.id_new_event_pic1,
            R.id.id_new_event_pic2,
            R.id.id_new_event_pic3,
            R.id.id_new_event_pic4,
            R.id.id_new_event_date_time,
            R.id.id_new_event_end_time,
            R.id.id_new_event_submit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_new_event_pic1:
                doPicClick(1);
                break;
            case R.id.id_new_event_pic2:
                doPicClick(2);
                break;
            case R.id.id_new_event_pic3:
                doPicClick(3);
                break;
            case R.id.id_new_event_pic4:
                doPicClick(4);
                break;
            case R.id.id_new_event_date_time:
                isDateTime = true;
                dateTimeStr = "";
                DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                break;
            case R.id.id_new_event_end_time:
                isDateTime = false;
                endTimeStr = "";
                DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                break;
            case R.id.id_new_event_submit:

                if (validateForm()) {
                   uploadPics();
                }
               // uploadPics();
                break;
        }
    }





    @Background
    void uploadPics() {
        remotePics.clear();
        if (picPaths.size() == 0){
            addEvent();
            return;
        }
        try {
            MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();

            for (String path : picPaths){
                FileSystemResource image = new FileSystemResource(path);
                data.add("file[]",image);
            }
            httpClient.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
            String response = httpClient.uploadFile(data);
            afterUpload(response);
        } catch (RestClientException e) {
            e.printStackTrace();
            CommonUtil.showToast(this,"网络请求失败");
        }
    }

    @UiThread
    void afterUpload(String response){
        try {
            JSONObject jo = new JSONObject(response);
            if (0 == JSONUtil.getInt(jo,"code")){
                Log.i(TAG,jo.toString());
                JSONArray ja = JSONUtil.getJSONArray(jo,"data");
                Log.i(TAG,ja.toString());
                for (int i=0;i<ja.length();i++){
                    remotePics.add(ja.getString(i));
                }
                addEvent();
            }else {
                CommonUtil.showToast(this,JSONUtil.getString(jo,"message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    boolean validateForm() {
        if (etTile.getText().toString().trim().equals("")) {
            CommonUtil.showToast(this, "请填写标题!");
            return false;
        }

        if (etCount.getText().toString().trim().equals("")) {
            if (Integer.parseInt(etCount.getText().toString().trim()) < 2) {
                CommonUtil.showToast(this, "人数不能少于2人!");
                return false;
            }
            CommonUtil.showToast(this, "请设置人数!");

            return false;
        }

        if (tvDateTime.getText().toString().trim().equals("点击设置")) {
            CommonUtil.showToast(this, "请设置活动时间!");
            return false;
        }

        if (tvEndTime.getText().toString().trim().equals("点击设置")) {
            CommonUtil.showToast(this, "请设置报名截止时间!");
            return false;
        }

        if (etPlace.getText().toString().trim().equals("")) {
            CommonUtil.showToast(this, "请填写活动地点!");
            return false;
        }
        if (etDetails.getText().toString().trim().equals("")) {
            CommonUtil.showToast(this, "请填写活动描述!");
            return false;
        }

        return true;

    }


    /**
     * 生产表单数据
     *
     * @return
     * @throws JSONException
     */
    JSONObject generateEventJson() {
        try {
            JSONObject jo = new JSONObject();
            jo.put("eventType", nsType.getSelectedIndex());
            jo.put("title", etPlace.getText().toString());
            jo.put("count", etCount.getText().toString());
            jo.put("dateTime", TimeFormat.getTimeStamp(tvDateTime.getText().toString()));
            jo.put("endTime", TimeFormat.getTimeStamp(tvEndTime.getText().toString()));
            jo.put("place", etPlace.getText().toString());
            jo.put("details", etDetails.getText().toString());
            jo.put("userId", JMessageClient.getMyInfo().getUserID());
            if (remotePics.size() > 0){
                for (int i = 1; i <= remotePics.size(); i++) {
                    jo.put("pic" + i, remotePics.get(i - 1));
                }
            }
            return jo;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Background
    void addEvent() {
        try {
            String response = httpClient.addEvent(generateEventJson().toString());
            Log.i(TAG, response);
            parseResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @UiThread
    void parseResponse(String response) {
        try {
            JSONObject jo = new JSONObject(response);
            if (0==JSONUtil.getInt(jo,"code")){
                CommonUtil.showToast(this, "发起活动成功");
                Intent intent = new Intent(NewEventActivity.this,MyEventActivity_.class);
                startActivity(intent);
                AppManager.getAppManager().finishActivity();
            }else {
                CommonUtil.showToast(this, "发起活动失败");
            }

        } catch (Exception e) {
//            e.printStackTrace();
            CommonUtil.showToast(this, "发起活动失败");
        }
    }


    /**
     * pic 点击事件具体逻辑
     *
     * @param i pic的序号
     */
    void doPicClick(int i) {
        if (picStates[i - 1]) {
            preViewPic(i);
        } else {
            openSelector();
        }
    }

    /**
     * 打开图片选择器
     */
    void openSelector() {
        Intent intent = new Intent(NewEventActivity.this, MultiImageSelectorActivity.class);
        // whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 4 - picPaths.size());
        // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // default select images (support array list)
        //intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, defaultDataArray);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @OnActivityResult(REQUEST_IMAGE)
    void onRequestImageResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the result list of select image paths
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            // do your logic ....

            for (String s : path) {
                Log.i(TAG, "压缩图片"+s);
                Bitmap bmp = BitmapUtil.getimage(s);
                String p = BitmapUtil.saveBitmap(bmp);
                picPaths.add(p);
            }
            updatePics();
        }
    }

    /**
     * 更新图片栏
     */
    private void updatePics() {
        resetPicsLayout();

        int plusPos = 0;
        for (int i = 0; i < picPaths.size(); i++) {
            ImageView iv = picIvs.get(i);
            plusPos++;
            iv.setImageURI(Uri.fromFile(new File(picPaths.get(i))));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setVisibility(View.VISIBLE);
            picStates[i] = true;
        }
        if (plusPos < 4) {
            ImageView iv = picIvs.get(plusPos);
            iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus_rec));
            iv.setVisibility(View.VISIBLE);
            picStates[plusPos] = false;
        }
    }

    /**
     * 预览图片
     *
     * @param i 图片序号
     */
    void preViewPic(int i) {
        Intent intent = new Intent(NewEventActivity.this, PreViewImageActivity_.class);
        intent.putExtra("position", (i - 1));
        intent.putStringArrayListExtra("picPaths", picPaths);
        startActivityForResult(intent, REQUEST__PRE_IMAGE);
    }

    @OnActivityResult(REQUEST__PRE_IMAGE)
    void onRequestPreImageResult(int resultCode, Intent data) {
        Bundle b = data.getExtras();
        int position = b.getInt("position");
        if (position != -1) {
            picPaths.remove(position);
            updatePics();
        }
    }


    /**
     * 初始化图片选择栏
     */
    void initPicsLayout() {
        picIvs.add(pic1);
        picIvs.add(pic2);
        picIvs.add(pic3);
        picIvs.add(pic4);

        resetPicsLayout();


    }

    void resetPicsLayout() {
        pic1.setVisibility(View.VISIBLE);
        pic1.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus_rec));
        pic2.setVisibility(View.INVISIBLE);
        pic3.setVisibility(View.INVISIBLE);
        pic4.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        if (isDateTime) {
            dateTimeStr += dateFormat.format(calendar.getTime());
        } else {
            endTimeStr += dateFormat.format(calendar.getTime());
        }
        TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        if (isDateTime) {
            dateTimeStr += " ";
            dateTimeStr += timeFormat.format(calendar.getTime());
            tvDateTime.setText(dateTimeStr);
        } else {
            endTimeStr += " ";
            endTimeStr += timeFormat.format(calendar.getTime());
            tvEndTime.setText(endTimeStr);
        }
    }
}
