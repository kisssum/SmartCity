package com.kisssum.smartcity.ui.navigations.me

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentMeBinding
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import com.kisssum.smartcity.tool.UpdateUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        initBtn()
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.Main) {
            val userInfoString = withContext(Dispatchers.IO) { MRString.getUserInfo(requireContext()) }
            val userInfoObj = DecodeJson.decodeUserInfo(userInfoString)

            if (userInfoObj["avatar"] != "")
                Glide.with(requireActivity()).load(userInfoObj["avatar"]).into(binding.meTop)
            binding.userName.text = userInfoObj["nickName"]
        }
    }


    private fun initBtn() {
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

        binding.btnTuiChu.setOnClickListener { v: View? ->
            val sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
            sp.edit()
                    .putString("token", "")
                    .putString("userName", "")
                    .apply()

            UpdateUI.toastUi(requireContext(), "退出成功")

            val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
            controller.navigate(R.id.action_navControlFragment_to_loginFragment)
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