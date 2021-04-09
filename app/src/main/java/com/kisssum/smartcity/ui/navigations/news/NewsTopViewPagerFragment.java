package com.kisssum.smartcity.ui.navigations.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentNewsTopViewPagerBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsTopViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsTopViewPagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private JSONObject index;
    private FragmentNewsTopViewPagerBinding binding;
    private static final Random random = new Random(2);

    public NewsTopViewPagerFragment(JSONObject index) {
        this.index = index;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsTopViewPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsTopViewPagerFragment newInstance(String param1, String param2) {
        NewsTopViewPagerFragment fragment = new NewsTopViewPagerFragment(new JSONObject());
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
        binding = FragmentNewsTopViewPagerBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.top_view_pager_1);
        imgs.add(R.drawable.top_view_pager_2);
        imgs.add(R.drawable.top_view_pager_3);
        imgs.add(R.drawable.top_view_pager_4);
        imgs.add(R.drawable.top_view_pager_5);

        try {
            binding.imageView4.setImageResource(imgs.get(random.nextInt(5)));
            binding.textView7.setText(index.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding.cardView4.setOnClickListener(v -> {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("title", index.getString("title"));
                bundle.putString("url", index.getString("url"));
                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_navControlFragment_to_newsDetailFragment, bundle);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}