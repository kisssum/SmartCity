package com.kisssum.smartcity.ui

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentNavControlBinding

/**
 * A simple [Fragment] subclass.
 * Use the [NavControlFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NavControlFragment : Fragment() {
    private lateinit var binding: FragmentNavControlBinding

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentNavControlBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val token = sp.getString("token", "")

        // 用户未注册就注册
        if (token == "") {
            val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
            controller.navigate(R.id.action_navControlFragment_to_loginFragment)
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            val controller = Navigation.findNavController(requireActivity(), R.id.fragment_detail)
            when (item.itemId) {
                R.id.item_home -> {
                    controller.popBackStack()
                    controller.navigate(R.id.homeFragment)
                }
                R.id.item_allservice -> {
                    controller.popBackStack()
                    controller.navigate(R.id.allServiceFragment)
                }
                R.id.item_news -> {
                    controller.popBackStack()
                    controller.navigate(R.id.newsFragment)
                }
                R.id.item_me -> {
                    controller.popBackStack()
                    controller.navigate(R.id.meFragment)
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = Color.TRANSPARENT
        if (isDarkTheme(requireContext())) {
            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
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
         * @return A new instance of fragment NavControlFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): NavControlFragment {
            val fragment = NavControlFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}