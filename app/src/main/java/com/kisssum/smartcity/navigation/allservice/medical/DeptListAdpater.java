package com.kisssum.smartcity.navigation.allservice.medical;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class DeptListAdpater extends RecyclerView.Adapter<DeptListAdpater.DefaultViewModel> {
    private List<JSONObject> data = new ArrayList<>();
    private Context context;
    private Handler handler;
    private int page = 1;
    private int[] imgs = new int[]{
            R.drawable.top_view_pager_1,
            R.drawable.top_view_pager_2,
            R.drawable.top_view_pager_3,
            R.drawable.top_view_pager_4,
            R.drawable.top_view_pager_5,
            R.drawable.top_view_pager_5,
    };
    private static final Random random = new Random(2);

    public DeptListAdpater(Context context) {
        this.context = context;

        loadData(false);

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
    }

    @NonNull
    @NotNull
    @Override
    public DefaultViewModel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dept_list, parent, false);
        return new DefaultViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DefaultViewModel holder, int position) {
        JSONObject object = data.get(position);
        try {
            holder.hName.setText(object.getString("deptName"));
            holder.hText.setText(object.getString("desc"));

            int type = random.nextInt(context.getResources().getStringArray(R.array.title).length);
            holder.hImg.setImageResource(imgs[type]);

            holder.hTag.setText(object.getString("tag"));
            holder.hlID.setText(object.getString("deptlId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class DefaultViewModel extends RecyclerView.ViewHolder {
        TextView hName, hText, hTag, hlID;
        ImageView hImg;

        public DefaultViewModel(@NonNull View itemView) {
            super(itemView);

            hName = itemView.findViewById(R.id.deptName);
            hText = itemView.findViewById(R.id.deptText);
            hImg = itemView.findViewById(R.id.deptPic);
            hTag = itemView.findViewById(R.id.deptTag);
            hlID = itemView.findViewById(R.id.deptlID);
        }
    }

    private List<JSONObject> getData(String doc) {
        List<JSONObject> list = new ArrayList<>();

        try {
            JSONObject main = new JSONObject(doc);

            if (main.getString("errMsg").equals("ok")) {
                JSONArray alist = main.getJSONObject("data").getJSONArray("list");
                for (int i = 0; i < alist.length(); i++) {
                    list.add(alist.getJSONObject(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void loadData(boolean up) {
        SharedPreferences sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String url = "http://" + sp.getString("ip", "") + ":" + sp.getString("duankou", "") + "/SmartCitySrv/hospital/deptList/";

        new Thread(() -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("page", page);
                jsonObject.put("limit", 10);
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
                message.obj = doc;
                if (up) message.what = 2;
                else message.what = 1;
                handler.sendMessage(message);

                page++;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
