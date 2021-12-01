package com.vnakhoa.vku.studentnote.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.Server;
import com.vnakhoa.vku.studentnote.Service;
import com.vnakhoa.vku.studentnote.activity.MoreWorkActivity;
import com.vnakhoa.vku.studentnote.adapter.ModuleAdapter;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.core.TableMainLayout;
import com.vnakhoa.vku.studentnote.model.CalendarDetail;
import com.vnakhoa.vku.studentnote.model.Module;
import com.vnakhoa.vku.studentnote.model.SampleObject;
import com.vnakhoa.vku.studentnote.model.Student;
import com.vnakhoa.vku.studentnote.model.Work;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointFragment extends Fragment {

    LinearLayout tablePoint;
    public String[] headers = {
            "#",
            "Điểm CC",
            "Điểm BT",
            "Điểm GK",
            "Điểm CK ",
            "Điểm TB ",
            "Điểm Chữ",
    };
    SharedPreferences preferences;
    Student student;
    List<SampleObject> sampleObjects;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getContext().getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
        String s = preferences.getString(Config.STUDENT,"");
        student = Student.convectJson(s);
        sampleObjects = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);

        tablePoint = view.findViewById(R.id.tablePoint);
        getPoint();
        return view;
    }

    class Point extends TableMainLayout {

        public Point(Context context, String[] headers) {
            super(context, headers);
        }

        @Override
        public List<SampleObject> sampleObjects() {
            return sampleObjects;
        }
    }

    public void getPoint() {
        Service service = Server.getInstance().create(Service.class);
        service.getPoint(student.get_id()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    boolean success = object.getBoolean("successful");
                    if (success) {
                        JSONArray data = object.getJSONArray("data");
                        for (int i=0; i < data.length(); i++) {
                            JSONObject jsonObject = data.getJSONObject(i);
                            Log.e("LOI", jsonObject.toString());
                            SampleObject sampleObject = new SampleObject(
                                    jsonObject.getString("name"),
                                    "\n"+jsonObject.getString("cc")+"\n",
                                    jsonObject.getString("bt"),
                                    jsonObject.getString("gk"),
                                    jsonObject.getString("ck"),
                                    jsonObject.getString("t10"),
                                    jsonObject.getString("letter"),
                                    null
                            );
                            sampleObjects.add(sampleObject);
                        }
                        tablePoint.removeAllViews();
                        tablePoint.addView(new Point(getContext(),headers));
                    } else {
                        String messages = object.getString("messages");
                        Toast.makeText(getContext(),messages,Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(),"Lỗi",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}