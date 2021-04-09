package com.kisssum.smartcity.ui.navigations.allservice.outpatientAppointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentOutpatientAppointmentPerfectPatientInformationBinding
import com.kisssum.smartcity.state.OutpatientAppointmentPatientCardModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OutpatientAppointmentPerfectPatientInformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutpatientAppointmentPerfectPatientInformationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentOutpatientAppointmentPerfectPatientInformationBinding
    private var isCreate = true
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentOutpatientAppointmentPerfectPatientInformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.oappiToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }

        val viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(OutpatientAppointmentPatientCardModel::class.java)

        isCreate = arguments?.getBoolean("isCreate")!!

        if (!isCreate) {
            position = arguments?.getInt("position")!!

            val map = viewModel.getData().value?.get(position)
            restore(map!!)
        }

        binding.oappiButton.setOnClickListener {
            val newMap = HashMap<String, String>()

            newMap["name"] = binding.oappiName.text.toString()
            newMap["gender"] = binding.oappiGender.text.toString()
            newMap["personalNumber"] = binding.oappiPersonalNumber.text.toString()
            newMap["bronDate"] = binding.oappiBornDate.text.toString()
            newMap["phoneNumber"] = binding.oappiPhoneNumber.text.toString()
            newMap["address"] = binding.oappiAddress.text.toString()
            newMap["patientNumber"] = binding.oappiPatientNumber.text.toString()


            if (isCreate) {
                viewModel.getData().value?.add(newMap)
            } else {
                viewModel.getData().value?.removeAt(position)
                viewModel.getData().value?.add(position, newMap)
            }

            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    private fun restore(map: Map<String, String>) {
        binding.oappiName.setText(map["name"])
        binding.oappiGender.setText(map["gender"])
        binding.oappiPersonalNumber.setText(map["personalNumber"])
        binding.oappiBornDate.setText(map["bronDate"])
        binding.oappiPhoneNumber.setText(map["phoneNumber"])
        binding.oappiAddress.setText(map["address"])
        binding.oappiPatientNumber.setText(map["patientNumber"])
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OutpatientAppointmentPerfectPatientInformationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                OutpatientAppointmentPerfectPatientInformationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}