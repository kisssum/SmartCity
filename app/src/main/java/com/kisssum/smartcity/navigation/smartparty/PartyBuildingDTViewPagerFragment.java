package com.kisssum.smartcity.navigation.smartparty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kisssum.smartcity.databinding.FragmentHomeNewsViewPagerBinding;
import com.kisssum.smartcity.navigation.news.NewsListAdpater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartyBuildingDTViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartyBuildingDTViewPagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentHomeNewsViewPagerBinding binding;
    private int index = 0;

    public PartyBuildingDTViewPagerFragment(int index) {
        this.index = index;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeNewsViewPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartyBuildingDTViewPagerFragment newInstance(String param1, String param2) {
        PartyBuildingDTViewPagerFragment fragment = new PartyBuildingDTViewPagerFragment(0);
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
        binding = FragmentHomeNewsViewPagerBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NewsListAdpater adpater = new NewsListAdpater(0, requireContext(), 10);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.newsList.setLayoutManager(layoutManager);
        binding.newsList.setAdapter(adpater);
    }
}