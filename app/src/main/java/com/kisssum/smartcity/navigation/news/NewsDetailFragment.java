package com.kisssum.smartcity.navigation.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentNewsDetailBinding;
import com.kisssum.smartcity.navigation.home.HomeNewsListAdpater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentNewsDetailBinding binding;

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsDetailFragment newInstance(String param1, String param2) {
        NewsDetailFragment fragment = new NewsDetailFragment();
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
        binding = FragmentNewsDetailBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();

        // Toolbar
        binding.newsDetailToolbar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
        });
        binding.newsDetailToolbar.setTitle(arguments.getString("title"));

        // webView
        binding.newDetailWeb.getSettings().setJavaScriptEnabled(true);
        binding.newDetailWeb.getSettings().setBuiltInZoomControls(true);
        binding.newDetailWeb.setWebViewClient(new WebViewClient());
        binding.newDetailWeb.loadUrl(arguments.getString("url"));

        // list
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        HomeNewsListAdpater adpater = new HomeNewsListAdpater(0, requireContext(), 3);
        binding.newsDetailList.setLayoutManager(layoutManager);
        binding.newsDetailList.setAdapter(adpater);
    }
}