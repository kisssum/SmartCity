package com.kisssum.smartcity.ui.navigations.allservice.outpatientAppointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentOutpatientAppointmentHospitalIntroductionTopViewPagerBinding
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OutpatientAppointmentHospitalIntroductionTopViewPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutpatientAppointmentHospitalIntroductionTopViewPagerFragment(val index: Int) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentOutpatientAppointmentHospitalIntroductionTopViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentOutpatientAppointmentHospitalIntroductionTopViewPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgs: MutableList<Int> = ArrayList()
        imgs.add(R.drawable.top_view_pager_1)
        imgs.add(R.drawable.top_view_pager_2)
        imgs.add(R.drawable.top_view_pager_3)
        imgs.add(R.drawable.top_view_pager_4)
        imgs.add(R.drawable.top_view_pager_5)

        binding.imageView3.setImageResource(imgs[index])
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OutpatientAppointmentHospitalIntroductionTopViewPagerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                OutpatientAppointmentHospitalIntroductionTopViewPagerFragment(0).apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}