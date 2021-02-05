package com.kisssum.smartcity.navigation.me;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.kisssum.smartcity.R;
import com.kisssum.smartcity.databinding.FragmentMeInformationBinding;

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
 * Use the {@link MeInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMeInformationBinding binding;
    private Handler handler;

    public MeInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeInformationFragment newInstance(String param1, String param2) {
        MeInformationFragment fragment = new MeInformationFragment();
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
        binding = FragmentMeInformationBinding.inflate(inflater);
        return binding.getRoot();
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
                message.what = RESTORE;
                message.obj = doc;
                handler.sendMessage(message);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private boolean isSave(String doc) {
        try {
            String object = new JSONObject(doc).getString("errMsg");
            return object.equals("ok");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setInformation(String doc) {
        try {
            String object = new JSONObject(doc).getString("errMsg");
            if (object.equals("ok")) {
                JSONObject information = new JSONObject(doc).getJSONObject("data");
                binding.name.setText(information.getString("name"));
                binding.sex.setChecked(information.getInt("gender") != 1);
                binding.phone.setText(information.getString("phone"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (msg.what == RESTORE)
                    setInformation((String) msg.obj);
                else if (msg.what == SAVE) {
                    if (isSave((String) msg.obj)) {
                        Toast.makeText(requireContext(), "保存成功", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
                    } else
                        Toast.makeText(requireContext(), "保存保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        };

        resotre();
        initBtn();
    }

    private void save() {
        SharedPreferences spUser = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences spSetting = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        String id = spUser.getString("id", "");
        String url = "http://" + spSetting.getString("ip", "") + ":" + spSetting.getString("duankou", "") + "/SmartCitySrv/user/save";

        new Thread(() -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("name", binding.name.getText());
                jsonObject.put("phone", binding.phone.getText());
                jsonObject.put("avatar", "666.jpg");
                jsonObject.put("gender", binding.sex.isChecked() ? 1 : 0);
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
                message.what = SAVE;
                message.obj = doc;
                handler.sendMessage(message);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private final int RESTORE = 1;
    private final int SAVE = 2;

    private void initBtn() {
        binding.meInformationToolbar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp();
        });

        binding.meInformationToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.item_me_information_change) save();
            return true;
        });

        binding.name.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.alertdialog_change_name, null);

            new AlertDialog.Builder(requireActivity())
                    .setTitle("修改昵称")
                    .setView(view1)
                    .setPositiveButton("确定", (dialog, which) -> {
                        EditText name = view1.findViewById(R.id.editext);
                        if (name.getText().toString().equals(""))
                            Toast.makeText(requireContext(), "昵称不能为空", Toast.LENGTH_SHORT).show();
                        else
                            binding.name.setText(name.getText().toString());
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                    })
                    .create()
                    .show();
        });

        binding.phone.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.alertdialog_change_phone, null);

            new AlertDialog.Builder(requireActivity())
                    .setTitle("修改电话")
                    .setView(view1)
                    .setPositiveButton("确定", (dialog, which) -> {
                        EditText name = view1.findViewById(R.id.editext);
                        if (name.getText().toString().equals(""))
                            Toast.makeText(requireContext(), "电话号码不能为空", Toast.LENGTH_SHORT).show();
                        else if (name.getText().toString().length() != 11)
                            Toast.makeText(requireContext(), "电话号码长度不正确", Toast.LENGTH_SHORT).show();
                        else
                            binding.phone.setText(name.getText().toString());
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                    })
                    .create()
                    .show();
        });
    }
}