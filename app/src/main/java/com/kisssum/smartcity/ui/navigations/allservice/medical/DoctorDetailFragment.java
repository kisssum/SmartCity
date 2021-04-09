package com.kisssum.smartcity.ui.navigations.allservice.medical;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentDoctorDetailBinding;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentDoctorDetailBinding binding;

    public DoctorDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorDetailFragment newInstance(String param1, String param2) {
        DoctorDetailFragment fragment = new DoctorDetailFragment();
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
        binding = FragmentDoctorDetailBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();

        binding.ddName.setText("医生姓名:" + arguments.getString("doctorname"));
        binding.ddHospitalId.setText("医院ID:" + arguments.getString("hospitalId"));
        binding.ddDoctorId.setText("医生ID:" + arguments.getString("doctorId"));
        binding.ddDeptlId.setText("科室ID:" + arguments.getString("deptlId"));
        binding.ddTag.setText("医生专长:" + arguments.getString("tag"));
        binding.ddDesc.setText("医生简介:" + arguments.getString("desc"));
        binding.ddAppTime.setText("预约时间:" + arguments.getString("appTime"));

        binding.ddToolbar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
        });
    }
}