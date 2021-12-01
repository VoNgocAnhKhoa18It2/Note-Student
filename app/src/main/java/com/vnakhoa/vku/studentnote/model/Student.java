package com.vnakhoa.vku.studentnote.model;

import android.annotation.SuppressLint;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Student implements Serializable {
    private String _id;
    private String idStudent;
    private String name;
    private String email;
    private String gender;
    private String birth;
    private String classActivity;
    private String phoneNumber;
    private String address;
    private String avatar;
    private String[] modules;

    public Student() {
    }

    public Student(String _id, String idStudent, String name, String email, String gender, String birth, String classActivity, String phoneNumber, String address, String avatar, String[] modules) {
        this._id = _id;
        this.idStudent = idStudent;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.classActivity = classActivity;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.avatar = avatar;
        this.modules = modules;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getClassActivity() {
        return classActivity;
    }

    public void setClassActivity(String classActivity) {
        this.classActivity = classActivity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getModules() {
        return modules;
    }

    public void setModules(String[] modules) {
        this.modules = modules;
    }

    public static Student convectJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Student>(){}.getType());
    }

    public Uri getAvataUri(String avatar){
        Uri uri = null;
        try {
            if (avatar.equals("")) avatar = this.getAvatar();
            URL url = new URL(avatar);
            uri = Uri.parse(url.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }

    public void updateStudent(ArrayList<ItemInfor> list) {
        this.name = list.get(1).getValue().trim();
        this.gender = list.get(2).getValue().trim();
        this.birth = list.get(3).getValue().trim();
        this.phoneNumber = list.get(6).getValue().trim();
        this.address = list.get(7).getValue().trim();
    }

    public String formatDate() {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss.SSS'Z'");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(this.birth);
            this.birth = dt1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBirth();
    }

    public String convertDate() {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat output = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss.SSS'Z'");
            Date date = input.parse(this.birth);
            this.birth = output.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBirth();
    }

    public List<ItemInfor> toArray() {
        List<ItemInfor> list = new ArrayList<>();

        list.add(new ItemInfor("Mã Sinh Viên",this.idStudent,false));
        list.add(new ItemInfor("Họ & Tên",this.name,true));
        list.add(new ItemInfor("Giới Tính",this.gender,true));
        list.add(new ItemInfor("Ngày Sinh",this.formatDate(),true));
        list.add(new ItemInfor("Email",this.email,false));
        list.add(new ItemInfor("Lớp Sinh Hoạt",this.classActivity,false));
        list.add(new ItemInfor("Số Điện Thoại",this.phoneNumber,true));
        list.add(new ItemInfor("Địa Chỉ",this.address,true));

        return list;
    }

    @Override
    public String toString() {
        return "Student{" +
                "_id='" + _id + '\'' +
                ", idStudent='" + idStudent + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birth='" + birth + '\'' +
                ", classActivity='" + classActivity + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", modules=" + Arrays.toString(modules) +
                '}';
    }
}
