package com.kisssum.smartcity.navigation.smartparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentPartyLunBoViewPagerBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartyLunBoViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartyLunBoViewPagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentPartyLunBoViewPagerBinding binding;
    private int index;

    public PartyLunBoViewPagerFragment(int index) {
        this.index = index;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartyLunBoViewPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartyLunBoViewPagerFragment newInstance(String param1, String param2) {
        PartyLunBoViewPagerFragment fragment = new PartyLunBoViewPagerFragment(0);
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
        binding = FragmentPartyLunBoViewPagerBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.party1);
        imgs.add(R.drawable.party2);
        imgs.add(R.drawable.party3);
        imgs.add(R.drawable.party4);
        imgs.add(R.drawable.party5);

        binding.partyLunBoImg.setImageResource(imgs.get(index));
    }
}