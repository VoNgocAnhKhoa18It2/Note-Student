package com.vnakhoa.vku.studentnote.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.Server;
import com.vnakhoa.vku.studentnote.Service;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.core.TableMainLayout;
import com.vnakhoa.vku.studentnote.model.SampleObject;
import com.vnakhoa.vku.studentnote.model.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarWeekActivity extends AppCompatActivity {

    private LinearLayout calendar;

    private Student student;
    private List<SampleObject> sampleObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_week);
        setTitle("Lịch học theo tuần");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_ios_24);
        addControls();
    }

    private void addControls() {
        calendar = findViewById(R.id.calendar);
        sampleObjects = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
        if (preferences != null) {
            String s = preferences.getString(Config.STUDENT,"");
            Gson gson = new Gson();
            student = Student.convectJson(s);
            getCalendar();
        } else {
            showTextView();
        }
    }

    private void showTextView() {
        TextView textView = new TextView(CalendarWeekActivity.this);
        textView.setText("TRỐNG");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setPadding(0,160,0,0);
        calendar.addView(textView);
        calendar.setOrientation(LinearLayout.VERTICAL);
        calendar.setGravity(Gravity.CENTER);
    }

    class CalendarTable extends TableMainLayout {

        public CalendarTable(Context context, String[] headers) {
            super(context, headers);
        }

        @Override
        public List<SampleObject> sampleObjects() {
            return sampleObjects;
        }
    }

    private void getCalendar(){
        Service service = Server.getInstance().create(Service.class);
        service.getCalendar(student.get_id()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    boolean success = object.getBoolean("successful");
                    if (success) {
                        JSONArray data = object.getJSONArray("data");
                        for (int i=0; i < data.length(); i++) {
                            JSONObject module = data.getJSONObject(i);
                            SampleObject sampleObject = converToJson(module);
                            sampleObjects.add(sampleObject);
                        }
                        calendar.removeAllViews();
                        calendar.addView(new CalendarTable(CalendarWeekActivity.this,Config.headers));
                    } else {
                        String messages = object.getString("messages");
                        Toast.makeText(CalendarWeekActivity.this,messages,Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CalendarWeekActivity.this,"Lỗi",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CalendarWeekActivity.this,t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private SampleObject converToJson(JSONObject module) throws JSONException {
        String[] arr = new String[7];
        String[] descriptions = { "LT", "TH", "BU" };
        JSONArray jsonArray = module.getJSONArray("calendars");
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            int description = Integer.parseInt(object.getString("description")) - 1;
            String s = (jsonArray.length() > 0) ? descriptions[description] : "";
            int position = (object.getString("dayOfWeek").equals("CN")) ? 6 : (Integer.parseInt(object.getString("dayOfWeek").substring(1))-2);
            arr[position] = object.getString("lesson")
                    +"\n"+object.getString("classroom")
                    +"\n"+s;
        }

        return new SampleObject(
                module.getString("nameModule"),
                "\n"+arr[0]+"\n",
                arr[1],
                arr[2],
                arr[3],
                arr[4],
                arr[5],
                arr[6]
        );
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
}