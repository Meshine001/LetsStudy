package com.meshine.letsstudyclient.tools;

import com.meshine.letsstudyclient.bean.Event;

/**
 * Created by Meshine on 16/5/20.
 */
public class MapUtil {
    /**
     * 获取对应类型的文本
     * @param type
     * @return
     */
    public static String mapEventType(int type){
        switch (type){
            case Event.NEWEST:
                return "最新活动";
            case Event.HOTEAST:
                return "热门活动";
            case Event.EATE:
                return "约饭";
            case Event.RUN:
                return "约跑";
            case Event.STUDY:
                return "约自习";
            case Event.REPORT:
                return "约讲座";
            case Event.OTHERS:
                return "其他活动";
        }
        return null;
    }

    /**
     * 获取Limit对应文本
     * @param type
     * @return
     */
    public static String mapLimitType(int type){
        switch (type){
            case Event.LIMIT_ALL:
                return "男女不限";
            case Event.LIMIT_MALE:
                return "男生";
            case Event.LIMIT_FEMALE:
                return "女生";
            case Event.LIMIT_UNKNOWN:
                return "其他";

        }
        return null;
    }
}
