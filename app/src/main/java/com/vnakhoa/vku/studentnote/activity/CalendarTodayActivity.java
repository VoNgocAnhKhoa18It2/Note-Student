package com.vnakhoa.vku.studentnote.activity;

import static com.vnakhoa.vku.studentnote.fragment.HomeFragment.listLich;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.adapter.TimeLineAdapter;
import com.vnakhoa.vku.studentnote.model.Lich;
import com.vnakhoa.vku.studentnote.model.TimeLine;

import java.util.ArrayList;

public class CalendarTodayActivity extends AppCompatActivity {
    private RecyclerView listToday;
    ArrayList<TimeLine> timeLineList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_today);
        setTitle("Lịch hôm nay");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_ios_24);

        addControls();
    }

    private void addControls() {
        listToday = findViewById(R.id.listToday);
        formatData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void formatData() {
        timeLineList = new ArrayList<>();
        for (Lich lich : listLich) {
            int n = timeLineList.size()-1;
            ArrayList<TimeLine> arr = lich.convertTimeLine();
            for (TimeLine timeLine : arr) {
                if (n > 1 && timeLine.getImage() == R.drawable.itemone) {
                    int i = timeLine.changeTime() - timeLineList.get(n).changeTime();
                    if (i > 2) {
                        for (int j = 1;j <= i/2;j++) {
                            String x = "0"+(timeLineList.get(n).changeTime()+j*2);
                            timeLineList.add(new TimeLine(R.drawable.itemthree,x.substring(1)+":00",""));
                        }
                    }
                }
                timeLineList.add(timeLine);
            }
        }
        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(CalendarTodayActivity.this,timeLineList);
        listToday.setAdapter(timeLineAdapter);
        listToday.setLayoutManager(new LinearLayoutManager(CalendarTodayActivity.this));
    }
}