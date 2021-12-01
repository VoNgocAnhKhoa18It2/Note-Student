package com.vnakhoa.vku.studentnote.core;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.vnakhoa.vku.studentnote.activity.CalendarTodayActivity;
import com.vnakhoa.vku.studentnote.activity.CalendarWeekActivity;
import com.vnakhoa.vku.studentnote.activity.EditPassActivity;
import com.vnakhoa.vku.studentnote.activity.LoginActivity;
import com.vnakhoa.vku.studentnote.activity.ProfileActivity;

public class ListenerMenu {

    private Activity context;

    public ListenerMenu(Activity context) {
        this.context = context;
    }

    public View.OnClickListener logout = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences preferences = context.getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Bạn Có Muốn Xóa Lịch Cá Nhân Không");
            builder.setCancelable(false);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    preferences.edit().clear().commit();
                    Intent logout = new Intent(context, LoginActivity.class);
                    logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(logout);
                    context.finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(Config.STUDENT,"");
                    editor.commit();
                    Intent logout = new Intent(context,LoginActivity.class);
                    logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(logout);
                    context.finish();
                }
            });
            builder.create().show();
        }
    };

    public View.OnClickListener profile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ProfileActivity.class);
            context.startActivity(intent);
        }
    };

    public View.OnClickListener showListToday = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, CalendarTodayActivity.class);
            context.startActivity(intent);
        }
    };

    public View.OnClickListener showCalendar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, CalendarWeekActivity.class);
            context.startActivity(intent);
        }
    };

    public View.OnClickListener editPassword = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, EditPassActivity.class);
            context.startActivity(intent);
        }
    };

}
