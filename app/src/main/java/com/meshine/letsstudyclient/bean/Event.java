package com.meshine.letsstudyclient.bean;

/**
 * Created by Ming on 2016/5/7.
 */
public class Event {

    public static final int NEWEST = 0;
    public static final int HOTEAST = 1;
    public static final int EATE = 2;
    public static final int RUN = 3;
    public static final int STUDY = 4;
    public static final int REPORT = 5;
    public static final int OTHERS = 6;

    public static final int LIMIT_ALL = 0;
    public static final int LIMIT_MALE = 1;
    public static final int LIMIT_FEMALE = 2;
    public static final int LIMIT_UNKNOWN = 3;


    private Long id;
    private int eventType;
    private String title;
    private int count;
    private int limit;
    private Long dateTime;
    private Long endTime;
    private Long createTime;
    private String place;
    private String details;
    private String userName;
    private String pic1;
    private String pic2;
    private String pic3;
    private String pic4;


    public Event() {

    }

    public Event(Long id,int eventType, String title, int count, int limit, Long dateTime, Long endTime,  String place, String details, Long createTime,String userName, String pic1, String pic2, String pic3, String pic4) {
        this.id = id;
        this.eventType = eventType;
        this.title = title;
        this.count = count;
        this.limit = limit;
        this.dateTime = dateTime;
        this.endTime = endTime;
        this.createTime = createTime;
        this.place = place;
        this.details = details;
        this.userName = userName;
        this.pic1 = pic1;
        this.pic2 = pic2;
        this.pic3 = pic3;
        this.pic4 = pic4;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventType=" + eventType +
                ", title='" + title + '\'' +
                ", count=" + count +
                ", limit=" + limit +
                ", dateTime=" + dateTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", place='" + place + '\'' +
                ", details='" + details + '\'' +
                ", userName='" + userName + '\'' +
                ", pic1='" + pic1 + '\'' +
                ", pic2='" + pic2 + '\'' +
                ", pic3='" + pic3 + '\'' +
                ", pic4='" + pic4 + '\'' +
                '}';
    }
}
