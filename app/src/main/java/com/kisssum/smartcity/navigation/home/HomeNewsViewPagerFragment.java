package com.kisssum.smartcity.navigation.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisssum.smartcity.databinding.FragmentHomeNewsViewPagerBinding;
import com.kisssum.smartcity.navigation.news.NewsListAdpater;
import com.kisssum.smartcity.navigation.news.NewsModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeNewsViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeNewsViewPagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentHomeNewsViewPagerBinding binding;
    private int index = 0;

    public HomeNewsViewPagerFragment(int index) {
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
    public static HomeNewsViewPagerFragment newInstance(String param1, String param2) {
        HomeNewsViewPagerFragment fragment = new HomeNewsViewPagerFragment(0);
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

        NewsModel model = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(NewsModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        HomeNewsListAdpater adpater = new HomeNewsListAdpater(index, requireContext(), 10,model);
        binding.newsList.setLayoutManager(layoutManager);
        binding.newsList.setAdapter(adpater);
    }
}