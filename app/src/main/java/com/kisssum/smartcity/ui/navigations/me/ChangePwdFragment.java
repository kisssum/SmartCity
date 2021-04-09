package com.kisssum.smartcity.ui.navigations.me;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentChangePwdBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePwdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePwdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentChangePwdBinding binding;

    public ChangePwdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePwdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePwdFragment newInstance(String param1, String param2) {
        ChangePwdFragment fragment = new ChangePwdFragment();
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
        binding = FragmentChangePwdBinding.inflate(inflater);
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

        binding.changePwdToolbar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
        });

        binding.btnOk.setOnClickListener(v -> {
            SharedPreferences sp = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE);

            //  都为空
            if (binding.beforePwd.getText().toString().equals("")
                    && binding.newPwd.getText().toString().equals("")) {
                Toast.makeText(requireContext(), "无修改", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
            }

            //  其一为空
            if (binding.beforePwd.getText().toString().equals("")
                    || binding.newPwd.getText().toString().equals("")) {
                Toast.makeText(requireContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            }

            // 输入原密码与新密码相同
            if (binding.beforePwd.getText().toString().equals(binding.newPwd.getText().toString())) {
                Toast.makeText(requireContext(), "密码不能相同", Toast.LENGTH_SHORT).show();
            } else {
                // 原密码与保存密码相同
                if (sp.getString("passwd", "").equals(binding.beforePwd.getText().toString())) {
                    sp.edit().putString("passwd", binding.newPwd.getText().toString()).apply();
                    Toast.makeText(requireContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
                } else {
                    Toast.makeText(requireContext(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}