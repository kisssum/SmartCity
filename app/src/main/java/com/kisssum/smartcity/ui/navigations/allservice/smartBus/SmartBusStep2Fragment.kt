package com.kisssum.smartcity.ui.navigations.allservice.smartBus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentSmartBusStep2Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SmartBusStep2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SmartBusStep2Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSmartBusStep2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSmartBusStep2Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        binding.sbs2Calendar.setOnDateChangeListener { calendarView, i, i2, i3 ->
            run {
                binding.sbs2Text.text = binding.sbs2Text.text.toString() + " ${i}-${i2}-${i3}"
            }
        }

        binding.sbs2Next.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("position", arguments?.getInt("position")!!)
            bundle.putString("dataText", binding.sbs2Text.text.toString())

            Navigation.findNavController(requireActivity(), R.id.fragment_main).apply {
                this.navigate(R.id.action_smartBusStep2Fragment_to_smartBusStep3Fragment, bundle)
            }
        }
    }

    private fun initToolbar() {
        binding.sbs2Toolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SmartBusStep2Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SmartBusStep2Fragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}