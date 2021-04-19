package com.kisssum.smartcity.ui.navigations.me

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentMeBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [MeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var binding: FragmentMeBinding
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentMeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 0) {
                    val map = DecodeJson.decodeUserInfo(msg.obj as String)
                    binding.userName.text = map["nickName"].toString()

                    Glide
                            .with(requireActivity())
                            .load(API.getBaseUrl(requireContext()) + map["avatar"])
                            .into(binding.meTop)

                    requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE).edit().putInt("userId", map["userId"].toString().toInt()).apply()
                }
            }
        }

        resotre()
        navigationPager()
        backUser()
    }

    private fun backUser() {
        binding.btnTuiChu.setOnClickListener { v: View? ->
            val sp = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
            sp.edit().clear().apply()

            Toast.makeText(requireContext(), "退出成功", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigationPager() {
        val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
        // 跳转到详细信息页
        binding.meTop.setOnClickListener { v: View? -> controller.navigate(R.id.action_navControlFragment_to_meInformationFragment) }
        binding.userName.setOnClickListener { v: View? -> controller.navigate(R.id.action_navControlFragment_to_meInformationFragment) }
        binding.l1.setOnClickListener { v: View? -> controller.navigate(R.id.action_navControlFragment_to_meInformationFragment) }
        // 跳转到账单页
        binding.l2.setOnClickListener { v: View? -> controller.navigate(R.id.action_navControlFragment_to_orderListFragment) }
        // 跳转到修改密码页
        binding.l3.setOnClickListener { v: View? -> controller.navigate(R.id.action_navControlFragment_to_changePwdFragment) }
        // 跳转到反馈页
        binding.l4.setOnClickListener { v: View? -> controller.navigate(R.id.action_navControlFragment_to_opinionFragment) }
    }

    private fun resotre() {
        Thread {
            try {
                val request = Request.Builder()
                        .url(API.getUserInfoUrl(requireContext()))
                        .header("Authorization", API.getToken(requireContext()))
                        .build()

                val client = OkHttpClient()
                val response = client.newCall(request).execute()
                val doc = response.body!!.string()

                val message = Message()
                message.what = 0
                message.obj = doc
                handler!!.sendMessage(message)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
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
         * @return A new instance of fragment MeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): MeFragment {
            val fragment = MeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}