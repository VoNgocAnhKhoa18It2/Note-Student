package com.vnakhoa.vku.studentnote.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.fragment.CalendarFragment;
import com.vnakhoa.vku.studentnote.fragment.HomeFragment;
import com.vnakhoa.vku.studentnote.fragment.PointFragment;
import com.vnakhoa.vku.studentnote.fragment.StudentFragment;

public class MainActivity extends AppCompatActivity {

    //Fragment
    private HomeFragment homeFragment;
    private CalendarFragment calendarFragment;
    private PointFragment pointFragment;
    private StudentFragment studentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }

    @SuppressLint("NonConstantResourceId")
    private void addControls() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationbar);

        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        pointFragment = new PointFragment();
        studentFragment = new StudentFragment();

        setFragment(homeFragment);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.home:
                    item.setIcon(R.drawable.ic_round_home_24);
                    setFragment(homeFragment);
                    return true;

                case R.id.calendar:
                    setFragment(calendarFragment);
                    return true;

                case R.id.point:
                    setFragment(pointFragment);
                    return true;

                case R.id.account:
                    setFragment(studentFragment);
                    return true;

                default:
                    return false;
            }
        });
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}