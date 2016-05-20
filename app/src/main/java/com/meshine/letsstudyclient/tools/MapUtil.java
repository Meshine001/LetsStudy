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
}
