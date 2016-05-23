package com.meshine.letsstudyclient.tools;

import android.util.Log;

import com.meshine.letsstudyclient.bean.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meshine on 16/5/23.
 */
public class EventUtil {

    private static final String TAG = EventUtil.class.getName();

    public static List<Event> parseEventsData(String data) {
        try {
            List<Event> events = new ArrayList<>();
            JSONObject jo = new JSONObject(data);
            JSONArray ja = JSONUtil.getJSONArray(jo, "data");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject eventJo = ja.getJSONObject(i);

                Event event = new Event(
                        eventJo.getLong("id"),
                        eventJo.getInt("eventType"),
                        eventJo.getString("title"),
                        eventJo.getInt("count"),
                        eventJo.getInt("limit"),
                        eventJo.getLong("dateTime"),
                        eventJo.getLong("endTime"),
                        eventJo.getString("place"),
                        eventJo.getString("details"),
                        eventJo.getLong("createTime"),
                        eventJo.getString("userName"),
                        eventJo.getString("pic1"),
                        eventJo.getString("pic2"),
                        eventJo.getString("pic3"),
                        eventJo.getString("pic4")
                );
                events.add(event);
            }
            for (Event e:events){
                Log.i(TAG,e.toString());
            }
            return events;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
