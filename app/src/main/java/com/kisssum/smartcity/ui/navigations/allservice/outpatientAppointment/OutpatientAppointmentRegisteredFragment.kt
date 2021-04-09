package com.kisssum.smartcity.ui.navigations.allservice.outpatientAppointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentOutpatientAppointmentRegisteredBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OutpatientAppointmentRegisteredFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutpatientAppointmentRegisteredFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentOutpatientAppointmentRegisteredBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentOutpatientAppointmentRegisteredBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.oarToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }

        initViewPager()
    }

    private fun initViewPager() {
        binding.oarViewPager.adapter = object : FragmentStateAdapter(requireActivity()) {
            override fun getItemCount() = 2

            override fun createFragment(position: Int) = when (position) {
                0 -> OutpatientAppointmentRegisteredExpertFragment()
                else -> OutpatientAppointmentRegisteredGeneralFragment()
            }
        }

        TabLayoutMediator(binding.oarTabLayout, binding.oarViewPager) { tab: TabLayout.Tab, i: Int ->
            tab.text = when (i) {
                0 -> "专家"
                else -> "普通"
            }
        }.attach()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OutpatientAppointmentRegisteredFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                OutpatientAppointmentRegisteredFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}