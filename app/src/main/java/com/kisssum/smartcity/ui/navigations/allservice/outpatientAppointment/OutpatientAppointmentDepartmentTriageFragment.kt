package com.kisssum.smartcity.ui.navigations.allservice.outpatientAppointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentOutpatientAppointmentDepartmentTriageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OutpatientAppointmentDepartmentTriageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutpatientAppointmentDepartmentTriageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentOutpatientAppointmentDepartmentTriageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentOutpatientAppointmentDepartmentTriageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.oadtToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }

        initList()
    }

    private fun initList() {
        val arrayOf = arrayOf("神经内科", "心肾内科", "呼吸消化科", "慢性病科", "普外科", "骨外科", "妇产科", "儿科", "眼耳鼻喉科", "口腔科皮肤科", "急诊")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, arrayOf)
        binding.oadtList.apply {
            this.adapter = arrayAdapter

            this.setOnItemClickListener { adapterView, view, i, l ->
                run {
                    Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_outpatientAppointmentDepartmentTriageFragment_to_outpatientAppointmentRegisteredFragment)
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OutpatientAppointmentDepartmentTriageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                OutpatientAppointmentDepartmentTriageFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}