package com.kisssum.smartcity.ui.navigations.me

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentChangePwdBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.UpdateUI
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [ChangePwdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangePwdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var binding: FragmentChangePwdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentChangePwdBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = Color.RED
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().window.statusBarColor = Color.TRANSPARENT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOk.setOnClickListener {
            UpdateUI.toastUi(requireContext(),"接口未提供")
        }
//        val hander = object : Handler() {
//            override fun handleMessage(msg: Message) {
//                super.handleMessage(msg)
//
//                val s = msg.obj as String
//
//                val jsonObject = JSONObject(s)
//
//                if (jsonObject.getInt("code") == 200) {
//                    Toast.makeText(requireContext(), "密码修改成功", Toast.LENGTH_SHORT).show()
//                    requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE).edit().putString("password", binding.newPwd.text.toString())
//                } else {
//                    Toast.makeText(requireContext(), "密码修改失败", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        binding.changePwdToolbar.setNavigationOnClickListener { v: View? -> Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp() }
//        binding.btnOk.setOnClickListener { v: View? ->
//            run {
//                Thread {
//                    OkHttpClient().apply {
//                        val jsonObject = JSONObject().apply {
//                            this.put("userId", API.getUserId(requireContext()).toString())
//                            this.put("oldPwd", binding.beforePwd.text.toString())
//                            this.put("password", binding.newPwd.text.toString())
//                        }
//
//                        val mediaType = "application/json;charset=utf-8".toMediaTypeOrNull()
//                        val requestBody = RequestBody.create(mediaType, jsonObject.toString())
//
//                        val build = Request.Builder()
//                                .url(API.getUserResetPwd(requireContext()))
//                                .put(requestBody)
//                                .header("Authorization", API.getToken(requireContext()))
//                                .build()
//
//                        val string = this.newCall(build).execute().body?.string()
//
//                        val message = Message()
//                        message.obj = string
//                        hander.sendMessage(message)
//                    }
//                }.start()
//            }
//        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChangePwdFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): ChangePwdFragment {
            val fragment = ChangePwdFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}