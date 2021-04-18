package com.kisssum.smartcity.ui.guidepages;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentGuideMainBinding;
import com.kisssum.smartcity.tool.API;
import com.kisssum.smartcity.tool.DecodeJson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuideMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuideMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentGuideMainBinding binding;
    private Handler handler;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> imgUrls;

    public GuideMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuideMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuideMainFragment newInstance(String param1, String param2) {
        GuideMainFragment fragment = new GuideMainFragment();
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
        binding = FragmentGuideMainBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean isFirstEnter = sp.getBoolean("isFirstEnter", true);

        // 判断是否是第一次进入
        if (isFirstEnter) {
            handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);

                    imgUrls = (ArrayList<String>) msg.obj;
                    initViewPager();
                }
            };

            loadRotationImgs();
        } else {
            NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_main);
            controller.popBackStack();
            controller.navigate(R.id.navControlFragment);
        }
    }

    private void loadRotationImgs() {
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(API.INSTANCE.getGuideRotationListUrl(requireContext()))
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String string = response.body().string();
                ArrayList<String> imgs = DecodeJson.INSTANCE.decodeGuideRotationList(string);

                Message message = new Message();
                message.obj = imgs;
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initViewPager() {
        FragmentStateAdapter adapter = new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return new GuidePagesFragment(position, imgUrls.get(position));
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        };

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.guideCard0.setCardBackgroundColor(Color.GRAY);
                binding.guideCard1.setCardBackgroundColor(Color.GRAY);
                binding.guideCard2.setCardBackgroundColor(Color.GRAY);
                binding.guideCard3.setCardBackgroundColor(Color.GRAY);
                binding.guideCard4.setCardBackgroundColor(Color.GRAY);

                if (position == 0) binding.guideCard0.setCardBackgroundColor(Color.WHITE);
                else if (position == 1) binding.guideCard1.setCardBackgroundColor(Color.WHITE);
                else if (position == 2) binding.guideCard2.setCardBackgroundColor(Color.WHITE);
                else if (position == 3) binding.guideCard3.setCardBackgroundColor(Color.WHITE);
                else if (position == 4) binding.guideCard4.setCardBackgroundColor(Color.WHITE);
            }
        });

        binding.guideCard0.setOnClickListener(view1 -> binding.viewPager.setCurrentItem(0));
        binding.guideCard1.setOnClickListener(view1 -> binding.viewPager.setCurrentItem(1));
        binding.guideCard2.setOnClickListener(view1 -> binding.viewPager.setCurrentItem(2));
        binding.guideCard3.setOnClickListener(view1 -> binding.viewPager.setCurrentItem(3));
        binding.guideCard4.setOnClickListener(view1 -> binding.viewPager.setCurrentItem(4));
    }

    @Override
    public void onResume() {
        super.onResume();

        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}