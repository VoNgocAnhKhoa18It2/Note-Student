package com.vnakhoa.vku.studentnote.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.Server;
import com.vnakhoa.vku.studentnote.Service;
import com.vnakhoa.vku.studentnote.model.Student;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout txtPassword;
    TextInputLayout txtEmail;
    Button btnLogin;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(Config.DATA_LOCAL, Context.MODE_PRIVATE);
        if (preferences != null && preferences.getString(Config.STUDENT,"").length() > 1) {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        if (!validete(txtEmail) | !validete(txtPassword)){
            return;
        }
        ProgressDialog dialog =new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Đang xử lý. Vui lòng đợi");
        dialog.show();
        String email = txtEmail.getEditText().getText().toString().trim();
        String password = txtPassword.getEditText().getText().toString().trim();
        Service service = Server.getInstance().create(Service.class);
        service.login(email,password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String messages = object.getString("messages");
                    boolean success = object.getBoolean("successful");
                    if (success) {
                        Student student = Student.convectJson(object.getJSONObject("data").toString());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Config.STUDENT,object.getJSONObject("data").toString());
                        editor.commit();
                        Log.e("LOI", student.toString() );
                        messages = messages + ". Xin Chào " + student.getName();
                        Toast.makeText(LoginActivity.this,messages,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this,messages,Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    Toast.makeText(LoginActivity.this,"Lỗi",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Đăng Nhập Thất Bại",Toast.LENGTH_LONG).show();
                Log.e("LOI", t.getMessage());
                dialog.dismiss();
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
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}