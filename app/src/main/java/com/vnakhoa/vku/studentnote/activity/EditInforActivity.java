package com.vnakhoa.vku.studentnote.activity;

import static com.vnakhoa.vku.studentnote.fragment.StudentFragment.itemInforArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.model.ItemInfor;

public class EditInforActivity extends AppCompatActivity {

    EditText txtEdit;
    int i;
    ItemInfor itemInfor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_infor);

        txtEdit = findViewById(R.id.txtEdit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_ios_24);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        i = intent.getIntExtra("potision",0);
        itemInfor = itemInforArrayList.get(i);
        setTitle("Sửa "+ itemInfor.getTitle());
        txtEdit.setText(itemInfor.getValue());
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                if (txtEdit.getText().toString().isEmpty()) {
                    Toast.makeText(EditInforActivity.this,itemInfor.getTitle()+" không đc để trống",Toast.LENGTH_SHORT).show();
                } else {
                    itemInforArrayList.get(i).setValue(txtEdit.getText().toString().trim());
                    finish();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}