package com.vnakhoa.vku.studentnote.model;

import java.io.Serializable;

public class Module implements Serializable {
    private String _id;
    private String nameModule;
    private String teacher;
    private String localtion;
    private String time;

    public Module() {
    }

    public Module(String _id, String nameModule, String teacher, String localtion, String time) {
        this._id = _id;
        this.nameModule = nameModule;
        this.teacher = teacher;
        this.localtion = localtion;
        this.time = time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameModule() {
        return nameModule;
    }

    public void setNameModule(String nameModule) {
        this.nameModule = nameModule;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLocaltion() {
        return localtion;
    }

    public void setLocaltion(String localtion) {
        this.localtion = localtion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
