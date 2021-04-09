package com.kisssum.smartcity.ui.navigations.me;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentOpinionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpinionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpinionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentOpinionBinding binding;

    public OpinionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpinionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OpinionFragment newInstance(String param1, String param2) {
        OpinionFragment fragment = new OpinionFragment();
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
        binding = FragmentOpinionBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        requireActivity().getWindow().setStatusBarColor(Color.RED);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        requireActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.opinionToolbar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
        });

        binding.btnSubmit.setOnClickListener(v -> {
            if (binding.opinionText.getText().toString().equals("")) {
                Toast.makeText(requireContext(), "内容不能为空", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "感谢您的反馈!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
            }
        });

        binding.opinionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.opinionCount.setText(String.valueOf(150 - s.length()));
            }
        });
    }
}