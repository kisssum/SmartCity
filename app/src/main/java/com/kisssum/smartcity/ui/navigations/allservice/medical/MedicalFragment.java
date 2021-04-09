package com.kisssum.smartcity.ui.navigations.allservice.medical;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentMedicalBinding;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMedicalBinding binding;

    public MedicalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicalFragment newInstance(String param1, String param2) {
        MedicalFragment fragment = new MedicalFragment();
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
        binding = FragmentMedicalBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.medicalToolbar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
        });

        initPager();
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

    private void initPager() {
        FragmentStateAdapter pagerAdapter = new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position < 3)
                    return new MedicalPagerFragment(position);
                else
                    return new AppointmentFragment();
            }

            @Override
            public int getItemCount() {
                return 4;
            }
        };
        binding.medicalViewPager.setAdapter(pagerAdapter);
        binding.medicalViewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        new TabLayoutMediator(binding.medicalTablayout, binding.medicalViewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("医院查询");
                    break;
                case 1:
                    tab.setText("科室查询");
                    break;
                case 2:
                    tab.setText("医生查询");
                    break;
                case 3:
                    tab.setText("预约挂号");
                    break;
            }
        }).attach();
    }
}