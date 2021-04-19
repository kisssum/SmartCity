package com.kisssum.smartcity.ui.navigations.me

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentMeInformationBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [MeInformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MeInformationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var binding: FragmentMeInformationBinding
    private lateinit var handler: Handler
    private lateinit var userInfo: Map<String, Any>

    private val RESTORE = 0
    private val SAVE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentMeInformationBinding.inflate(inflater)
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
                message.what = RESTORE
                message.obj = doc
                handler.sendMessage(message)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                if (msg.what == RESTORE) {
                    userInfo = DecodeJson.decodeUserInfoInformation(msg.obj as String)

                    Glide
                            .with(requireActivity())
                            .load(API.getBaseUrl(requireContext()) + userInfo["avatar"].toString())
                            .into(binding.miImg)

                    binding.miName.text = userInfo["nickName"].toString()
                    binding.miSex.isChecked = when (userInfo["sex"].toString().toInt()) {
                        0 -> false
                        else -> true
                    }
                    binding.miPhone.text = userInfo["phonenumber"].toString()
                } else if (msg.what == SAVE) {
                    val jsonObject = JSONObject(msg.obj as String)
                    if (jsonObject.getInt("code") == 200) {
                        Toast.makeText(requireContext(), "修改成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "修改失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        resotre()
        initBtn()
    }

    private fun save() {
        Thread {
            try {
                val json = JSONObject().apply {
                    this.put("userId", userInfo["userId"])
                    this.put("nickName", binding.miName.text.toString())
                    this.put("sex", binding.miSex.isChecked)
                    this.put("phonenumber", binding.miPhone.text.toString())
                }

                val mediaType = "application/json;charset=utf-8".toMediaTypeOrNull()
                val requestBody = RequestBody.create(mediaType, json.toString())

                val request = Request.Builder()
                        .url(API.getUserUpdata(requireContext()))
                        .post(requestBody)
                        .header("Authorization", API.getToken(requireContext()))
                        .build()

                val client = OkHttpClient()
                val response = client.newCall(request).execute()
                val doc = response.body!!.string()

                val message = Message()
                message.what = SAVE
                message.obj = doc
                handler.sendMessage(message)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun initBtn() {
        binding.meInformationToolbar.setNavigationOnClickListener { v: View? -> Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp() }
        binding.meInformationToolbar.setOnMenuItemClickListener { item: MenuItem ->
            if (item.itemId == R.id.item_me_information_change) save()
            true
        }

        binding.miName.setOnClickListener { v: View? ->
            val view1 = layoutInflater.inflate(R.layout.alertdialog_change_name, null)
            AlertDialog.Builder(requireActivity())
                    .setTitle("修改昵称")
                    .setView(view1)
                    .setPositiveButton("确定") { dialog: DialogInterface?, which: Int ->
                        val name = view1.findViewById<EditText>(R.id.editext)
                        if (name.text.toString() == "") Toast.makeText(requireContext(), "昵称不能为空", Toast.LENGTH_SHORT).show()
                        else binding.miName.text = name.text.toString()
                    }
                    .setNegativeButton("取消") { dialog: DialogInterface?, which: Int -> }
                    .create()
                    .show()
        }

        binding.miPhone.setOnClickListener { v: View? ->
            val view1 = layoutInflater.inflate(R.layout.alertdialog_change_phone, null)
            AlertDialog.Builder(requireActivity())
                    .setTitle("修改电话")
                    .setView(view1)
                    .setPositiveButton("确定") { dialog: DialogInterface?, which: Int ->
                        val name = view1.findViewById<EditText>(R.id.editext)
                        when {
                            name.text.toString() == "" -> Toast.makeText(requireContext(), "电话号码不能为空", Toast.LENGTH_SHORT).show()
                            name.text.toString().length != 11 -> Toast.makeText(requireContext(), "电话号码长度不正确", Toast.LENGTH_SHORT).show()
                            else -> binding.miPhone.text = name.text.toString()
                        }
                    }
                    .setNegativeButton("取消") { dialog: DialogInterface?, which: Int -> }
                    .create()
                    .show()
        }
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
         * @return A new instance of fragment MeInformationFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): MeInformationFragment {
            val fragment = MeInformationFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}