package com.kisssum.smartcity.navigation.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;
import com.kisssum.smartcity.databinding.FragmentNewsBinding;
import com.kisssum.smartcity.navigation.home.HomeNewsViewPagerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentNewsBinding binding;
    private NewsModel model;

    private final Handler handler = new Handler();

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        binding = FragmentNewsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(NewsModel.class);

        // 轮播图
        initLunbotu();

        // 新闻专栏
        initNews();
    }

    private void initLunbotu() {
        FragmentStateAdapter topViewAdapter = new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return new NewsTopViewPagerFragment(model.getData().get(position));
            }

            @Override
            public int getItemCount() {
                return model.getCount();
            }
        };
        binding.newsMainLunBoPager.setAdapter(topViewAdapter);
        binding.newsMainLunBoPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        // 无限滚轮
        loopTopViewPager();
    }

    private void loopTopViewPager() {
        handler.postDelayed(() -> {
            int cIndex = binding.newsMainLunBoPager.getCurrentItem();

            if (cIndex >= model.getCount() - 1) cIndex = 0;
            else cIndex++;

            binding.newsMainLunBoPager.setCurrentItem(cIndex);
            loopTopViewPager();
        }, 3000);
    }

    private void initNews() {
        FragmentStateAdapter pagerAdapter = new FragmentStateAdapter(requireActivity()) {
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
        binding.newsMainPager.setAdapter(pagerAdapter);
        binding.newsMainPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        new TabLayoutMediator(binding.newsMainTablayout, binding.newsMainPager, (tab, position) -> {
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
}