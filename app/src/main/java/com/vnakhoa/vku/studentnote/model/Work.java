package com.vnakhoa.vku.studentnote.model;

public class Work {
    private String title;
    private String dayOfWeek;
    private String time;
    private String location;

    public Work() {
    }

    public Work(String title, String dayOfWeek, String time, String location) {
        this.title = title;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
