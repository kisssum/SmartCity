package com.kisssum.smartcity.navigation.news;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class NewsModel extends AndroidViewModel {
    public NewsModel(@NonNull @NotNull Application application) {
        super(application);
        loadData(true);
    }

    private int page = 1;
    private int limit = 10;
    private MutableLiveData<List<JSONObject>> data = new MutableLiveData<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            String doc = (String) msg.obj;

            if (msg.what == 1) {
                data.setValue(getJsonNewsList(doc));
            } else if (msg.what == 2) {
                data.postValue(getJsonNewsList(doc));
            }
        }
    };

    List<JSONObject> getData() {
        return data.getValue();
    }

    int getCount() {
        if (data.getValue() == null)
            return 0;
        else
            return data.getValue().size();
    }

    private List<JSONObject> getJsonNewsList(String doc) {
        List<JSONObject> rData = new ArrayList<>();

        try {
            JSONObject main = new JSONObject(doc);
            String errMsg = main.getString("errMsg");

            if (errMsg.equals("ok")) {
                JSONArray list = main.getJSONObject("data").getJSONArray("list");

                for (int i = 0; i < list.length(); i++) {
                    JSONObject map = list.getJSONObject(i);
                    rData.add(map);
                }
            } else {
                Log.d("QT", "fail");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rData;
    }

    void loadData(boolean up) {
        SharedPreferences sp = getApplication().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String url = "http://" + sp.getString("ip", "") + ":" + sp.getString("duankou", "") + "/SmartCitySrv/news/list";

        new Thread(() -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("page", page);
                jsonObject.put("limit", limit);
                MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
                RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String doc = response.body().string();

                Message message = new Message();
                if (up)
                    message.what = 2;
                else
                    message.what = 1;
                message.obj = doc;
                handler.sendMessage(message);

                page++;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
