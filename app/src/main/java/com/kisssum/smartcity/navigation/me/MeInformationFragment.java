package com.kisssum.smartcity.navigation.me;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentMeInformationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMeInformationBinding binding;

    public MeInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeInformationFragment newInstance(String param1, String param2) {
        MeInformationFragment fragment = new MeInformationFragment();
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
        binding = FragmentMeInformationBinding.inflate(inflater);
        return binding.getRoot();
    }

    private void load() {
        SharedPreferences sp = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        binding.name.setText(sp.getString("name", "root"));
        binding.sex.setChecked(sp.getBoolean("sex", false));
        binding.phone.setText(sp.getString("phone", "18757799489"));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        load();

        binding.meInformationToolbar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
        });

        binding.meInformationToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.item_me_information_change) {
                SharedPreferences sp = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
                sp.edit().putString("name", binding.name.getText().toString())
                        .putBoolean("sex", binding.sex.isChecked())
                        .putString("phone", binding.phone.getText().toString())
                        .apply();

                Toast.makeText(requireContext(), "修改成功", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
            }
            return true;
        });

        binding.name.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.alertdialog_change_name, null);

            new AlertDialog.Builder(requireActivity())
                    .setTitle("修改昵称")
                    .setView(view1)
                    .setPositiveButton("确定", (dialog, which) -> {
                        EditText name = view1.findViewById(R.id.editext);
                        if (name.getText().toString().equals(""))
                            Toast.makeText(requireContext(), "昵称不能为空", Toast.LENGTH_SHORT).show();
                        else
                            binding.name.setText(name.getText().toString());
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                    })
                    .create()
                    .show();
        });

        binding.phone.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.alertdialog_change_phone, null);

            new AlertDialog.Builder(requireActivity())
                    .setTitle("修改电话")
                    .setView(view1)
                    .setPositiveButton("确定", (dialog, which) -> {
                        EditText name = view1.findViewById(R.id.editext);
                        if (name.getText().toString().equals(""))
                            Toast.makeText(requireContext(), "电话号码不能为空", Toast.LENGTH_SHORT).show();
                        else if (name.getText().toString().length() != 11)
                            Toast.makeText(requireContext(), "电话号码长度不正确", Toast.LENGTH_SHORT).show();
                        else
                            binding.phone.setText(name.getText().toString());
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                    })
                    .create()
                    .show();
        });
    }
}