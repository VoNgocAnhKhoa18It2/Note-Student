package com.vnakhoa.vku.studentnote.model;

import java.io.Serializable;

public class ItemInfor implements Serializable {
    private String title;
    private String value;
    private boolean status;

    public ItemInfor() {
    }

    public ItemInfor(String title, String value, boolean status) {
        this.title = title;
        this.value = value;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
