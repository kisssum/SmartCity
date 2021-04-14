package com.kisssum.smartcity.ui.navigations.allservice.livingExpenses.phoneFee

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentLivingExpensesPhoneFeePayBinding
import com.kisssum.smartcity.state.livingExpenses.LivingExpensesPhoneFeeModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LivingExpensesPhoneFeePayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LivingExpensesPhoneFeePayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLivingExpensesPhoneFeePayBinding
    private lateinit var viewModel: LivingExpensesPhoneFeeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLivingExpensesPhoneFeePayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initViewModel()

        val type = arguments?.getInt("type")
        val phoneNumber = arguments?.getString("phoneNumber")
        val date = arguments?.getString("date")
        var payNumber = 50

        binding.lepffPhoneNumber.text = phoneNumber
        binding.lepfp50.setCardBackgroundColor(Color.GREEN)

        binding.lepfp50.setOnClickListener {
            binding.lepfp50.setCardBackgroundColor(Color.GREEN)
            binding.lepfp100.setCardBackgroundColor(Color.WHITE)
            binding.lepfp200.setCardBackgroundColor(Color.WHITE)
            payNumber = 50
        }

        binding.lepfp100.setOnClickListener {
            binding.lepfp50.setCardBackgroundColor(Color.WHITE)
            binding.lepfp100.setCardBackgroundColor(Color.GREEN)
            binding.lepfp200.setCardBackgroundColor(Color.WHITE)
            payNumber = 100
        }

        binding.lepfp200.setOnClickListener {
            binding.lepfp50.setCardBackgroundColor(Color.WHITE)
            binding.lepfp100.setCardBackgroundColor(Color.WHITE)
            binding.lepfp200.setCardBackgroundColor(Color.GREEN)
            payNumber = 200
        }

        binding.lepfpGoPay.setOnClickListener {
            val map = HashMap<String, String>()
            map["type"] = type.toString()
            map["phoneNumber"] = phoneNumber.toString()
            map["date"] = date.toString()
            map["payNumber"] = payNumber.toString()
            viewModel.getData().value?.add(map)

            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(LivingExpensesPhoneFeeModel::class.java)
    }

    private fun initToolbar() {
        binding.lepfpToolbar.setNavigationOnClickListener { v ->
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
         * @return A new instance of fragment LivingExpensesPhoneFeePayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LivingExpensesPhoneFeePayFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}