package com.kisssum.smartcity.guidepages;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentGuideMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuideMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuideMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentGuideMainBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuideMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuideMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuideMainFragment newInstance(String param1, String param2) {
        GuideMainFragment fragment = new GuideMainFragment();
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
        binding = FragmentGuideMainBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 判断是否是第一次进入
        SharedPreferences sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        boolean guidePage = sp.getBoolean("GuidePage", false);

        if (guidePage) {
            NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_main);
            controller.popBackStack();
            controller.navigate(R.id.navControlFragment);
        } else {
            FragmentStateAdapter adapter = new FragmentStateAdapter(requireActivity()) {
                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    return new GuidePagesFragment(position);
                }

                @Override
                public int getItemCount() {
                    return 5;
                }
            };

            binding.viewPager.setAdapter(adapter);
            binding.viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (isDarkTheme(requireContext())) {
            requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        } else {
            requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private boolean isDarkTheme(Context context) {
        int flag = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return flag == Configuration.UI_MODE_NIGHT_YES;
    }
}