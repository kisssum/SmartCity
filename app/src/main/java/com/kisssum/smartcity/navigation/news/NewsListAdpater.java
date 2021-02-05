package com.kisssum.smartcity.navigation.news;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.kisssum.smartcity.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NewsListAdpater extends RecyclerView.Adapter<NewsListAdpater.DefaultViewModel> {
    private int type = 0;
    private Context context;
    private int count = 0;
    private List<JSONObject> data;

    public NewsListAdpater(int type, Context context, int count, List<JSONObject> data) {
        this.type = type;
        this.count = count;
        this.context = context;
        this.data=data;
    }

    @NotNull
    @Override
    public DefaultViewModel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list2, parent, false);
        return new DefaultViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DefaultViewModel holder, int position) {
        JSONObject object =data.get(position);

        holder.img.setImageResource(R.drawable.top_view_pager_1);
        try {
            holder.title.setText(object.getString("title"));
            holder.text.setText(object.getString("abstract"));
//            holder.comment.setText(data.get(position).get("comment").toString());
//            holder.time.setText(data.get(position).get("time").toString());
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
            bundle.putString("text", data.get(i).getString("abstract"));
            bundle.putString("url", data.get(i).getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (count == 3) {
            Navigation.findNavController(((Activity) context), R.id.fragment_main).popBackStack();
            Navigation.findNavController(((Activity) context), R.id.fragment_main).navigate(R.id.newsDetailFragment, bundle);
        } else {
            Navigation.findNavController(((Activity) context), R.id.fragment_main).navigate(R.id.action_navControlFragment_to_newsDetailFragment, bundle);
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
}
