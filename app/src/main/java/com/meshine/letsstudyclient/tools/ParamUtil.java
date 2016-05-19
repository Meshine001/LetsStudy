package com.meshine.letsstudyclient.tools;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Ming on 2016/5/19.
 */
public class ParamUtil {
    public static JSONObject getUserInfoJson(UserInfo info){
        JSONObject jo = null;
        try {
            jo = new JSONObject();
            jo.put("username",info.getUserName());
            jo.put("nickname",info.getNickname());
            jo.put("star",info.getStar());
            jo.put("avatar",info.getAvatar());
            jo.put("gender",info.getGender());
            jo.put("signature",info.getSignature());
            jo.put("region",info.getRegion());
            jo.put("address",info.getAddress());
            return jo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }


}
