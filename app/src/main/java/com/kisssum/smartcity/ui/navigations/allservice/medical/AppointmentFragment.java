package com.kisssum.smartcity.ui.navigations.allservice.medical;

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
import com.kisssum.smartcity.databinding.FragmentAppointmentBinding;

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
 * Use the {@link AppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentAppointmentBinding binding;
    private Handler handler;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
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
        binding = FragmentAppointmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                String doc = (String) msg.obj;

                if (msg.what == 1) {
                    JSONObject object = jsonPass(doc);
                    followMe(object);
                }
            }
        };

        binding.appGo.setOnClickListener(v -> {
            if (binding.atPid.getText().toString().equals("")
                    || binding.atName.getText().toString().equals("")
                    || binding.atPhone.getText().toString().equals("")
                    || binding.atTime.getText().toString().equals("")
                    || binding.atDoctorId.getText().toString().equals("")) {
                Toast.makeText(requireContext(), "数据不能为空!", Toast.LENGTH_SHORT).show();
            } else {
                loadData();
            }
        });
    }

    private JSONObject jsonPass(String doc) {
        try {
            JSONObject main = new JSONObject(doc);
            if (main.getString("errMsg").equals("ok")) return main.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    private void followMe(JSONObject object) {
        Bundle bundle = new Bundle();
        try {
            bundle.putString("appTime", object.getString("appTime"));
            bundle.putString("hospitalId", object.getString("hospitalId"));
            bundle.putString("doctorId", object.getString("doctorId"));
            bundle.putString("deptlId", object.getString("deptlId"));
            bundle.putString("tag", object.getString("tag"));
            bundle.putString("doctorname", object.getString("doctorname"));
            bundle.putString("desc", object.getString("desc"));

            NavController controller = Navigation.findNavController(requireActivity(), R.id.fragment_main);
            controller.navigate(R.id.action_medicalFragment_to_doctorDetailFragment, bundle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        SharedPreferences sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String url = "http://" + sp.getString("ip", "") + ":" + sp.getString("duankou", "") + "/SmartCitySrv/hospital/appointment/";

        new Thread(() -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pid", binding.atPid.getText().toString());
                jsonObject.put("name", binding.atName.getText().toString());
                jsonObject.put("phone", binding.atPhone.getText().toString());
                jsonObject.put("appTime", binding.atTime.getText().toString());
                jsonObject.put("doctorId", binding.atDoctorId.getText().toString());
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
                message.obj = doc;
                message.what = 1;
                handler.sendMessage(message);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }
}