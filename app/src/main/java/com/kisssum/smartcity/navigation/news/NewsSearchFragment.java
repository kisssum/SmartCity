package com.kisssum.smartcity.navigation.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentNewsSearchBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentNewsSearchBinding binding;
    private Handler handler;

    public NewsSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsSearchFragment newInstance(String param1, String param2) {
        NewsSearchFragment fragment = new NewsSearchFragment();
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

        binding = FragmentNewsSearchBinding.inflate(inflater);
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
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                JSONObject newsInfo = (JSONObject) msg.obj;

                if (msg.what == 1) {
                    navNewsInformation(newsInfo);
                }
            }
        };

        binding.newsSearchToolbar.setNavigationOnClickListener(v -> {
            NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_main);
            controller.popBackStack();
        });

        binding.newsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findNews(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void navNewsInformation(JSONObject news) {
        Bundle bundle = new Bundle();

        try {
            bundle.putString("title", news.getString("title"));
            bundle.putString("url", news.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_main);
        controller.navigate(R.id.action_newsSearchFragment_to_newsDetailFragment, bundle);
    }

    private void findNews(String id) {
        SharedPreferences sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String url = "http://" + sp.getString("ip", "") + ":" + sp.getString("duankou", "") + "/SmartCitySrv/news/news-info/";

        new Thread(() -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("newsId", id);
                MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
                RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String doc = response.body().string();
                JSONObject newsInfo = getNewsInfo(doc);

                Message message = new Message();
                message.what = 1;
                message.obj = newsInfo;
                handler.sendMessage(message);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private JSONObject getNewsInfo(String doc) {
        JSONObject rData = new JSONObject();

        try {
            JSONObject main = new JSONObject(doc);
            String errMsg = main.getString("errMsg");

            if (errMsg.equals("ok")) {
                rData = main.getJSONObject("data");
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }

        return rData;
    }

}