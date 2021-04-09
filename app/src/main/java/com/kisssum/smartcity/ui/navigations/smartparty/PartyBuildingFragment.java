package com.kisssum.smartcity.ui.navigations.smartparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.kisssum.smartcity.databinding.FragmentPartyBuildingBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartyBuildingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartyBuildingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentPartyBuildingBinding binding;

    public PartyBuildingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartyBuildingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartyBuildingFragment newInstance(String param1, String param2) {
        PartyBuildingFragment fragment = new PartyBuildingFragment();
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
        binding = FragmentPartyBuildingBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 轮播图
        initLunbotu();

        // 动态
        initDongTai();
    }

    private void initDongTai() {
        FragmentStateAdapter newsAdapter = new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return new PartyBuildingDTViewPagerFragment(position);
            }

            @Override
            public int getItemCount() {
                return 6;
            }
        };

        binding.partyBuildingDongTaiViewPager.setAdapter(newsAdapter);
        binding.partyBuildingDongTaiViewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        new TabLayoutMediator(binding.partyBuildingDTlayout, binding.partyBuildingDongTaiViewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("社会");
                    break;
                case 1:
                    tab.setText("国内");
                    break;
                case 2:
                    tab.setText("国际");
                    break;
                case 3:
                    tab.setText("军事");
                    break;
                case 4:
                    tab.setText("财经");
                    break;
                case 5:
                    tab.setText("娱乐");
                    break;
            }
        }).attach();
    }

    private void initLunbotu() {
        FragmentStateAdapter adapter = new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return new PartyLunBoViewPagerFragment(position);
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        };
        binding.partyBuildingLunBoViewPager.setAdapter(adapter);
        binding.partyBuildingLunBoViewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        // 无限滚轮
        loopTopViewPager();
    }

    private void loopTopViewPager() {
        new Handler().postDelayed(() -> {
            int cIndex = binding.partyBuildingLunBoViewPager.getCurrentItem();
            if (cIndex >= 4) cIndex = 0;
            else cIndex++;

            binding.partyBuildingLunBoViewPager.setCurrentItem(cIndex);
            loopTopViewPager();
        }, 3000);
    }
}