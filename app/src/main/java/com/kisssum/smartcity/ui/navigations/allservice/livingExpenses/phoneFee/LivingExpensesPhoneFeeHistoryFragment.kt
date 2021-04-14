package com.kisssum.smartcity.ui.navigations.allservice.livingExpenses.phoneFee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentLivingExpensesPhoneFeeHistoryBinding
import com.kisssum.smartcity.state.livingExpenses.LivingExpensesPhoneFeeModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LivingExpensesPhoneFeeHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LivingExpensesPhoneFeeHistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLivingExpensesPhoneFeeHistoryBinding
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
        binding = FragmentLivingExpensesPhoneFeeHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initToolbar()
        initList()
    }

    private fun initList() {
        val simpleAdapter = SimpleAdapter(
                requireContext(),
                viewModel.getData().value!!,
                R.layout.list_style_living_expenses_phone_fee_history,
                arrayOf("phoneNumber", "date", "payNumber"),
                intArrayOf(R.id.lsvepfhPhoneNumber, R.id.lsvepfhDate, R.id.lsvepfhPay)
        )

        binding.lepfhList.adapter = simpleAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(LivingExpensesPhoneFeeModel::class.java)
    }

    private fun initToolbar() {
        binding.lepfhToolbar.setNavigationOnClickListener { v ->
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
         * @return A new instance of fragment LivingExpensesPhoneFeeHistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LivingExpensesPhoneFeeHistoryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}