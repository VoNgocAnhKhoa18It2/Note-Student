package com.vnakhoa.vku.studentnote.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.adapter.MenuItemAdapter;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.core.ListenerMenu;
import com.vnakhoa.vku.studentnote.model.ItemInfor;
import com.vnakhoa.vku.studentnote.model.MenuItem;
import com.vnakhoa.vku.studentnote.model.Student;

import java.util.ArrayList;

public class StudentFragment extends Fragment {
    SharedPreferences preferences;
    TextView txtName,txtEmail,txtClass,txtId;
    ImageView imgStudent;
    RecyclerView listMenu;
    ListenerMenu listenerMenu;
    ArrayList<MenuItem> menuItems;
    public static Student student;
    public static Uri uriImgage;
    public static ArrayList<ItemInfor> itemInforArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getContext().getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
        String s = preferences.getString(Config.STUDENT,"");
        student = Student.convectJson(s);
        uriImgage = student.getAvataUri(Config.URL_IMG+student.getAvatar());
        itemInforArrayList = (ArrayList<ItemInfor>) student.toArray();
        listenerMenu = new ListenerMenu((Activity) getContext());
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.drawable.timeline,"Lịch trình hôm nay",listenerMenu.showListToday));
        menuItems.add(new MenuItem(R.drawable.friday,"Lịch học theo tuần",listenerMenu.showCalendar));
        menuItems.add(new MenuItem(R.drawable.user,"Thông tin cá nhân",listenerMenu.profile));
        menuItems.add(new MenuItem(R.drawable.password,"Đổi mật khẩu",listenerMenu.editPassword));
        menuItems.add(new MenuItem(R.drawable.sign_out,"Đăng xuất",listenerMenu.logout));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtClass = view.findViewById(R.id.txtClass);
        txtId = view.findViewById(R.id.txtId);
        imgStudent = view.findViewById(R.id.imgStudent);
        listMenu = view.findViewById(R.id.listMenu);
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(menuItems,getContext());
        listMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        listMenu.setAdapter(menuItemAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addData();
    }

    private void addData() {
        txtName.setText(student.getName());
        txtEmail.setText(student.getEmail());
        txtClass.setText(student.getClassActivity());
        txtId.setText(student.getIdStudent());
        Picasso.get().load(uriImgage).into(imgStudent);
    }
}