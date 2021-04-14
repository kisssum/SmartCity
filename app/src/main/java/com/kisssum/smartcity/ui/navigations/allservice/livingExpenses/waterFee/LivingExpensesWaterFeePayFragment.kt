package com.kisssum.smartcity.ui.navigations.allservice.livingExpenses.waterFee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentLivingExpensesWaterFeePayBinding
import com.kisssum.smartcity.state.livingExpenses.LivingExpensesWaterFeeModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LivingExpensesWaterFeePayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LivingExpensesWaterFeePayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLivingExpensesWaterFeePayBinding
    private lateinit var viewModel: LivingExpensesWaterFeeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLivingExpensesWaterFeePayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initToolbar()
        restore()
    }

    private fun restore() {
        val areaPosition = arguments?.getInt("area")
        val area = resources.getStringArray(R.array.areas)[areaPosition!!]
        val date = arguments?.getString("date")
        binding.lewpfArea.text = area

        binding.lewfpWaterNumber.text = arguments?.getString("waterNumber")
        binding.lewfpMoney.text = arguments?.getString("pay")

        binding.lewfpGoPay.setOnClickListener {
            val map = HashMap<String, String>()
            map["area"] = areaPosition.toString()
            map["waterNumber"] = binding.lewfpWaterNumber.text.toString()
            map["date"] = date!!
            map["pay"] = binding.lewfpMoney.text.toString()
            viewModel.getData().value?.add(map)

            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(LivingExpensesWaterFeeModel::class.java)
    }

    private fun initToolbar() {
        binding.lewfpToolbar.setNavigationOnClickListener { v ->
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
         * @return A new instance of fragment LivingExpensesWaterFeePayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LivingExpensesWaterFeePayFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}