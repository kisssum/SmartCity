package com.kisssum.smartcity.navigation.me;

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
import com.kisssum.smartcity.databinding.FragmentLoginBinding;

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
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentLoginBinding binding;
    private Handler handler;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        binding = FragmentLoginBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (msg.what == 0)
                    saveData();
                else
                    Toast.makeText(requireContext(), "账号或密码错误", Toast.LENGTH_LONG).show();
            }
        };

        binding.logonGo.setOnClickListener(v -> {
            String name = binding.loginName.getText().toString();
            String pwd = binding.loginPwd.getText().toString();

            if (name.equals("") || pwd.equals("")) {
                Toast.makeText(requireContext(), "账号或密码不能为空", Toast.LENGTH_LONG).show();
            } else {
                haveId(name);
            }
        });
    }

    private void haveId(String id) {
        SharedPreferences sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String url = "http://" + sp.getString("ip", "") + ":" + sp.getString("duankou", "") + "/SmartCitySrv/user/user-info/";

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

                if (isUser(doc))
                    handler.sendEmptyMessage(0);
                else
                    handler.sendEmptyMessage(1);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private boolean isUser(String doc) {
        try {
            String object = new JSONObject(doc).getString("errMsg");
            return object.equals("ok");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveData() {
        SharedPreferences sp = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        sp.edit()
                .putString("id", binding.loginName.getText().toString())
                .putString("passwd", binding.loginPwd.getText().toString())
                .apply();

        NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_main);
        controller.popBackStack();
    }
}