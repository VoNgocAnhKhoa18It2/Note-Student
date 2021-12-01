package com.vnakhoa.vku.studentnote.model;
import android.view.View;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private int icon;
    private String title;
    private View.OnClickListener onClickListener;

    public MenuItem(int icon, String title, View.OnClickListener onClickListener) {
        this.icon = icon;
        this.title = title;
        this.onClickListener = onClickListener;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
