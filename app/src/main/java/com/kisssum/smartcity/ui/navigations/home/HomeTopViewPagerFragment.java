package com.kisssum.smartcity.ui.navigations.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentHomeTopViewPagerBinding;
import com.kisssum.smartcity.tool.API;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeTopViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeTopViewPagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentHomeTopViewPagerBinding binding;
    private int index = 0;
    private String url;
    private Handler handler;

    public HomeTopViewPagerFragment(int index, String url) {
        this.index = index;
        this.url = url;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeTopViewPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeTopViewPagerFragment newInstance(String param1, String param2) {
        HomeTopViewPagerFragment fragment = new HomeTopViewPagerFragment(0, "");
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
        binding = FragmentHomeTopViewPagerBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                Bitmap steam = (Bitmap) msg.obj;
                binding.imageView2.setImageBitmap(steam);
//                binding.imageView2.setOnClickListener(v -> navNewsInformation(index));
            }
        };

        getImg();
    }

    private void getImg() {
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(API.INSTANCE.getBaseUrl(requireContext()) + url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                InputStream stream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);

                Message message = new Message();
                message.obj = bitmap;
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void navNewsInformation(int i) {
        String[] title = getResources().getStringArray(R.array.title);
        String[] text = getResources().getStringArray(R.array.text);
        String[] url = getResources().getStringArray(R.array.url);

        Bundle bundle = new Bundle();
        bundle.putString("title", title[i]);
        bundle.putString("text", text[i]);
        bundle.putString("url", url[i]);

        Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_navControlFragment_to_newsDetailFragment, bundle);
    }
}