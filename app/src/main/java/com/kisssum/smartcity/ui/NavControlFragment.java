package com.kisssum.smartcity.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentNavControlBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavControlFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentNavControlBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavControlFragment newInstance(String param1, String param2) {
        NavControlFragment fragment = new NavControlFragment();
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
        binding = FragmentNavControlBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_detail);

            switch (item.getItemId()) {
                case R.id.item_home:
                    controller.popBackStack();
                    controller.navigate(R.id.homeFragment);
                    break;
                case R.id.item_allservice:
                    controller.popBackStack();
                    controller.navigate(R.id.allServiceFragment);
                    break;
                case R.id.item_partybuidling:
                    controller.popBackStack();
                    controller.navigate(R.id.partyBuildingFragment);
                    break;
                case R.id.item_news:
                    controller.popBackStack();
                    controller.navigate(R.id.newsFragment);
                    break;
                case R.id.item_me:
                    SharedPreferences sp = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE);

                    if (sp.getString("id", "").equals("")) {
                        NavController controllerMain = Navigation.findNavController(requireActivity(), R.id.fragment_main);
                        controllerMain.navigate(R.id.action_navControlFragment_to_loginFragment);
                    } else {
                        controller.popBackStack();
                        controller.navigate(R.id.meFragment);
                    }
                    break;
            }
            return true;
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        requireActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}