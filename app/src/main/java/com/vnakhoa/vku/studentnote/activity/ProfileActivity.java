package com.vnakhoa.vku.studentnote.activity;

import static com.vnakhoa.vku.studentnote.fragment.StudentFragment.itemInforArrayList;
import static com.vnakhoa.vku.studentnote.fragment.StudentFragment.student;
import static com.vnakhoa.vku.studentnote.fragment.StudentFragment.uriImgage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.Server;
import com.vnakhoa.vku.studentnote.Service;
import com.vnakhoa.vku.studentnote.adapter.ItemInforAdapter;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.core.RealPathUtil;
import com.vnakhoa.vku.studentnote.model.ItemInfor;
import com.vnakhoa.vku.studentnote.model.Student;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    LinearLayout item;
    ImageView imgStudent;
    RecyclerView listInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Thông tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_ios_24);

        addControlls();
        addEvents();
    }

    private void addEvents() {
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requestPermission();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void selectImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            selectImage();
            return;
        }
        if(ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions,1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                selectImage();
        }
    }

    private void addControlls() {
        item = findViewById(R.id.item);
        imgStudent = findViewById(R.id.imgStudent);
        listInfor = findViewById(R.id.listInfor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ItemInforAdapter itemInforAdapter = new ItemInforAdapter(ProfileActivity.this,itemInforArrayList);
        listInfor.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
        listInfor.setAdapter(itemInforAdapter);
        Picasso.get().load(uriImgage).into(imgStudent);
    }

    private ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) return;
                        uriImgage = data.getData();
                    }
                }
            });

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                saveData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        ProgressDialog dialog =new ProgressDialog(ProfileActivity.this);
        dialog.setMessage("Đang xử lý. Vui lòng đợi");
        dialog.show();
        student.updateStudent(itemInforArrayList);
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),student.getName());
        RequestBody gender = RequestBody.create(MediaType.parse("multipart/form-data"),student.getGender());
        RequestBody birth = RequestBody.create(MediaType.parse("multipart/form-data"),student.convertDate());
        RequestBody phoneNumber = RequestBody.create(MediaType.parse("multipart/form-data"),student.getPhoneNumber());
        RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"),student.getAddress());
        RequestBody _id = RequestBody.create(MediaType.parse("multipart/form-data"),student.get_id());

        File file = new File(RealPathUtil.getRealPath(ProfileActivity.this,uriImgage));
        RequestBody avt= RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part avatar = MultipartBody.Part.createFormData("avatar",file.getName(),avt);

        Service service = Server.getInstance().create(Service.class);
        service.updateStudent(name,gender,birth,phoneNumber,address,_id,avatar)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            SharedPreferences preferences = getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
                            JSONObject object = new JSONObject(response.body().string());
                            String messages = object.getString("messages");
                            boolean success = object.getBoolean("successful");
                            if (success) {
                                student = Student.convectJson(object.getJSONObject("data").toString());
                                itemInforArrayList = (ArrayList<ItemInfor>) student.toArray();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(Config.STUDENT,object.getJSONObject("data").toString());
                                editor.commit();
                                Log.e("LOI", student.toString() );
                                Toast.makeText(ProfileActivity.this,messages,Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ProfileActivity.this,messages,Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e){
                            Toast.makeText(ProfileActivity.this,"Lỗi",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("LOI", t.getMessage());
                        dialog.dismiss();
                    }
                });
    }
}