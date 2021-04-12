package com.kisssum.smartcity.ui.navigations.allservice.smartBus

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.allservice.smartBus.SmartBusExtendableListAdapater
import com.kisssum.smartcity.databinding.FragmentSmartBusBinding
import com.kisssum.smartcity.state.SmartBusModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SmartBusFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SmartBusFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSmartBusBinding
    private lateinit var viewModel: SmartBusModel
    private lateinit var extendableListAdapater: SmartBusExtendableListAdapater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSmartBusBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initToolbar()
        initList()
    }

    override fun onResume() {
        super.onResume()

        requireActivity().window.statusBarColor= Color.RED
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(SmartBusModel::class.java)
    }

    private fun initToolbar() {
        binding.sbToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    private fun initList() {
        binding.sbList.apply {
            extendableListAdapater = SmartBusExtendableListAdapater(requireContext(), viewModel.getGroupData().value!!, viewModel.getChildData().value!!)
            this.setAdapter(extendableListAdapater)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SmartBusFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SmartBusFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}