package com.vnakhoa.vku.studentnote.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.adapter.AdapterWork;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.model.CalendarDetail;
import com.vnakhoa.vku.studentnote.model.Work;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MoreWorkActivity extends AppCompatActivity {
    Calendar timer = Calendar.getInstance();
    SimpleDateFormat spf = new SimpleDateFormat("HH:mm");
    TextInputLayout txtChuDe,txtDiaChi;
    TextView txtGioBD,txtGioKT;
    Button btnThemLich;
    Spinner thu;
    String[] dsThu;
    ArrayAdapter<String> adapterThu;
    RecyclerView listWord;
    ArrayList<CalendarDetail> dsCV;
    AdapterWork adapterWork;
    AutoCompleteTextView chuDe;
    ArrayList<String> works;
    public static ArrayAdapter<String> listWork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_work);
        addControls();
        setTitle("Thêm Lịch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_ios_24);
        addEvents();
    }

    private void addEvents() {
        txtGioKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThoiGian(txtGioKT);
            }
        });
        txtGioBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThoiGian(txtGioBD);
            }
        });

        btnThemLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validete(txtDiaChi) | !validete(txtChuDe)){
                    return;
                }
                xuLyThemLich();
            }
        });
    }

    private boolean validete(TextInputLayout textInputLayout) {
        boolean check = true ;
        String text = textInputLayout.getEditText().getText().toString();
        if (text.isEmpty()) {
            textInputLayout.setError(textInputLayout.getHint()+" không thể để trống");
            return false;
        }
        textInputLayout.setError("");
        return check;
    }

    private void addControls() {
        txtChuDe = findViewById(R.id.txtChuDe);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtGioBD = findViewById(R.id.txtGioBD);
        txtGioKT = findViewById(R.id.txtGioKT);
        btnThemLich = findViewById(R.id.btnThemLich);
        chuDe = findViewById(R.id.chuDe);
        thu = findViewById(R.id.spnThu);
        listWord = findViewById(R.id.listWord);
        dsThu = getResources().getStringArray(R.array.Date);
        adapterThu = new ArrayAdapter<>(MoreWorkActivity.this,android.R.layout.simple_list_item_1,dsThu);
        thu.setAdapter(adapterThu);
        txtGioBD.setText(spf.format(timer.getTime()));
        txtGioKT.setText(spf.format(timer.getTime()));
    }

    private void xuLyThoiGian(final TextView textView) {
        try {
            if (textView.getText().toString().length() > 0) {
                timer.setTime(spf.parse(textView.getText().toString()));
            }
            TimePickerDialog.OnTimeSetListener callBack = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timer.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    timer.set(Calendar.MINUTE,minute);
                    timer.set(Calendar.SECOND,0);
                    textView.setText(spf.format(timer.getTime()));
                }
            };
            TimePickerDialog time = new TimePickerDialog(
                    MoreWorkActivity.this,
                    callBack,
                    timer.get(Calendar.HOUR_OF_DAY),
                    timer.get(Calendar.MINUTE),
                    true
            );
            time.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCalendar(String date,String chuDe,String diaChi,String time) {
        for (CalendarDetail cv : dsCV) {
            for (Work work : cv.getList()) {
                if (work.getLocation().equals(diaChi)
                        && work.getTime().equals(time) && work.getDayOfWeek().equals(date)
                        || work.getTime().equals(time) && work.getDayOfWeek().equals(date)) {
                    Toast.makeText(getApplicationContext(), "Lịch Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        return false;
    }

    private void xuLyThemLich() {
        String date = (!thu.getSelectedItem().equals("Chủ Nhật")) ? "T"+((String) thu.getSelectedItem()).substring(4) : "CN";
        String chuDe = txtChuDe.getEditText().getText().toString().trim();
        String diaChi = txtDiaChi.getEditText().getText().toString().trim();
        String thoiGian = txtGioBD.getText().toString()+"->"+txtGioKT.getText().toString();
        ArrayList<Work> list;
        Boolean check = true;

        if (dsCV.size() > 0) {
            if (checkCalendar(date,chuDe,diaChi,thoiGian)) return;
            for (CalendarDetail cv : dsCV) {
                if (cv.getTitle().equals(chuDe)) {
                    list = cv.getList();
                    Work w = new Work();
                    w.setLocation(diaChi);
                    w.setTime(thoiGian);
                    w.setDayOfWeek(date);
                    list.add(w);
                    cv.setList(list);
                    adapterWork.notifyDataSetChanged();
                    txtDiaChi.getEditText().setText("");
                    txtChuDe.getEditText().setText("");
                    Toast.makeText(getApplicationContext(),"Thêm Lịch Thành Công",Toast.LENGTH_SHORT).show();
                    check = false;
                    break;
                }
            }
            if (check == true) {
                CalendarDetail cv = new CalendarDetail();
                cv.setTitle(chuDe);
                list = new ArrayList<>();
                Work work = new Work();
                work.setLocation(diaChi);
                work.setTime(thoiGian);
                work.setDayOfWeek(date);
                list.add(work);
                cv.setList(list);
                dsCV.add(cv);
                listWork.add(chuDe);
                adapterWork.notifyDataSetChanged();
                txtChuDe.getEditText().setText("");
                txtDiaChi.getEditText().setText("");
                Toast.makeText(getApplicationContext(),"Thêm Lịch Thành Công",Toast.LENGTH_SHORT).show();
            }
        } else {
            CalendarDetail cv = new CalendarDetail();
            cv.setTitle(chuDe);
            list = new ArrayList<>();
            Work work = new Work();
            work.setLocation(diaChi);
            work.setTime(thoiGian);
            work.setDayOfWeek(date);
            list.add(work);
            cv.setList(list);
            dsCV.add(cv);
            listWork.add(chuDe);
            adapterWork.notifyDataSetChanged();
            txtChuDe.getEditText().setText("");
            txtDiaChi.getEditText().setText("");
            Toast.makeText(getApplicationContext(),"Thêm Lịch Thành Công",Toast.LENGTH_SHORT).show();
        }
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

    @Override
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        SharedPreferences preferences = getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String json = gson.toJson(dsCV);
        editor.putString(Config.WORK,json);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dsCV = new ArrayList<>();
        works = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
        if (preferences != null) {
            Gson gson = new Gson();
            String json = preferences.getString(Config.WORK,null);
            if (json != null) {
                ArrayList<CalendarDetail> list = gson.fromJson(json, new TypeToken<ArrayList<CalendarDetail>>(){}.getType());
                for (CalendarDetail subject : list) {
                    dsCV.add(subject);
                    works.add(subject.getTitle());
                }
            }
        }
        adapterWork = new AdapterWork(MoreWorkActivity.this,dsCV);
        listWord.setAdapter(adapterWork);
        listWord.setLayoutManager(new LinearLayoutManager(MoreWorkActivity.this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listWord);
        listWork = new ArrayAdapter<>(
                MoreWorkActivity.this,
                android.R.layout.simple_list_item_1,
                works
        );
        chuDe.setThreshold(1);
        chuDe.setAdapter(listWork);
    }
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            CalendarDetail calendarDetail = dsCV.get(position);
            dsCV.remove(position);
            adapterWork.notifyDataSetChanged();
            Snackbar.make(listWord,"Đã Xóa Lịch "+calendarDetail.getTitle(), BaseTransientBottomBar.LENGTH_SHORT).setAction("Hủy", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dsCV.add(position,calendarDetail);
                    adapterWork.notifyDataSetChanged();
                }
            }).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(MoreWorkActivity.this, android.R.color.holo_red_dark))
                    .setActionIconTint(Color.WHITE)
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .setSwipeLeftLabelTypeface(ResourcesCompat.getFont(MoreWorkActivity.this, R.font.signika_negative))
                    .setSwipeLeftLabelTextSize(1,18)
                    .addSwipeLeftLabel("Xóa")
                    .addActionIcon(R.drawable.ic_round_delete_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}