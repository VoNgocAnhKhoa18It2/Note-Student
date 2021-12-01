package com.vnakhoa.vku.studentnote.activity;

import static com.vnakhoa.vku.studentnote.fragment.StudentFragment.student;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import com.google.android.material.textfield.TextInputLayout;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.Server;
import com.vnakhoa.vku.studentnote.Service;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPassActivity extends AppCompatActivity {
    TextInputLayout txtPassOld,txtPassNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);
        setTitle("Sửa mật khẩu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_close_24);
        addControlls();
    }

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
                changePass();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changePass() {
        if (!validete(txtPassNew) | !validete(txtPassOld)) return;
        String passOld = txtPassOld.getEditText().getText().toString().trim();
        String passNew = txtPassNew.getEditText().getText().toString().trim();
        ProgressDialog dialog =new ProgressDialog(EditPassActivity.this);
        dialog.setMessage("Đang xử lý. Vui lòng đợi");
        dialog.show();
        Service service = Server.getInstance().create(Service.class);
        service.changePassword(student.getEmail(),passOld,passNew).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    String messages = object.getString("messages");
                    boolean success = object.getBoolean("successful");
                    dialog.dismiss();
                    if (success) {
                        Toast.makeText(EditPassActivity.this,messages,Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(EditPassActivity.this,messages,Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    Toast.makeText(EditPassActivity.this,"Lỗi",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditPassActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                Log.e("LOI", t.getMessage());
                dialog.dismiss();
            }
        });
    }

    private boolean validete(TextInputLayout textInputLayout) {
        String text = textInputLayout.getEditText().getText().toString();
        if (text.isEmpty()) {
            textInputLayout.setError(textInputLayout.getHint()+" không để trống");
            return false;
        }
        textInputLayout.setError("");
        return true;
    }

    private void addControlls() {
        txtPassOld = findViewById(R.id.txtPassOld);
        txtPassNew = findViewById(R.id.txtPassNew);
    }
}