package com.kisssum.smartcity.adapter.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.state.NewsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class HomeNewsListAdpater extends RecyclerView.Adapter<HomeNewsListAdpater.DefaultViewModel> {
    private List<Map<String, Object>> data;
    private int index = 0;
    private Context context;
    private int count = 0;

    public HomeNewsListAdpater(int index, Context context, int count, NewsModel model) {
        this.index = index;
        this.count = count;
        this.context = context;

        this.data = getData();
    }

    @NonNull
    @Override
    public DefaultViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list2, parent, false);
        return new DefaultViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewModel holder, int position) {
        holder.img.setImageResource((Integer) data.get(position).get("img"));
        holder.title.setText(data.get(position).get("title").toString());
        holder.text.setText(data.get(position).get("text").toString());
        holder.comment.setText(data.get(position).get("comment").toString());
        holder.time.setText(data.get(position).get("time").toString());

        holder.itemView.setOnClickListener(v -> {
            navNewsInformation(position);
        });
    }

    private void navNewsInformation(int i) {
        Bundle bundle = new Bundle();
        bundle.putString("title", data.get(i).get("title").toString());
        bundle.putString("text", data.get(i).get("text").toString());
        bundle.putString("url", data.get(i).get("url").toString());

        if (count == 3) {
            Navigation.findNavController(((Activity) context), R.id.fragment_main).popBackStack();
            Navigation.findNavController(((Activity) context), R.id.fragment_main).navigate(R.id.newsDetailFragment, bundle);
        } else {
            Navigation.findNavController(((Activity) context), R.id.fragment_main).navigate(R.id.action_navControlFragment_to_newsDetailFragment, bundle);
        }
    }

    public void isAndGoToNewsInformation(String title) {
        String p = ".*" + title + ".*";

        for (int i = 0; i < data.size(); i++) {
            String map = data.get(i).get("title").toString();
            boolean is = Pattern.matches(p, map);

            if (is) {
                navNewsInformation(i);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DefaultViewModel extends RecyclerView.ViewHolder {
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

    private int[] imgs = new int[]{
            R.drawable.top_view_pager_1,
            R.drawable.top_view_pager_2,
            R.drawable.top_view_pager_3,
            R.drawable.top_view_pager_4,
            R.drawable.top_view_pager_5,
            R.drawable.top_view_pager_5,
    };

    private static final Random random = new Random(2);

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;

        for (int i = 0; i < count; i++) {
            map = new HashMap<>();
            int type = random.nextInt(context.getResources().getStringArray(R.array.title).length);
            map.put("img", imgs[type]);
            map.put("title", context.getResources().getStringArray(R.array.title)[type]);
            map.put("text", context.getResources().getStringArray(R.array.text)[type]);
            map.put("url", context.getResources().getStringArray(R.array.url)[type]);
            map.put("comment", context.getResources().getStringArray(R.array.comment)[type]);
            map.put("time", context.getResources().getStringArray(R.array.time)[type]);
            list.add(map);
        }

        return list;
    }
}
