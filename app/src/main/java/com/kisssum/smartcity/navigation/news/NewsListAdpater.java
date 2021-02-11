package com.kisssum.smartcity.navigation.news;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.kisssum.smartcity.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewsListAdpater extends RecyclerView.Adapter<NewsListAdpater.DefaultViewModel> {
    private int type = 0;
    private Context context;
    private int count = 0;
    private List<JSONObject> data = new ArrayList<>();
    private static final Random random = new Random(2);
    private int page = 1;
    private Handler handler;
    private int[] imgs = new int[]{
            R.drawable.top_view_pager_1,
            R.drawable.top_view_pager_2,
            R.drawable.top_view_pager_3,
            R.drawable.top_view_pager_4,
            R.drawable.top_view_pager_5,
            R.drawable.top_view_pager_5,
    };

    public NewsListAdpater(int type, Context context, int count) {
        this.type = type;
        this.count = count;
        this.context = context;
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                String doc = (String) msg.obj;

                if (msg.what == 1) data = getData(doc);
                else if (msg.what == 2) data.addAll(getData(doc));
                notifyDataSetChanged();
            }
        };

        loadData(false);
    }

    @NotNull
    @Override
    public DefaultViewModel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list2, parent, false);
        return new DefaultViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewModel holder, int position) {
        JSONObject object = data.get(position);

        try {
            holder.img.setImageResource(imgs[random.nextInt(5)]);
            holder.title.setText(object.getString("title"));
            holder.text.setText(object.getString("abstract"));
            holder.comment.setText("100");
            holder.time.setText("2020年10月07日 14:21");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(v -> {
            navNewsInformation(position);
        });
    }

    private void navNewsInformation(int i) {
        Bundle bundle = new Bundle();
        try {
            bundle.putString("title", data.get(i).getString("title"));
            bundle.putString("url", data.get(i).getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NavController controller = Navigation.findNavController(((Activity) context), R.id.fragment_main);
        if (count == 3) {
            controller.popBackStack();
            controller.navigate(R.id.newsDetailFragment, bundle);
        } else {
            controller.navigate(R.id.action_navControlFragment_to_newsDetailFragment, bundle);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class DefaultViewModel extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, text, comment, time;

        public DefaultViewModel(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.newsImg);
            title = itemView.findViewById(R.id.newsTitle);
            text = itemView.findViewById(R.id.newsText);
            comment = itemView.findViewById(R.id.newsComment);
            time = itemView.findViewById(R.id.newsTime);
        }
    }

    void loadData(boolean up) {
        SharedPreferences sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String url = "http://" + sp.getString("ip", "") + ":" + sp.getString("duankou", "") + "/SmartCitySrv/news/list";

        new Thread(() -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("page", page);
                jsonObject.put("limit", "10");
                jsonObject.put("sort", "desc");
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

    private List<JSONObject> getData(String doc) {
        List<JSONObject> rData = new ArrayList<>();

        try {
            JSONObject main = new JSONObject(doc);
            String errMsg = main.getString("errMsg");

            if (errMsg.equals("ok")) {
                JSONArray list = main.getJSONObject("data").getJSONArray("list");

                for (int i = 0; i < (count == 3 ? 3 : list.length()); i++) {
                    JSONObject map = list.getJSONObject(i);
                    rData.add(map);
                }
            } else {
                Log.d("QT", "fail");
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }

        return rData;
    }
}
