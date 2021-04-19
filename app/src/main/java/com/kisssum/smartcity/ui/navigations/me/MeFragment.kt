package com.kisssum.smartcity.ui.navigations.me;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentMeBinding;

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
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMeBinding binding;
    private Handler handler;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        binding = FragmentMeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (msg.what == 0)
                    setInformation((String) msg.obj);
            }
        };

        resotre();
        navigationPager();
        backUser();
    }

    private void backUser() {
        binding.btnTuiChu.setOnClickListener(v -> {
            SharedPreferences sp = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
            sp.edit()
                    .putString("id", "")
                    .putString("name", "root")
                    .putBoolean("sex", false)
                    .putString("phone", "18757799489")
                    .putString("passwd", "")
                    .apply();

            binding.userName.setText(sp.getString("name", ""));
            Toast.makeText(requireContext(), "退出成功", Toast.LENGTH_SHORT).show();
        });
    }

    private void navigationPager() {
        NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_main);
        // 跳转到详细信息页
        binding.meTop.setOnClickListener(v -> controller.navigate(R.id.action_navControlFragment_to_meInformationFragment));
        binding.userName.setOnClickListener(v -> controller.navigate(R.id.action_navControlFragment_to_meInformationFragment));
        binding.l1.setOnClickListener(v -> controller.navigate(R.id.action_navControlFragment_to_meInformationFragment));
        // 跳转到账单页
        binding.l2.setOnClickListener(v -> controller.navigate(R.id.action_navControlFragment_to_orderListFragment));
        // 跳转到修改密码页
        binding.l3.setOnClickListener(v -> controller.navigate(R.id.action_navControlFragment_to_changePwdFragment));
        // 跳转到反馈页
        binding.l4.setOnClickListener(v -> controller.navigate(R.id.action_navControlFragment_to_opinionFragment));
    }

    private void resotre() {
        SharedPreferences spUser = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences spSetting = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        String id = spUser.getString("id", "");
        String url = "http://" + spSetting.getString("ip", "") + ":" + spSetting.getString("duankou", "") + "/SmartCitySrv/user/user-info/";

        new Thread(() -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", id);
                MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
                RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                String doc = response.body().string();

                Message message = new Message();
                message.what = 0;
                message.obj = doc;
                handler.sendMessage(message);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void setInformation(String doc) {
        try {
            String object = new JSONObject(doc).getString("errMsg");
            if (object.equals("ok")) {
                JSONObject information = new JSONObject(doc).getJSONObject("data");
                binding.userName.setText(information.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}