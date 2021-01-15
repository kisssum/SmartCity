package com.kisssum.smartcity.navigation.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kisssum.smartcity.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeNewsListAdpater extends RecyclerView.Adapter<HomeNewsListAdpater.DefaultViewModel> {
    List<Map<String, Object>> data;
    private int index = 0;

    public HomeNewsListAdpater(int index) {
        this.index = index;
        data = getData();
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

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;

        for (int i = 0; i < 10; i++) {
            map = new HashMap<>();
            switch (index) {
                case 0:
                    map.put("img", R.drawable.top_view_pager_1);
                    map.put("title", "“特权病毒”需要彻底根治");
                    map.put("text", "1月13日，网传大连市金普新区某街道办副主任王某在进入小区时，不服从防疫规定，拒不登记身份证号，还找小区所在社区书记“协调”。");
                    map.put("comment", "100评");
                    map.put("time", "2021年01月15日 16:26");
                    break;
                case 1:
                    map.put("img", R.drawable.top_view_pager_2);
                    map.put("title", "“纸牌屋”坍塌，美国政治乱象令世人瞠目");
                    map.put("text", "2021年，美国以让世人瞠目的政治闹剧开场，接下来，总统特朗普为数不多的几天任期，也将以更多纷争收场。");
                    map.put("comment", "10000评");
                    map.put("time", "2021年01月15日 12:40");
                    break;
                case 2:
                    map.put("img", R.drawable.top_view_pager_3);
                    map.put("title", "我们需要怎样的“训练指挥棒");
                    map.put("text", "北部战区陆军某旅一营二连班长赵志从未想过，自己有一天会在伪装防护这个课目上失手。");
                    map.put("comment", "1000评");
                    map.put("time", "2021年1月14日");
                    break;
                case 3:
                    map.put("img", R.drawable.top_view_pager_4);
                    map.put("title", "法《世界报》：中国，世界经济增长的火车头");
                    map.put("text", "大流行？哪个大流行？如果2021年如经济学家预测的那样发展的话，中国将会毫无阻碍地跨过危机。中国的国内生产总值（GDP）到年底应会达到西方的经济趋势分析专家们曾在2019年底时预测的水平。");
                    map.put("comment", "211评");
                    map.put("time", "2021年1月13日");
                    break;
                case 4:
                    map.put("img", R.drawable.top_view_pager_5);
                    map.put("title", "2021爱奇艺为爱尖叫晚会”今晚来了");
                    map.put("text", "今天（1月15日）晚上，“全球首台多画面互动直播超级晚”——2021爱奇艺为爱尖叫晚会即将开播。");
                    map.put("comment", "431评");
                    map.put("time", "2021年1月13日");
                    break;
            }
            list.add(map);
        }

        return list;
    }
}
