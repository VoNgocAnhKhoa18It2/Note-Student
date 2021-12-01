package com.vnakhoa.vku.studentnote.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.Server;
import com.vnakhoa.vku.studentnote.Service;
import com.vnakhoa.vku.studentnote.adapter.ModuleAdapter;
import com.vnakhoa.vku.studentnote.adapter.TodayAdapter;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.model.CalendarDetail;
import com.vnakhoa.vku.studentnote.model.Lich;
import com.vnakhoa.vku.studentnote.model.Module;
import com.vnakhoa.vku.studentnote.model.Student;
import com.vnakhoa.vku.studentnote.model.Work;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView listToday,listModule;
    private ProgressBar prob2,prob1;
    private Student student;
    public static ArrayList<Lich> listLich;
    private Service service;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listToday = view.findViewById(R.id.listToday);
        listModule = view.findViewById(R.id.listModule);
        prob2 = view.findViewById(R.id.prob2);
        prob1 = view.findViewById(R.id.prob1);
        getCalendarToday();
        getCalendar();
        return view;
    }

    private void getCalendar() {
        service.getModule(student.get_id()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    boolean success = object.getBoolean("successful");
                    if (success) {
                        ArrayList<Module> list = new ArrayList<>();
                        String[] arr = { "LT", "TH", "BU" };
                        JSONArray data = object.getJSONArray("data");
                        for (int i=0; i < data.length(); i++) {
                            JSONObject jsonObject = data.getJSONObject(i);
                            String title = jsonObject.getJSONObject("module").getString("nameModule");
                            int d = Integer.parseInt(jsonObject.getString("description")) - 1;
                            Module module = new Module();
                            module.set_id(jsonObject.getString("_id"));
                            module.setNameModule(title + " | " + arr[d]);
                            module.setTeacher(jsonObject.getJSONObject("teacher").getString("name"));
                            module.setTime(jsonObject.getString("lesson"));
                            module.setLocaltion(jsonObject.getString("classroom"));
                            list.add(module);
                        }
                        prob2.setVisibility(View.GONE);
                        listModule.setVisibility(View.VISIBLE);
                        ModuleAdapter todayAdapter = new ModuleAdapter(getContext(),list);
                        listModule.setAdapter(todayAdapter);
                        listModule.setLayoutManager(new GridLayoutManager(getContext(),2));

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

    private void getCalendarToday() {
        service.getToday(student.get_id()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    boolean success = object.getBoolean("successful");
                    if (success) {
                        JSONArray data = object.getJSONArray("data");
                        for (int i=0; i < data.length(); i++) {
                            JSONObject jsonObject = data.getJSONObject(i);
                            Lich lich = new Lich();
                            lich.setImage(R.drawable.ic_round_school_24);
                            lich.setTitle(jsonObject.getJSONObject("module").getString("nameModule"));
                            lich.setTime(jsonObject.getString("lesson"));
                            lich.setLocation(jsonObject.getString("classroom"));
                            listLich.add(lich);
                        }
                        prob1.setVisibility(View.GONE);
                        listToday.setVisibility(View.VISIBLE);
                        Collections.sort(listLich, new Comparator<Lich>() {
                            @Override
                            public int compare(Lich o1, Lich o2) {
                                return o1.getTime().compareTo(o2.getTime());
                            }
                        });
                        TodayAdapter todayAdapter = new TodayAdapter(getContext(),listLich);
                        listToday.setAdapter(todayAdapter);
                        listToday.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getContext().getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
        String s = preferences.getString(Config.STUDENT,"");
        student = Student.convectJson(s);
        service = Server.getInstance().create(Service.class);
        listLich = new ArrayList<>();
        Gson gson = new Gson();
        String json = preferences.getString(Config.WORK,null);
        Calendar calendar = Calendar.getInstance();
        Log.e("LOI", "onCreate: "+Calendar.MONDAY+","+Calendar.TUESDAY+
                ","+Calendar.WEDNESDAY+","+Calendar.THURSDAY+","+Calendar.FRIDAY
                +","+Calendar.SATURDAY+","+Calendar.SUNDAY);
        if (json != null) {
            ArrayList<CalendarDetail> listWork = gson.fromJson(json, new TypeToken<ArrayList<CalendarDetail>>(){}.getType());
            for (CalendarDetail detail : listWork) {
                for (Work work :detail.getList()) {
                    int dayOfWeek = (work.getDayOfWeek().equals("CN")) ? 1 : (Integer.parseInt(work.getDayOfWeek().substring(1)));
                    if (dayOfWeek == calendar.get(Calendar.DAY_OF_WEEK)) {
                        Lich lich = new Lich();
                        lich.setImage(R.drawable.ic_round_school_24);
                        lich.setTitle(detail.getTitle());
                        lich.setTime(work.getTime());
                        lich.setLocation(work.getLocation());
                        listLich.add(lich);
                    }
                }
            }
        }
    }
}