package com.vnakhoa.vku.studentnote.activity;

import static com.vnakhoa.vku.studentnote.core.Config.validete;

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
import com.vnakhoa.vku.studentnote.adapter.AdapterWorkCT;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.model.CalendarDetail;
import com.vnakhoa.vku.studentnote.model.Work;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class EditWorkDetailActivity extends AppCompatActivity {
    CalendarDetail work;
    ArrayList<CalendarDetail> listWork;
    Calendar timer = Calendar.getInstance();
    SimpleDateFormat spf = new SimpleDateFormat("HH:mm");
    public static TextInputLayout txtChuDe,txtDiaChi;
    public static TextView txtGioBD,txtGioKT;
    Button btnSuaLich;
    public static Spinner thu;
    String[] dsThu;
    ArrayAdapter<String> adapterThu;
    RecyclerView listWord;
    AdapterWorkCT workCT;
    public static int positionWork;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work_detail);

        setTitle("Sửa Lịch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_ios_24);

        addControls();
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

        btnSuaLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chuDe = txtChuDe.getEditText().getText().toString().trim();
                String bd = txtGioBD.getText().toString();
                String kt = txtGioKT.getText().toString();
                if (!chuDe.equals("") && bd.equals("") && kt.equals("") && txtDiaChi.getEditText().getText().equals("")) {
                    listWork.get(position).setTitle(chuDe);
                    Toast.makeText(getApplicationContext(), "Sửa chủ đề thành công", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validete(txtDiaChi) | !validete(txtChuDe)){
                    return;
                }
                xuLySuaLich();
            }
        });
    }

    private void xuLySuaLich() {
        String date = (!thu.getSelectedItem().equals("Chủ Nhật")) ? "T"+((String) thu.getSelectedItem()).substring(4) : "CN";
        String chuDe = txtChuDe.getEditText().getText().toString().trim();
        String diaChi = txtDiaChi.getEditText().getText().toString().trim();
        String thoiGian = txtGioBD.getText().toString()+"->"+txtGioKT.getText().toString();

        if (checkCalendarLike(date,chuDe,diaChi,thoiGian)) return;
        CalendarDetail CV = listWork.get(position);
        CV.setTitle(chuDe);
        Work w = CV.getList().get(positionWork);
        w.setDayOfWeek(date);
        w.setTime(thoiGian);
        w.setLocation(diaChi);
        workCT.notifyDataSetChanged();
        txtGioBD.setText("");
        txtGioKT.setText("");
        txtDiaChi.getEditText().setText("");
        Toast.makeText(getApplicationContext(), "Sửa lịch thành công", Toast.LENGTH_SHORT).show();
    }

    public boolean checkCalendarLike(String date,String chuDe,String diaChi,String time) {

        for (CalendarDetail cv : listWork) {
            int i = 0;
            for (Work w : cv.getList()) {
                if (cv.getTitle().equals(work.getTitle())) {
                    if (i != positionWork) {
                        if (w.getLocation().equals(diaChi)
                                && w.getTime().equals(time) && w.getDayOfWeek().equals(date)
                                || w.getTime().equals(time) && w.getDayOfWeek().equals(date)) {
                            Toast.makeText(getApplicationContext(), "Lịch Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                } else {
                    if (w.getLocation().equals(diaChi)
                            && w.getTime().equals(time) && w.getDayOfWeek().equals(date)
                            || w.getTime().equals(time) && w.getDayOfWeek().equals(date)) {
                        Toast.makeText(getApplicationContext(), "Lịch Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                i++;
            }
        }
        return false;
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
        String json = gson.toJson(listWork);
        editor.putString(Config.WORK,json);
        editor.commit();
    }

    private void addControls() {
        txtChuDe = findViewById(R.id.txtChuDe);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtGioBD = findViewById(R.id.txtGioBD);
        txtGioKT = findViewById(R.id.txtGioKT);
        btnSuaLich = findViewById(R.id.btnSuaLich);
        thu = findViewById(R.id.spnThu);
        listWord = findViewById(R.id.listWord);
        dsThu = getResources().getStringArray(R.array.Date);
        adapterThu = new ArrayAdapter<>(EditWorkDetailActivity.this,android.R.layout.simple_list_item_1,dsThu);
        thu.setAdapter(adapterThu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        position = getIntent().getIntExtra("Position",0);
        SharedPreferences preferences = getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(Config.WORK,null);

        listWork = gson.fromJson(json, new TypeToken<ArrayList<CalendarDetail>>(){}.getType());
        Log.e("LOI", "onStart: "+position);
        work = listWork.get(position);

        txtChuDe.getEditText().setText(work.getTitle());
        workCT = new AdapterWorkCT(EditWorkDetailActivity.this,listWork.get(position).getList());
        listWord.setLayoutManager(new LinearLayoutManager(EditWorkDetailActivity.this));
        listWord.setAdapter(workCT);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listWord);
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
                    EditWorkDetailActivity.this,
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

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int i = viewHolder.getAdapterPosition();
            Work work = listWork.get(position).getList().get(i);
            listWork.get(position).getList().remove(i);
            workCT.notifyDataSetChanged();
            Snackbar.make(listWord,"Đã Xóa Lịch ", BaseTransientBottomBar.LENGTH_SHORT).setAction("Hủy", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listWork.get(position).getList().add(position,work);
                    workCT.notifyDataSetChanged();
                }
            }).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(EditWorkDetailActivity.this, android.R.color.holo_red_dark))
                    .setActionIconTint(Color.WHITE)
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .setSwipeLeftLabelTypeface(ResourcesCompat.getFont(EditWorkDetailActivity.this, R.font.signika_negative))
                    .setSwipeLeftLabelTextSize(1,18)
                    .addSwipeLeftLabel("Xóa")
                    .addActionIcon(R.drawable.ic_round_delete_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}