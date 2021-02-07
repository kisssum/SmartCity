package com.kisssum.smartcity.navigation.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisssum.smartcity.databinding.FragmentNewsPagerBinding;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsPagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentNewsPagerBinding binding;
    private int type;
    private NewsListAdpater adpater;

    public NewsPagerFragment(int type) {
        this.type = type;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsPagerFragment newInstance(String param1, String param2) {
        NewsPagerFragment fragment = new NewsPagerFragment(0);
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
        binding = FragmentNewsPagerBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        adpater.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        adpater = new NewsListAdpater(type, requireContext(), 10);
        binding.newsPagerList.setLayoutManager(layoutManager);
        binding.newsPagerList.setAdapter(adpater);

        binding.newsSmart.setOnLoadMoreListener(refreshLayout -> {
            adpater.loadData(true);
            refreshLayout.finishLoadMore(100);
        });
    }
}