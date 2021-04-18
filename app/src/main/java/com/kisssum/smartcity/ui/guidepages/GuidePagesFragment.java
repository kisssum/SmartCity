package com.kisssum.smartcity.ui.guidepages;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentGuidePagesBinding;
import com.kisssum.smartcity.tool.API;
import com.kisssum.smartcity.tool.DecodeJson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuidePagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuidePagesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentGuidePagesBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int index;
    private String url;
    private Handler handler;

    public GuidePagesFragment(int index, String url) {
        this.index = index;
        this.url = url;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuidePagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuidePagesFragment newInstance(String param1, String param2) {
        GuidePagesFragment fragment = new GuidePagesFragment(0, "");
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
        binding = FragmentGuidePagesBinding.inflate(inflater);
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
                binding.imageView.setImageBitmap(steam);
            }
        };

        getImg();
        if (index == 4) initFeatures();
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

    private void initFeatures() {
        binding.btnGo.setVisibility(View.VISIBLE);
        binding.btnNetwork.setVisibility(View.VISIBLE);

        SharedPreferences sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        binding.btnGo.setOnClickListener(v -> {
            // 保存
            sp.edit().putBoolean("isFirstEnter", false).apply();

            NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_main);
            controller.popBackStack();
            controller.navigate(R.id.navControlFragment);
        });

        binding.btnNetwork.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.alertdialog_change_ip, null);
            EditText ip = view1.findViewById(R.id.ip);
            EditText duankou = view1.findViewById(R.id.duankou);
            ip.setText(sp.getString("ip", ""));
            duankou.setText(sp.getString("duankou", ""));

            new AlertDialog.Builder(requireContext())
                    .setTitle("网络设置")
                    .setView(view1)
                    .setPositiveButton("保存", (dialog, which) -> {
                        sp.edit()
                                .putString("ip", ip.getText().toString())
                                .putString("duankou", duankou.getText().toString())
                                .apply();
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                    })
                    .create()
                    .show();
        });
    }
}