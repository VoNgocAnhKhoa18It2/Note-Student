package com.vnakhoa.vku.studentnote.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CalendarDetail implements Serializable {
    private String title;
    private ArrayList<Work> list;

    public CalendarDetail() {
    }

    public CalendarDetail(String title, ArrayList<Work> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Work> getList() {
        return list;
    }

    public void setList(ArrayList<Work> list) {
        this.list = list;
    }
}
