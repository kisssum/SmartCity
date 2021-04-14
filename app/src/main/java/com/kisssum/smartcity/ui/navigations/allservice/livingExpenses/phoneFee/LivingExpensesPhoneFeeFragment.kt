package com.kisssum.smartcity.ui.navigations.allservice.livingExpenses.phoneFee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentLivingExpensesPhoneFeeBinding
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LivingExpensesPhoneFeeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LivingExpensesPhoneFeeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLivingExpensesPhoneFeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLivingExpensesPhoneFeeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        val c = Calendar.getInstance()
        binding.lepfDate.text = "${c[Calendar.YEAR]}.${c[Calendar.MONTH] + 1}.${c[Calendar.DAY_OF_MONTH]}"

        binding.lepfFind.setOnClickListener {
            if (binding.lepfPhoneNumber.text.toString() != "") {
                val bundle = Bundle().apply {
                    this.putInt("type", binding.lepfChoiceOprators.selectedItemPosition)
                    this.putString("phoneNumber", binding.lepfPhoneNumber.text.toString())
                    this.putString("date", binding.lepfDate.text.toString())
                }

                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_livingExpensesPhoneFeeFragment_to_livingExpensesPhoneFeePayFragment, bundle)
            }
        }
    }

    private fun initToolbar() {
        binding.lepfToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }

        binding.lepfToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_living_expenses_fee_history -> {
                    Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_livingExpensesPhoneFeeFragment_to_livingExpensesPhoneFeeHistoryFragment)
                }
                else -> ""
            }

            return@setOnMenuItemClickListener true
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LivingExpensesPhoneFeeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LivingExpensesPhoneFeeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}