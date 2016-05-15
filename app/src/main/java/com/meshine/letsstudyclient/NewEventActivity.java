package com.meshine.letsstudyclient;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
import com.meshine.letsstudyclient.BaseActivity;
import com.meshine.letsstudyclient.net.MyRestClient;
import com.meshine.letsstudyclient.net.NetInfo;
import com.meshine.letsstudyclient.widget.NiceSpinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.rest.spring.annotations.RestService;
import org.androidannotations.rest.spring.api.MediaType;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        initPicsLayout();
        initSpinner();
        initCalendar();
    }

    void initSpinner() {
        List<String> dataset = new LinkedList<>(Arrays.asList("约饭", "约跑", "约xx", "其他"));
        nsType.attachDataSource(dataset);
        List<String> dataset1 = new LinkedList<>(Arrays.asList("男女不限", "男", "女", "其他"));
        nsLimit.attachDataSource(dataset1);
    }

    void initCalendar() {
        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
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
                try {
                    JSONObject jo = new JSONObject();
                    jo.put("userId",10000);
                    newEvent(picPaths,jo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }



    @Background
    void newEvent(List<String> picPaths,JSONObject jo) {
        try {
            MultiValueMap<String,Object>  formData = new LinkedMultiValueMap<>();
            for (String path:picPaths){
                FileSystemResource file = new FileSystemResource(path);
                formData.add("file[]",file);
            }
            formData.set("data",jo.toString());
            httpClient.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA);
            String response = httpClient.addEvent(formData);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @UiThread
    void addEventResponse(JSONObject data) {

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
            picPaths.addAll(path);
            for (String s : path) {
                Log.i(TAG, s);
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
