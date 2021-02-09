package com.kisssum.smartcity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        // 初始化数据
        initLocalData();
    }

    private void initLocalData() {
        SharedPreferences sp = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        if (sp.getString("ip", "").equals("") ||
                sp.getString("duankou", "").equals("")) {
            sp.edit().putString("ip", "106.12.160.221")
                    .putString("duankou", "8080")
                    .apply();
        }
    }
}