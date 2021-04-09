package com.kisssum.smartcity.adapter.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kisssum.smartcity.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotServiceListAdpater extends RecyclerView.Adapter<HotServiceListAdpater.DefaultViewMode> {
    List<Map<String, Object>> data;
    private Context context;

    public HotServiceListAdpater(Context context) {
        data = getData();
        this.context = context;
    }

    @NonNull
    @Override
    public DefaultViewMode onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_style_hot_service, parent, false);
        return new DefaultViewMode(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewMode holder, int position) {
        holder.img.setImageResource((Integer) data.get(position).get("img"));
        holder.name.setText(data.get(position).get("title").toString());

        holder.itemView.setOnClickListener(v -> {
            BottomNavigationView view = ((Activity) context).findViewById(R.id.bottomNavigationView);
            view.setSelectedItemId(R.id.item_allservice);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Map<String, Object>> getData() {
        ArrayList list = new ArrayList();
        Map<String, Object> map;
        int[] imgs = new int[]{
                R.drawable.top_view_pager_1,
                R.drawable.top_view_pager_2,
                R.drawable.top_view_pager_3,
                R.drawable.top_view_pager_4,
                R.drawable.top_view_pager_5,
                R.drawable.top_view_pager_1
        };

        for (int img : imgs) {
            map = new HashMap<>();
            map.put("img", img);
            map.put("title", "热门主题");
            list.add(map);
        }
        return list;
    }

    class DefaultViewMode extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;

        public DefaultViewMode(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.hotImg);
            name = itemView.findViewById(R.id.hotName);
        }
    }
}
