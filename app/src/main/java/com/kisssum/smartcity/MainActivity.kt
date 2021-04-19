package com.kisssum.smartcity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // 初始化数据
        initLocalData();
    }

    private void initLocalData() {
        // 学校  http://106.12.160.221:8080
        // 省赛  http://124.93.196.45:10002
        // 国赛  http://182.92.105.116:8080

        SharedPreferences sp = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        if (sp.getString("ip", "").equals("") || sp.getString("duankou", "").equals("")) {
            sp.edit()
                    .putString("ip", "182.92.105.116")
                    .putString("duankou", "8080")
                    .apply();
        }


    }
}