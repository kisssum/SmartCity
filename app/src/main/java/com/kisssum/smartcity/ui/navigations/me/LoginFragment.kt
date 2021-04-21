package com.kisssum.smartcity.ui.navigations.me

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentLoginBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import com.kisssum.smartcity.tool.UpdateUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var binding: FragmentLoginBinding
    private var handler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val name = binding.loginName.text.toString()
            val pwd = binding.loginPwd.text.toString()

            if (name == "" || pwd == "") {
                Toast.makeText(requireContext(), "账号或密码不能为空", Toast.LENGTH_LONG).show()
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    val string = withContext(Dispatchers.IO) { MRString.getLoginInfo(name, pwd) }
                    val result = DecodeJson.decodeUserLogin(string.toString())

                    if (result == "") {
                        UpdateUI.toastUi(requireContext(), "登录失败!")
                    } else {
                        val sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
                        sp.edit()
                                .putString("token", result)
                                .putString("userName", name)
                                .apply()

                        val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
                        controller.popBackStack()
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
            controller.navigate(R.id.action_loginFragment_to_registerFragment)
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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): LoginFragment {
            val fragment = LoginFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}