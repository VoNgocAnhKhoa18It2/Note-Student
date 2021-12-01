package com.vnakhoa.vku.studentnote.model;

import com.vnakhoa.vku.studentnote.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Lich implements Serializable {
    private int image;
    private String title;
    private String time;
    private String location;

    public Lich() {
    }

    public Lich(int image, String title, String time, String location) {
        this.image = image;
        this.title = title;
        this.time = time;
        this.location = location;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ArrayList<TimeLine> convertTimeLine() {
        ArrayList<TimeLine> arr = new ArrayList<>();
        String[] arrTime = this.time.split("->");
        int n = Integer.parseInt(arrTime[1].substring(0,2)) - Integer.parseInt(arrTime[0].substring(0,2));
        TimeLine timeStart = new TimeLine();
        timeStart.setImage(R.drawable.itemone);
        timeStart.setTime(arrTime[0]);
        timeStart.setTitle("");
        TimeLine timeEnd = new TimeLine();
        timeEnd.setImage(R.drawable.itemfour);
        timeEnd.setTime(arrTime[1]);
        timeEnd.setTitle(getTitle());
        arr.add(timeStart);
        arr.add(timeEnd);

        if (n > 2) {
            arr.get(1).setTitle("");
            for (int j = 1;j <= n/2;j++) {
                String x = "0"+(Integer.parseInt(arrTime[0].substring(0,2))+j);
                arr.add(j,new TimeLine(R.drawable.itemtwo,x.substring(1)+":00",getTitle()));
            }
        }
        return arr;
    }

}
