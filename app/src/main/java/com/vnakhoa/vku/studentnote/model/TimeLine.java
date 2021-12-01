package com.vnakhoa.vku.studentnote.model;

import java.io.Serializable;

public class TimeLine implements Serializable {
    private int image;
    private String time;
    private String title;

    public TimeLine() {
    }

    public TimeLine(int image, String time, String title) {
        this.image = image;
        this.time = time;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int changeTime() {
        return Integer.parseInt(getTime().substring(0,2));
    }
}
