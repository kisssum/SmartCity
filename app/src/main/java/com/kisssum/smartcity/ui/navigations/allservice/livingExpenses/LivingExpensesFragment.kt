package com.kisssum.smartcity.ui.navigations.allservice.livingExpenses

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentLivingExpensesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LivingExpensesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LivingExpensesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLivingExpensesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLivingExpensesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initFeatures()
    }

    private fun initToolbar() {
        binding.leToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()

        requireActivity().window.statusBarColor = Color.RED
    }

    private fun initFeatures() {
        binding.lePhoneFee.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_livingExpensesFragment_to_livingExpensesPhoneFeeFragment)
        }

        binding.leAccountManagement.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_livingExpensesFragment_to_livingExpensesAccountManagementFragment)
        }

        binding.leWaterFee.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_livingExpensesFragment_to_livingExpensesFragmentWaterFeeFragment)
        }

        binding.leElectricityFee.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_livingExpensesFragment_to_livingExpensesElectricityFeeFragment)
        }

        binding.leGasFee.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_livingExpensesFragment_to_livingExpensesGasFeeFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LivingExpensesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LivingExpensesFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}