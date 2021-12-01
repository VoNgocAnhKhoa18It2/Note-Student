package com.vnakhoa.vku.studentnote.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.activity.MoreWorkActivity;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.core.TableMainLayout;
import com.vnakhoa.vku.studentnote.model.CalendarDetail;
import com.vnakhoa.vku.studentnote.model.SampleObject;
import com.vnakhoa.vku.studentnote.model.Work;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment {

    private LinearLayout calendarWork;
    private FloatingActionButton addCV;
    private ArrayList<CalendarDetail> listWork;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarWork = view.findViewById(R.id.calendarWork);
        addCV = view.findViewById(R.id.addCV);
        addEvents();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences = getContext().getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
        if (preferences != null) {
            Gson gson = new Gson();
            String json = preferences.getString(Config.WORK,null);
            if (json != null) {
                listWork = gson.fromJson(json, new TypeToken<ArrayList<CalendarDetail>>(){}.getType());
                if (listWork.size() > 0) {
                    calendarWork.removeAllViews();
                    calendarWork.setGravity(Gravity.TOP);
                    calendarWork.addView(new WorkPersonal(getContext(),Config.headers));
                } else {
                    showTextView();
                }
            } else {
                showTextView();
            }
        } else {
            showTextView();
        }

    }

    private void showTextView() {
        TextView textView = new TextView(getContext());
        textView.setText("TRỐNG");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setPadding(0,160,0,0);
        calendarWork.addView(textView);
        calendarWork.setOrientation(LinearLayout.VERTICAL);
        calendarWork.setGravity(Gravity.CENTER);
    }


    private void addEvents() {
        addCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MoreWorkActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    private SampleObject converToObject(CalendarDetail calendarSubject){
        String[] arr = new String[7];
        for (Work work : calendarSubject.getList()) {
            int position = (work.getDayOfWeek().equals("CN")) ? 6 : (Integer.parseInt(work.getDayOfWeek().substring(1))-2);
            arr[position] = work.getTime()
                    +"\n"+work.getLocation();
        }

        return new SampleObject(
                calendarSubject.getTitle(),
                "\n"+arr[0]+"\n",
                arr[1],
                arr[2],
                arr[3],
                arr[4],
                arr[5],
                arr[6]
        );
    }

    class WorkPersonal extends TableMainLayout {

        public WorkPersonal(Context context, String[] headers) {
            super(context, headers);
        }

        @Override
        public List<SampleObject> sampleObjects() {
            List<SampleObject> sampleObjects = new ArrayList<>();
            for(CalendarDetail calendarSubject : listWork){
                SampleObject sampleObject = converToObject(calendarSubject);
                sampleObjects.add(sampleObject);
            }
            return sampleObjects;
        }
    }
}