package com.kisssum.smartcity.navigation.home;

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
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentHomeBinding;
import com.kisssum.smartcity.navigation.news.NewsListAdpater;
import com.kisssum.smartcity.navigation.news.NewsModel;
import com.kisssum.smartcity.navigation.news.NewsPagerFragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentHomeBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    private Handler handler;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                JSONObject newsInfo = (JSONObject) msg.obj;

                if (msg.what == 1) {
                    navNewsInformation(newsInfo);
                }
            }
        };

        // 搜索框
        initSearch();

        // 轮播图
        initLunbotu();

        // 服务列表
        initServiceList();

        // 热门主题
        initHotTheme();

        // 新闻专栏
        initNews();
    }

    private void initSearch() {
        binding.homeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findNews(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void navNewsInformation(JSONObject news) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("title", news.getString("title"));
            bundle.putString("url", news.getString("url"));
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_navControlFragment_to_newsDetailFragment, bundle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void findNews(String id) {
        SharedPreferences sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String url = "http://" + sp.getString("ip", "") + ":" + sp.getString("duankou", "") + "/SmartCitySrv/news/news-info/";

        new Thread(() -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("newsId", id);
                MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
                RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String doc = response.body().string();
                JSONObject newsInfo = getNewsInfo(doc);

                Message message = new Message();
                message.what = 1;
                message.obj = newsInfo;
                handler.sendMessage(message);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private JSONObject getNewsInfo(String doc) {
        JSONObject rData = new JSONObject();

        try {
            JSONObject main = new JSONObject(doc);
            String errMsg = main.getString("errMsg");

            if (errMsg.equals("ok")) {
                rData = main.getJSONObject("data");
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }

        return rData;
    }

    private void initNews() {
        FragmentStateAdapter newsAdapter = new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return new NewsPagerFragment(position);
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        };

        binding.homeNewsPager.setAdapter(newsAdapter);
        binding.homeNewsPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        new TabLayoutMediator(binding.homeNewsTablayout, binding.homeNewsPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("国内");
                    break;
                case 1:
                    tab.setText("国际");
                    break;
                case 2:
                    tab.setText("军事");
                    break;
                case 3:
                    tab.setText("财经");
                    break;
                case 4:
                    tab.setText("娱乐");
                    break;
            }
        }).attach();
    }

    private void initLunbotu() {
        FragmentStateAdapter topViewAdapter = new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return new HomeTopViewPagerFragment(position);
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        };
        binding.homeTopViewPager.setAdapter(topViewAdapter);
        binding.homeTopViewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        // 无限滚轮
        loopTopViewPager();
    }

    private void initServiceList() {
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);

        binding.homeServiceList.serviceGovernmentAffairs.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.item_allservice);
        });

        binding.homeServiceList.serviceEnvironmrnalProtection.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.item_allservice);
        });

        binding.homeServiceList.serviceSecurity.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.item_allservice);
        });

        binding.homeServiceList.serviceTraffic.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.item_allservice);
        });

        binding.homeServiceList.servicePension.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.item_allservice);
        });

        binding.homeServiceList.serviceEducation.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.item_allservice);
        });

        binding.homeServiceList.serviceMedicalTreatment.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_main);
            controller.navigate(R.id.action_navControlFragment_to_medicalFragment);
        });

        binding.homeServiceList.serviceLife.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.item_allservice);
        });

        binding.homeServiceList.serviceTourism.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.item_allservice);
        });

        binding.homeServiceList.serviceMore.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.item_allservice);
        });
    }

    private void initHotTheme() {
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        HotServiceListAdpater HotServiceAdpater = new HotServiceListAdpater(requireContext());
        binding.homeHotServiceList.setLayoutManager(layoutManager);
        binding.homeHotServiceList.setAdapter(HotServiceAdpater);
    }

    private void loopTopViewPager() {
        new Handler().postDelayed(() -> {
            int cIndex = binding.homeTopViewPager.getCurrentItem();
            if (cIndex >= 4) cIndex = 0;
            else cIndex++;

            binding.homeTopViewPager.setCurrentItem(cIndex);
            loopTopViewPager();
        }, 3000);
    }
}