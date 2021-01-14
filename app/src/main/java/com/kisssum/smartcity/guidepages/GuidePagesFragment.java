package com.kisssum.smartcity.guidepages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentGuidePagesBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuidePagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuidePagesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentGuidePagesBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int index;

    public GuidePagesFragment(int index) {
        this.index = index;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuidePagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuidePagesFragment newInstance(String param1, String param2) {
        GuidePagesFragment fragment = new GuidePagesFragment(0);
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
        binding = FragmentGuidePagesBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ratingBar.setProgress(index + 1);

        if (index != 4) {
            binding.btnGo.setVisibility(View.INVISIBLE);
        } else {
            binding.btnGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 保存
                    SharedPreferences sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
                    sp.edit().putBoolean("GuidePage", true).commit();


                    Navigation.findNavController(requireActivity(), R.id.fragment_main).popBackStack();
                    Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.navControlFragment);
                }
            });
        }
    }
}