package com.vnakhoa.vku.studentnote.core;

import android.content.Context;
import android.util.TypedValue;

import com.google.android.material.textfield.TextInputLayout;

public class Config {
    public static final String DATA_LOCAL = "data-local";
    public static final String STUDENT = "student";
    public static final String WORK = "work";
    public static final String URL_IMG = "http://192.168.0.2:1201/assets/img/avatar/";

    public static final String[] headers = new String[]{
            "#",
            " Thứ 2 ",
            " Thứ 3 ",
            " Thứ 4 ",
            " Thứ 5 ",
            " Thứ 6 ",
            " Thứ 7 ",
            " Chủ Nhật ",
    };

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static boolean validete(TextInputLayout textInputLayout) {
        boolean check = true ;
        String text = textInputLayout.getEditText().getText().toString();
        if (text.isEmpty()) {
            textInputLayout.setError(textInputLayout.getHint()+" không thể để trống");
            return false;
        }
        textInputLayout.setError("");
        return check;
    }
}
