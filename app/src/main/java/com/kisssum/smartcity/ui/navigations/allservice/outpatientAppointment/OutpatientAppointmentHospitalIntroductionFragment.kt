package com.kisssum.smartcity.ui.navigations.allservice.outpatientAppointment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentOutpatientAppointmentHospitalIntroductionBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OutpatientAppointmentHospitalIntroductionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutpatientAppointmentHospitalIntroductionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentOutpatientAppointmentHospitalIntroductionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentOutpatientAppointmentHospitalIntroductionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restore()
        initLunbotu()

        binding.oahiToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }

        binding.oahiButton.setOnClickListener { v ->
            val findNavController = Navigation.findNavController(requireActivity(), R.id.fragment_main)
            findNavController.navigate(R.id.action_outpatientAppointmentHospitalIntroductionFragment_to_outpatientAppointmentPatientCardFragment)
        }
    }

    private fun restore() {
        binding.oahiText.text = arguments?.getString("describe")
    }

    private fun initLunbotu() {
        val topViewAdapter = object : FragmentStateAdapter(requireActivity()) {
            override fun createFragment(position: Int): Fragment {
                return OutpatientAppointmentHospitalIntroductionTopViewPagerFragment(position)
            }

            override fun getItemCount(): Int {
                return 5
            }
        }
        binding.oahiPager.adapter = topViewAdapter
        binding.oahiPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        // 无限滚轮
        loopTopViewPager()
    }

    private fun loopTopViewPager() {
        Handler().postDelayed({
            var cIndex: Int = binding.oahiPager.currentItem
            if (cIndex >= 4) cIndex = 0 else cIndex++
            binding.oahiPager.currentItem = cIndex
            loopTopViewPager()
        }, 3000)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OutpatientAppointmentHospitalIntroductionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                OutpatientAppointmentHospitalIntroductionFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}